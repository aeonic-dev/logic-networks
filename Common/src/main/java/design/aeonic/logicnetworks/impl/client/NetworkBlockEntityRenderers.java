package design.aeonic.logicnetworks.impl.client;

import design.aeonic.logicnetworks.api.client.FacadeRenderer;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class NetworkBlockEntityRenderers {
    public static void register(BlockEntityRendererRegistry registry) {
        registry.accept(NetworkBlockEntities.NETWORK_ANCHOR, FacadeRenderer::new);
    }

    public interface BlockEntityRendererRegistry {
        <T extends BlockEntity> void accept(BlockEntityType<T> type, BlockEntityRendererProvider<T> provider);
    }
}
