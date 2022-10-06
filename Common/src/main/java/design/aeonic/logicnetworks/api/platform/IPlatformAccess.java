package design.aeonic.logicnetworks.api.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public interface IPlatformAccess {
    <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockEntitySupplier<T> supplier, Block... validBlocks);

    @FunctionalInterface
    interface BlockEntitySupplier<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}
