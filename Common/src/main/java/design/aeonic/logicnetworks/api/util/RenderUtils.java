package design.aeonic.logicnetworks.api.util;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.client.Texture;
import net.minecraft.client.gui.screens.Screen;

public final class RenderUtils {
    /**
     * Draws a rectangle with the given texture tiled to fit the dimensions. Ex: nodes, which use a 16x16 texture.
     * See the `textures/gui/graph/node.png` file for an example.
     */
    public static void drawRect(PoseStack stack, Texture texture, int x, int y, int zOffset, int width, int height) {
        int cornerWidth = texture.width() / 4;
        int cornerHeight = texture.height() / 4;

        texture.setup(1, 1, 1, 1);

        // Corners
        Screen.blit(stack, x, y, zOffset, 0, 0, cornerWidth, cornerHeight, texture.width(), texture.height());
        Screen.blit(stack, x + width - cornerWidth, y, zOffset, texture.width() - cornerWidth, 0, cornerWidth, cornerHeight, texture.width(), texture.height());
        Screen.blit(stack, x, y + height - cornerHeight, zOffset, 0, texture.height() - cornerHeight, cornerWidth, cornerHeight, texture.width(), texture.height());
        Screen.blit(stack, x + width - cornerWidth, y + height - cornerHeight, zOffset, texture.width() - cornerWidth, texture.height() - cornerHeight, cornerWidth, cornerHeight, texture.width(), texture.height());

        for (int i = cornerWidth; i < width - cornerWidth; i += cornerWidth * 2) {
            int w = Math.min(cornerWidth * 2, width - cornerWidth - i);
            // Top + bottom edges
            Screen.blit(stack, x + i, y, zOffset, cornerWidth, 0, w, cornerHeight, texture.width(), texture.height());
            Screen.blit(stack, x + i, y + height - cornerHeight, zOffset, cornerWidth, texture.height() - cornerHeight, w, cornerHeight, texture.width(), texture.height());

            // Infill
            for (int j = cornerHeight; j < height - cornerHeight; j += cornerHeight * 2) {
                int h = Math.min(cornerHeight * 2, height - cornerHeight - j);
                Screen.blit(stack, x + i, y + j, zOffset, cornerWidth, cornerHeight, w, h, texture.width(), texture.height());
            }
        }

        // Left + right edges
        for (int i = cornerHeight; i < height - cornerHeight; i += cornerHeight * 2) {
            int h = Math.min(cornerHeight * 2, height - cornerHeight - i);
            Screen.blit(stack, x, y + i, zOffset, 0, cornerHeight, cornerWidth, h, texture.width(), texture.height());
            Screen.blit(stack, x + width - cornerWidth, y + i, zOffset, texture.width() - cornerWidth, cornerHeight, cornerWidth, h, texture.width(), texture.height());
        }
    }
}
