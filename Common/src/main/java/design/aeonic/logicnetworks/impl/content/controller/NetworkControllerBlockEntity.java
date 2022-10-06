package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.impl.builtin.redstone.RedstoneOperators;
import design.aeonic.logicnetworks.api.graph.Network;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.graph.Socket;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NetworkControllerBlockEntity extends BlockEntity {
    private Network network;

    public NetworkControllerBlockEntity(BlockPos pos, BlockState state) {
        super(NetworkBlockEntities.NETWORK_CONTROLLER, pos, state);
    }

    public void networkTest() {
        Node<?, ?> node1 = getNetwork().addNode(new Node<>(network, RedstoneOperators.ANALOG_INVERT, 0, 0));
        Node<?, ?> node2 = getNetwork().addNode(new Node<>(network, RedstoneOperators.ANALOG_INVERT, 0, 0));
        node1.getSocket(Socket.Side.OUTPUT, 0).connect(node2.getSocket(Socket.Side.INPUT, 0));
        setChanged();
    }

    public Network getNetwork() {
        if (network == null) network = new Network();
        return network;
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
}
