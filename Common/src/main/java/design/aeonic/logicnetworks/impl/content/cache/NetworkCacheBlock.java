package design.aeonic.logicnetworks.impl.content.cache;

import design.aeonic.logicnetworks.api.block.WrenchableEntityBlock;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class NetworkCacheBlock extends WrenchableEntityBlock {
    private static final VoxelShape shape = box(5, 5, 5, 11, 11, 11);

    public NetworkCacheBlock(Properties props) {
        super(props);
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
