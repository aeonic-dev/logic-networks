package design.aeonic.logicnetworks.impl.content.anchor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class NetworkAnchorBlock extends BaseEntityBlock {

    public NetworkAnchorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSignalSource(BlockState $$0) {
        return true;
    }

    @Override
    public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
        BlockEntity blockEntity = $$1.getBlockEntity($$2);
        if (blockEntity instanceof NetworkAnchorBlockEntity) {
            return ((NetworkAnchorBlockEntity) blockEntity).getRedstone($$3);
        }
        return 0;
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
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NetworkAnchorBlockEntity(pos, state);
    }
}
