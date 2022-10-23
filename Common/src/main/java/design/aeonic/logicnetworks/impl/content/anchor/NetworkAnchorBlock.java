package design.aeonic.logicnetworks.impl.content.anchor;

import design.aeonic.logicnetworks.api.block.FacadeHideable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class NetworkAnchorBlock extends BaseEntityBlock {
    private final VoxelShape shape = box(5, 5, 5, 11, 11, 11);

    public NetworkAnchorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSignalSource(BlockState $$0) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState $$0) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean hasDynamicShape() {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        if ($$1.getBlockEntity($$2) instanceof FacadeHideable facadeHideable) {
            BlockState facade = facadeHideable.getFacade();
            return facade == null ? shape : facade.getShape($$1, $$2, $$3);
        }
        return shape;
    }

    @Override
    public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        if ($$1.getBlockEntity($$2) instanceof FacadeHideable facadeHideable) {
            BlockState facade = facadeHideable.getFacade();
            return facade == null ? shape : facade.getVisualShape($$1, $$2, $$3);
        }
        return shape;
    }

    @Override
    public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
        BlockEntity blockEntity = $$1.getBlockEntity($$2);
        if (blockEntity instanceof NetworkAnchorBlockEntity anchor) {
            return anchor.getRedstone($$3.getOpposite());
        }
        return 0;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        var result = FacadeHideable.tryPutFacade(state, level, pos, player, hand, res);
        if (result != InteractionResult.FAIL) return result;
        else if ((result = FacadeHideable.tryRemoveFacade(state, level, pos, player, hand, res)) != InteractionResult.FAIL) return result;

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
