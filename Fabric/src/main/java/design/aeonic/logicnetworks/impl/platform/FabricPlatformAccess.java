package design.aeonic.logicnetworks.impl.platform;

import design.aeonic.logicnetworks.api.platform.IPlatformAccess;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricPlatformAccess implements IPlatformAccess {
    @Override
    public <T extends BlockEntity> BlockEntityType<T> blockEntityType(BlockEntitySupplier<T> supplier, Block... validBlocks) {
        return FabricBlockEntityTypeBuilder.create(supplier::create, validBlocks).build(null);
    }
}
