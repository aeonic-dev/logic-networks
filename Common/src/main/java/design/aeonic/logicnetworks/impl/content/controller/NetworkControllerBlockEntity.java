package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.control.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.CompiledNetwork;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.field.EnumField;
import design.aeonic.logicnetworks.api.networking.container.field.IntField;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import design.aeonic.logicnetworks.impl.logic.NetworkImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

    private RedstoneControl redstoneControl = RedstoneControl.ALWAYS;
    private int ticksPerOperation = 20;

    private ContainerFields containerData = new ContainerFields(
            new EnumField<>(RedstoneControl.class, () -> redstoneControl),
            new IntField(() -> ticksPerOperation)
    );

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

    public Network getNetwork() {
        if (network == null) network = new NetworkImpl();
        return network;
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

        if (network != null) {
            CompoundTag networkTag = new CompoundTag();
            network.serialize(networkTag);
            tag.put("network", networkTag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

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
        return new NetworkControllerMenu(var1, var2, containerData);
    }
}
