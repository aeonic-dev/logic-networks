package design.aeonic.logicnetworks.impl.content.controller;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class NetworkControllerBlock extends BaseEntityBlock {
    public NetworkControllerBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        var prv = getMenuProvider(state, level, pos);
        if (!level.isClientSide()) {
            player.openMenu(prv);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
        return (level, pos, state, be) -> {
            if (level instanceof ServerLevel serverLevel) ((NetworkControllerBlockEntity) be).serverTick(serverLevel);
        };
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
        return (MenuProvider) $$1.getBlockEntity($$2);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkControllerBlockEntity(pos, state);
    }
}
