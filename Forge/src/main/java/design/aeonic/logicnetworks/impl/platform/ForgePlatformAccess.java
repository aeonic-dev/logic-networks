package design.aeonic.logicnetworks.impl.platform;

import design.aeonic.logicnetworks.api.platform.IPlatformAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ForgePlatformAccess implements IPlatformAccess {
    @Override
    public <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockEntitySupplier<T> supplier, Block... validBlocks) {
        return BlockEntityType.Builder.of(supplier::create, validBlocks).build(null);
    }
}
