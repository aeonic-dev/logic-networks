package design.aeonic.logicnetworks.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.block.FacadeHideable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FacadeRenderer<T extends BlockEntity & FacadeHideable> implements BlockEntityRenderer<T> {

    public FacadeRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(T blockEntity, float partialTick, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay) {
        BlockState facade = blockEntity.getFacade();
        if (facade != null) {
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(facade, stack, bufferSource, light, overlay);
        }
    }
}
