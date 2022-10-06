package design.aeonic.logicnetworks.api.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public record Texture(ResourceLocation location, int width, int height) {
    public void setup(float r, float g, float b, float a) {
        RenderSystem.setShaderColor(r, g, b, a);
        setup();
    }

    public void setup() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
    }
}
