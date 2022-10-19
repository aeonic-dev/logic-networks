package design.aeonic.logicnetworks.api.util;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.core.Constants;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public final class RenderUtils {
    public static final Texture LINE_EE_BG = new Texture("logicnetworks:textures/gui/graph/line1.png", 32, 32, 32, 3, 0, 9);
    public static final Texture LINE_NN_BG = new Texture("logicnetworks:textures/gui/graph/line2.png", 32, 32, 3, 32, 0, 0);
    public static final Texture LINE_ES_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 6, 0);
    public static final Texture LINE_EN_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 6 , 6);
    public static final Texture LINE_SE_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 0, 6);
    public static final Texture LINE_NE_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 0, 0);

    public static final Texture LINE_EE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 32, 3, 0, 12);
    public static final Texture LINE_NN = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 32, 32, 3, 32, 3, 0);
    public static final Texture LINE_ES = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 15, 0);
    public static final Texture LINE_EN = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 15 , 6);
    public static final Texture LINE_SE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 9, 6);
    public static final Texture LINE_NE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 9, 0);

    public static void renderLine(PoseStack stack, int fromX, int fromY, int toX, int toY, int blitOffset, int color) {
        float[] rgb = new float[4];
        unpackRGB(color, rgb);

        if (toX >= fromX + 6) {
            int halfWidth = (toX - (fromX + 3)) / 2;
            if (toY <= fromY - 1) {
                // Up and to the right
                renderLinePiece(LINE_EE, stack, fromX, fromY, blitOffset, halfWidth, 3, rgb);
                renderLinePiece(LINE_EN, stack, fromX + halfWidth, fromY, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, fromX + halfWidth, toY, blitOffset, 3, fromY - toY, rgb);
                renderLinePiece(LINE_NE, stack, fromX + halfWidth, toY, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, fromX + halfWidth + 3, toY, blitOffset, ((toX - (fromX + 3)) - halfWidth), 3, rgb);
            } else if (toY >= fromY + 1){
                // Down and to the right
                renderLinePiece(LINE_EE, stack, fromX, fromY, blitOffset, halfWidth, 3, rgb);
                renderLinePiece(LINE_ES, stack, fromX + halfWidth, fromY, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, fromX + halfWidth, fromY + 3, blitOffset, 3, toY - (fromY + 3), rgb);
                renderLinePiece(LINE_SE, stack, fromX + halfWidth, toY, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, fromX + halfWidth + 3, toY, blitOffset, ((toX - (fromX + 3)) - halfWidth), 3, rgb);
            } else {
                // Straight right
                renderLinePiece(LINE_EE, stack, fromX, fromY, blitOffset, halfWidth + 1, 3, rgb);
                renderLinePiece(LINE_EE, stack, fromX + halfWidth + 1, toY, blitOffset, (toX - (fromX + 3)) - halfWidth, 3, rgb);
            }
        } else {
            if (toY <= fromY - 9) {
                // Up and to the left
                int halfHeight = (fromY - toY) / 2;
                renderLinePiece(LINE_EE, stack, fromX, fromY, blitOffset, 3, 3, rgb);
                renderLinePiece(LINE_EN, stack, fromX + 3, fromY, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, fromX + 3, fromY - halfHeight, blitOffset, 3, halfHeight, rgb);
                renderLinePiece(LINE_ES, stack, fromX + 3, fromY - halfHeight - 3, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, toX - 3, fromY - halfHeight - 3, blitOffset, fromX + 6 - toX, 3, rgb);
                renderLinePiece(LINE_SE, stack, toX - 6, fromY - halfHeight - 3, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, toX - 6, toY + 3, blitOffset, 3, fromY - toY - 6 - halfHeight, rgb);
                renderLinePiece(LINE_NE, stack, toX - 6, toY, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, toX - 3, toY, blitOffset, 3, 3, rgb);
            } else if (toY >= fromY + 9){
                // Down and to the left
                int halfHeight = (toY - fromY) / 2;
                renderLinePiece(LINE_EE, stack, fromX, fromY, blitOffset, 3, 3, rgb);
                renderLinePiece(LINE_ES, stack, fromX + 3, fromY, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, fromX + 3, fromY + 3, blitOffset, 3, halfHeight, rgb);
                renderLinePiece(LINE_EN, stack, fromX + 3, fromY + 3 + halfHeight, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, toX - 3, fromY + 3 + halfHeight, blitOffset, fromX + 6 - toX, 3, rgb);
                renderLinePiece(LINE_NE, stack, toX - 6, fromY + 3 + halfHeight, blitOffset, rgb);
                renderLinePiece(LINE_NN, stack, toX - 6, fromY + 6 + halfHeight, blitOffset, 3, toY - fromY - 6 - halfHeight, rgb);
                renderLinePiece(LINE_SE, stack, toX - 6, toY, blitOffset, rgb);
                renderLinePiece(LINE_EE, stack, toX - 3, toY, blitOffset, 3, 3, rgb);
            } else {
                // TODO: Up left down right
                renderLinePiece(LINE_EE, stack, toX, fromY, blitOffset, fromX + 3 - toX, 3, rgb);
            }
        }

//        renderLinePiece(LINE_HORIZONTAL, stack, fromX, fromY, blitOffset, rgb);
//        if (toY <= fromY - 8) {
//            renderLinePiece(LINE_SE, stack, fromX + 4, fromY - 4, blitOffset, rgb);
//            renderLinePiece(LINE_VERTICAL, stack, fromX + 4, toY + 4, blitOffset, 4, fromY - 8 - toY, rgb);
//            if (toX <= fromX + 8) {
//                renderLinePiece(LINE_NE, stack, fromX + 4, toY, blitOffset, rgb);
//                renderLinePiece(LINE_HORIZONTAL, stack, toX, toY, blitOffset, (fromX + 4 - toX), 4, rgb);
//            } else {
//                renderLinePiece(LINE_NW, stack, fromX + 4, toY, blitOffset, rgb);
//                renderLinePiece(LINE_HORIZONTAL, stack, fromX + 8, toY, blitOffset, toX - (fromX + 8), 4, rgb);
//            }
//        }
    }

    private static void renderLinePiece(Texture piece, PoseStack stack, int x, int y, int blitOffset, int width, int height, float[] rgb) {
        if (piece == LINE_EE) LINE_EE_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);
        else if (piece == LINE_NN) LINE_NN_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);
        else if (piece == LINE_ES) LINE_ES_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);
        else if (piece == LINE_EN) LINE_EN_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);
        else if (piece == LINE_SE) LINE_SE_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);
        else if (piece == LINE_NE) LINE_NE_BG.draw(stack, x, y, blitOffset, width, height, 1, 1, 1, 1, false);

        piece.draw(stack, x, y, blitOffset, width, height, rgb[0], rgb[1], rgb[2], 1, false);
    }

    private static void renderLinePiece(Texture piece, PoseStack stack, int x, int y, int blitOffset, float[] rgb) {
        if (piece == LINE_EE) LINE_EE_BG.draw(stack, x, y, blitOffset);
        else if (piece == LINE_NN) LINE_NN_BG.draw(stack, x, y, blitOffset);
        else if (piece == LINE_ES) LINE_ES_BG.draw(stack, x, y, blitOffset);
        else if (piece == LINE_EN) LINE_EN_BG.draw(stack, x, y, blitOffset);
        else if (piece == LINE_SE) LINE_SE_BG.draw(stack, x, y, blitOffset);
        else if (piece == LINE_NE) LINE_NE_BG.draw(stack, x, y, blitOffset);

        piece.draw(stack, x, y, blitOffset, rgb[0], rgb[1], rgb[2], 1);
    }

    public static void unpackRGB(int color, float[] rgb) {
        rgb[0] = ((color >> 16) & 0xFF) / 255.0F;
        rgb[1] = ((color >> 8) & 0xFF) / 255.0F;
        rgb[2] = (color & 0xFF) / 255.0F;
    }

    public static void unpackRGBA(int color, float[] rgba) {
        rgba[0] = ((color >> 16) & 0xFF) / 255f;
        rgba[1] = ((color >> 8) & 0xFF) / 255f;
        rgba[2] = (color & 0xFF) / 255f;
        rgba[3] = ((color >> 24) & 0xFF) / 255f;
    }

    public static void drawRect(PoseStack stack, Texture texture, int x, int y, int zOffset, int width, int height) {
        drawRect(stack, texture, x, y, zOffset, width, height, 0xFFFFFFFF);
    }

    /**
     * Draws a rectangle with the given texture tiled to fit the dimensions. Ex: nodes, which use a 16x16 texture.
     * See the `textures/gui/graph/node.png` file for an example.
     */
    public static void drawRect(PoseStack stack, Texture texture, int x, int y, int zOffset, int width, int height, int color) {
        int cornerWidth = texture.fileWidth() / 4;
        int cornerHeight = texture.fileHeight() / 4;

        float[] rgba = new float[4];
        unpackRGBA(color, rgba);
        texture.setup(rgba[0], rgba[1], rgba[2], rgba[3]);

        // Corners
        Screen.blit(stack, x, y, zOffset, 0, 0, cornerWidth, cornerHeight, texture.fileWidth(), texture.fileHeight());
        Screen.blit(stack, x + width - cornerWidth, y, zOffset, texture.fileWidth() - cornerWidth, 0, cornerWidth, cornerHeight, texture.fileWidth(), texture.fileHeight());
        Screen.blit(stack, x, y + height - cornerHeight, zOffset, 0, texture.fileHeight() - cornerHeight, cornerWidth, cornerHeight, texture.fileWidth(), texture.fileHeight());
        Screen.blit(stack, x + width - cornerWidth, y + height - cornerHeight, zOffset, texture.fileWidth() - cornerWidth, texture.fileHeight() - cornerHeight, cornerWidth, cornerHeight, texture.fileWidth(), texture.fileHeight());

        for (int i = cornerWidth; i < width - cornerWidth; i += cornerWidth * 2) {
            int w = Math.min(cornerWidth * 2, width - cornerWidth - i);
            // Top + bottom edges
            Screen.blit(stack, x + i, y, zOffset, cornerWidth, 0, w, cornerHeight, texture.fileWidth(), texture.fileHeight());
            Screen.blit(stack, x + i, y + height - cornerHeight, zOffset, cornerWidth, texture.fileHeight() - cornerHeight, w, cornerHeight, texture.fileWidth(), texture.fileHeight());

            // Infill
            for (int j = cornerHeight; j < height - cornerHeight; j += cornerHeight * 2) {
                int h = Math.min(cornerHeight * 2, height - cornerHeight - j);
                Screen.blit(stack, x + i, y + j, zOffset, cornerWidth, cornerHeight, w, h, texture.fileWidth(), texture.fileHeight());
            }
        }

        // Left + right edges
        for (int i = cornerHeight; i < height - cornerHeight; i += cornerHeight * 2) {
            int h = Math.min(cornerHeight * 2, height - cornerHeight - i);
            Screen.blit(stack, x, y + i, zOffset, 0, cornerHeight, cornerWidth, h, texture.fileWidth(), texture.fileHeight());
            Screen.blit(stack, x + width - cornerWidth, y + i, zOffset, texture.fileWidth() - cornerWidth, cornerHeight, cornerWidth, h, texture.fileWidth(), texture.fileHeight());
        }
    }
}
