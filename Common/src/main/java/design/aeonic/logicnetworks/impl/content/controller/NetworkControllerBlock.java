package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.graph.Network;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.graph.Socket;
import design.aeonic.logicnetworks.api.builtin.redstone.RedstoneOperators;
import design.aeonic.logicnetworks.impl.graph.NetworkGraphScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class NetworkControllerBlock extends BaseEntityBlock {
    public NetworkControllerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
//        if (!level.isClientSide) {
//            BlockEntity blockEntity = level.getBlockEntity(pos);
//            if (blockEntity instanceof NetworkControllerBlockEntity networkControllerBlockEntity) {
//                networkControllerBlockEntity.networkTest();
//                return InteractionResult.CONSUME;
//            }
//        }
        if (level.isClientSide) {
            Network network = new Network();

            var node1 = network.addNode(new Node<>(network, RedstoneOperators.ANALOG_ADD, 0, 0));
            var node2 = network.addNode(new Node<>(network, RedstoneOperators.ANALOG_ADD, 100, 20));
            var node3 = network.addNode(new Node<>(network, RedstoneOperators.ANALOG_INVERT, 0, 150));
            var node4 = network.addNode(new Node<>(network, RedstoneOperators.ANALOG_SUBTRACT, -120, 80));

            node1.getOutputSocket().connect(node2.getSocket(Socket.Side.INPUT, 0));
            node4.getOutputSocket().connect(node3.getSocket(Socket.Side.INPUT, 0));
            node3.getOutputSocket().connect(node2.getSocket(Socket.Side.INPUT, 1));

            Minecraft.getInstance().setScreen(new NetworkGraphScreen(Component.literal("Network Graph"), network));
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkControllerBlockEntity(pos, state);
    }
}
