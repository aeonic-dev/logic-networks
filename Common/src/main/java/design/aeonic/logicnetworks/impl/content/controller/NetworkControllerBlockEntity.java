package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.control.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.CompiledNetwork;
import design.aeonic.logicnetworks.api.logic.Network;
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
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class NetworkControllerBlockEntity extends BlockEntity implements MenuProvider, design.aeonic.logicnetworks.api.logic.Network.IHasNetwork {
    private Network network;
    private CompiledNetwork compiledNetwork;

    private RedstoneControl redstoneControl;
    private int ticksPerOperation;

    public NetworkControllerBlockEntity(BlockPos pos, BlockState state) {
        super(NetworkBlockEntities.NETWORK_CONTROLLER, pos, state);
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

    public void networkTick() {
        if (redstoneControl.shouldRun(level, worldPosition)) {
            if (level.getGameTime() % ticksPerOperation == 0) {
                getCompiledNetwork().tick();
            }
        }
    }

    public CompiledNetwork getCompiledNetwork() {
        if (compiledNetwork == null) compiledNetwork = network.compile();
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
        this.compiledNetwork = network.compile();
        setChanged();
        level.setBlock(getBlockPos(), getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putString("redstoneControl", redstoneControl.name());
        tag.putInt("ticksPerOperation", ticksPerOperation);

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

        if (tag.contains("network")) {
            network = Network.deserialize(tag.getCompound("network"));
        }
    }

    @Override
    public Component getDisplayName() {
        return Translations.NetworkController.TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
        Constants.LOG.info("server side {} {}", redstoneControl, ticksPerOperation);
        return new NetworkControllerMenu(var1, var2, new ContainerFields(
                new EnumField<>(RedstoneControl.class, () -> redstoneControl),
                new IntField(() -> ticksPerOperation),
                new BlockPosField(this::getBlockPos)
        ));
    }
}
