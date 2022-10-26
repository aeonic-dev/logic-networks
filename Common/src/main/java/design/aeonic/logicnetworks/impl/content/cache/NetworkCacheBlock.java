package design.aeonic.logicnetworks.impl.content.cache;

import design.aeonic.logicnetworks.api.block.FacadeHideable;
import design.aeonic.logicnetworks.api.block.WrenchableEntityBlock;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class NetworkCacheBlock extends WrenchableEntityBlock {
    private static final VoxelShape shape = box(5, 5, 5, 11, 11, 11);
    public NetworkCacheBlock(Properties props) {
        super(props);
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        var result = FacadeHideable.tryPutFacade(state, level, pos, player, hand, res);
        if (result != InteractionResult.FAIL) return result;

        result = super.use(state, level, pos, player, hand, res);
        if (result != InteractionResult.PASS) return result;

        return FacadeHideable.tryRemoveFacade(state, level, pos, player, hand, res);
    }

    @Override
    public RenderShape getRenderShape(BlockState $$0) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return NetworkBlockEntities.NETWORK_CACHE.create(pos, state);
    }
}
