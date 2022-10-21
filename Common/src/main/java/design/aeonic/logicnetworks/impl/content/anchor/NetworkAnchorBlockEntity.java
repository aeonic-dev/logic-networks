package design.aeonic.logicnetworks.impl.content.anchor;

import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.NetworkAnchor;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.field.BlockPosField;
import design.aeonic.logicnetworks.api.networking.container.field.StringField;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class NetworkAnchorBlockEntity extends BlockEntity implements MenuProvider, NetworkAnchor {
    private String name = "Network Anchor";
    private Map<Direction, Integer> redstoneSignals = new HashMap<>();

    public NetworkAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(NetworkBlockEntities.NETWORK_ANCHOR, pos, state);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        setChanged();
    }

    @Override
    public <T> void write(Direction side, SignalType<T> type, T value) {
        type.write(this, side, value);
    }

    public int getRedstone(Direction side) {
        return redstoneSignals.getOrDefault(side, 0);
    }

    @Override
    public void setRedstone(Direction side, int signal) {
        if (level == null || level.isClientSide) return;
        redstoneSignals.put(side, signal);
        setChanged();
//        BlockPos relative = getBlockPos().relative(side);
        level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
//        if (updateFlag != Block.UPDATE_NONE) level.setBlock(getBlockPos(), getBlockState(), updateFlag);
    }

    @Override
    public <T> T read(Direction side, SignalType<T> type) {
        return type.read(this, side);
    }

    @Override
    public Component getDisplayName() {
        return Translations.NetworkAnchor.TITLE;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void load(CompoundTag $$0) {
        super.load($$0);
        if ($$0.contains("Name", Tag.TAG_STRING)) name = $$0.getString("Name");

        CompoundTag redstoneSignalTag = $$0.getCompound("Signals");
        for (String key : redstoneSignalTag.getAllKeys()) {
            redstoneSignals.put(Direction.byName(key), redstoneSignalTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag $$0) {
        super.saveAdditional($$0);
        $$0.putString("Name", name);

        CompoundTag redstoneSignalTag = new CompoundTag();
        for (Direction side : Direction.values()) {
            redstoneSignalTag.putInt(side.getName(), getRedstone(side));
        }
        $$0.put("Signals", redstoneSignalTag);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
        return new NetworkAnchorMenu(var1, var2, new ContainerFields(new BlockPosField(this::getBlockPos), new StringField(18, this::getName)));
    }
}
