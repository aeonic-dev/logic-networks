package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.logic.RedstoneControl;
import design.aeonic.logicnetworks.api.logic.network.CompiledNetwork;
import design.aeonic.logicnetworks.api.logic.network.Network;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.field.BlockPosField;
import design.aeonic.logicnetworks.api.networking.container.field.EnumField;
import design.aeonic.logicnetworks.api.networking.container.field.IntField;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import design.aeonic.logicnetworks.impl.logic.NetworkImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class NetworkControllerBlockEntity extends BlockEntity implements MenuProvider, NetworkController {
    private Network network;
    private CompiledNetwork compiledNetwork;

    private RedstoneControl redstoneControl = RedstoneControl.ALWAYS;
    private int ticksPerOperation = 20;
    private int timer = 0;
    private boolean pulsed = false;

    public NetworkControllerBlockEntity(BlockPos pos, BlockState state) {
        super(NetworkBlockEntities.NETWORK_CONTROLLER, pos, state);
    }

    @Nullable
    @Override
    public Level getControllerLevel() {
        return super.getLevel();
    }

    @Override
    public void setChanged() {
        super.setChanged();
        level.setBlock(getBlockPos(), getBlockState(), Block.UPDATE_CLIENTS);
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

    public void serverTick(ServerLevel level) {
        boolean shouldTick = switch (redstoneControl) {
            case ALWAYS ->  ticksPerOperation != 0 && ++timer % ticksPerOperation == 0;
            case HIGH -> level.hasNeighborSignal(getBlockPos()) && ticksPerOperation != 0 && ++timer % ticksPerOperation == 0;
            case LOW -> !level.hasNeighborSignal(getBlockPos()) && ticksPerOperation != 0 && ++timer % ticksPerOperation == 0;
            case NEVER -> false;
            case PULSE -> {
                if (level.hasNeighborSignal(getBlockPos())) {
                    if (!pulsed) yield pulsed = true;
                    else yield false;
                } else yield pulsed = false;
            }
        };

        if (shouldTick) getCompiledNetwork().tick(this);
    }

    public CompiledNetwork getCompiledNetwork() {
        if (level == null || level.isClientSide) return CompiledNetwork.EMPTY;
        if (compiledNetwork == null) compiledNetwork = getNetwork().compile();
        return compiledNetwork;
    }

    public Network getNetwork() {
        if (network == null) network = new NetworkImpl();
        return network;
    }

    public void setRedstoneControl(RedstoneControl redstoneControl) {
        this.redstoneControl = redstoneControl;
        setChanged();
    }

    public void setTicksPerOperation(int ticksPerOperation) {
        this.ticksPerOperation = ticksPerOperation;
        setChanged();
    }

    public void setNetwork(Network network) {
        this.network = network;
        network.getNodes().forEach(node -> node.loadOnServer(this));
        this.compiledNetwork = network.compile();
        setChanged();
        level.setBlock(getBlockPos(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putString("redstoneControl", redstoneControl.name());
        tag.putInt("ticksPerOperation", ticksPerOperation);
        tag.putBoolean("pulsed", pulsed);
        tag.putInt("timer", timer);

        if (network != null) {
            CompoundTag networkTag = new CompoundTag();
            network.serialize(networkTag);
            tag.put("network", networkTag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        redstoneControl = tag.contains("redstoneControl", Tag.TAG_STRING) ? RedstoneControl.valueOf(tag.getString("redstoneControl")) : RedstoneControl.ALWAYS;
        ticksPerOperation = tag.contains("ticksPerOperation", Tag.TAG_INT) ? tag.getInt("ticksPerOperation") : 20;
        pulsed = tag.contains("pulsed", Tag.TAG_BYTE) && tag.getBoolean("pulsed");
        timer = tag.contains("timer", Tag.TAG_INT) ? tag.getInt("timer") : 0;

        if (tag.contains("network")) {
            network = Network.deserialize(tag.getCompound("network"));
            if (level != null && !level.isClientSide) {
                network.getNodes().forEach(node -> node.loadOnServer(this));
                setChanged();
            }
        }
    }

    @Override
    public Component getDisplayName() {
        return Translations.NetworkController.TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
        return new NetworkControllerMenu(var1, var2, new ContainerFields(
                new EnumField<>(RedstoneControl.class, () -> redstoneControl),
                new IntField(() -> ticksPerOperation),
                new BlockPosField(this::getBlockPos)
        ));
    }
}
