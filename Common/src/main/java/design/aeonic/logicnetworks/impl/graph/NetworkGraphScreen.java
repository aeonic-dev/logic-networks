package design.aeonic.logicnetworks.impl.graph;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.client.Texture;
import design.aeonic.logicnetworks.api.graph.Network;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.graph.Socket;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class NetworkGraphScreen extends Screen {
    // FIXME Cleanup
    // TODO: Some janky rendering here but whatever man we'll fix it eventually
    // contribute pretty please? i dont wanna do it :pleading:
    // oh also most of the blit offsets are completely arbitrary numbers lmao

    public static final Texture LINE_HORIZONTAL =   new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 8, 8, 8, 4, 0, 0);
    public static final Texture LINE_VERTICAL =     new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 8, 8, 4, 8, 0, 0);
    public static final Texture LINE_RIGHT_DOWN =   new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 8, 8, 4, 4, 0, 4);
    public static final Texture LINE_RIGHT_UP =     new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 8, 8, 4, 4, 4, 4);
    public static final Texture LINE_DOWN_RIGHT =   new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 8, 8, 4, 4, 4, 0);
    public static final Texture LINE_UP_RIGHT =     new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 8, 8, 4, 4, 4, 4);

    private final Texture window = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/window.png"), 512, 512, 378, 231, 0, 0);
    private final Texture backgroundTile = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/background.png"), 16, 16);
    private final int backgroundTileOffsetX = 9;
    private final int backgroundTileOffsetY = 18;

    private Network network;
    private Map<Node<?, ?>, NodeRenderer<?, ?>> renderers;

    private int leftPos;
    private int topPos;

    private Node<?, ?> lastHoveredNode;
    private int lastHoveredNodeMinX = -1000;
    private int lastHoveredNodeMaxX = 1000;
    private int lastMouseX = -1;
    private int lastMouseY = -1;
    private int offsetX = 0;
    private int offsetY = 0;

    public NetworkGraphScreen(Component title, Network network) {
        super(title);
        this.network = network;
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - window.width()) / 2;
        this.topPos = (this.height - window.height()) / 2;

        renderers = new HashMap<>();
        network.getNodes().values().forEach(node -> renderers.put(node, node.getRenderer()));
    }

    @Override
    public boolean keyPressed(int $$0, int $$1, int $$2) {
        if (super.keyPressed($$0, $$1, $$2)) return true;
        if (minecraft.options.keyInventory.matches($$0, $$1)) {
            this.onClose();
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double $$0, double $$1, int key, double $$3, double $$4) {
        if (key == 0) {
            if (lastMouseX == -1 || lastMouseY == -1 || lastHoveredNode == null) {
                lastHoveredNode = getHoveredNode((int) $$0, (int) $$1);
                int mouseX = (int) $$0 - leftPos - window.width() / 2 - offsetX;
                int mouseY = (int) $$1 - topPos - window.height() / 2 - offsetY;

                if (lastHoveredNode != null) {
                    Socket<?> socket = getHoveredSocket(lastHoveredNode, mouseX, mouseY);
                    if (socket != null) {
                        // TODO: Start socket dragging
                        return false;
                    } else if ($$0 >= leftPos && $$0 <= leftPos + window.width() && $$1 >= topPos && $$1 <= topPos + window.height()) {
                        lastMouseX = (int) $$0;
                        lastMouseY = (int) $$1;
                        Socket<?> connectedSocket;
                        if (lastHoveredNode.getOutputSocket() != null && (connectedSocket = lastHoveredNode.getOutputSocket().getConnectedSocket()) != null) {
                            lastHoveredNodeMaxX = connectedSocket.node.getX() - getWidth(lastHoveredNode) - 10;
                        } else lastHoveredNodeMaxX = 1000;
                        lastHoveredNodeMinX = -1000;
                        for (Socket<?> inputSocket : lastHoveredNode.getInputSockets()) {
                            if ((connectedSocket = inputSocket.getConnectedSocket()) != null) {
                                lastHoveredNodeMinX = Math.max(lastHoveredNodeMinX, connectedSocket.node.getX() + getWidth(connectedSocket.node) + 10);
                            }
                        }
                    } else {
                        lastHoveredNode = null;
                        return false;
                    }
                } else return false;
            } else {
                lastHoveredNode.setX(Mth.clamp(lastHoveredNode.getX() + (int) $$0 - lastMouseX, lastHoveredNodeMinX, lastHoveredNodeMaxX));
                lastHoveredNode.setY(lastHoveredNode.getY() + (int) $$1 - lastMouseY);
                lastMouseX = (int) $$0;
                lastMouseY = (int) $$1;
            }
            return true;
        }
        else if (key == 1) {
            // Just using the last two params results in jerky/weird movement
            if (lastMouseX == -1 || lastMouseY == -1) {
                if ($$0 >= leftPos && $$0 <= leftPos + window.width() &&
                        $$1 >= topPos && $$1 <= topPos + window.height()) {
                    lastMouseX = (int) $$0;
                    lastMouseY = (int) $$1;
                } else return false;
            } else {
                offsetX += (int) $$0 - lastMouseX;
                offsetY += (int) $$1 - lastMouseY;
                lastMouseX = (int) $$0;
                lastMouseY = (int) $$1;
            }
            return true;
        }
        return super.mouseDragged($$0, $$1, key, $$3, $$4);
    }

    @SuppressWarnings("unchecked")
    <T, O extends Operator<T>> int getWidth(Node<T, O> node) {
        return ((NodeRenderer<T, O>) renderers.get(node)).getWidth(node);
    }

    @SuppressWarnings("unchecked")
    public <T, O extends Operator<T>> Socket<?> getHoveredSocket(Node<T, O> node, int mouseX, int mouseY) {
        return ((NodeRenderer<T, O>) renderers.get(node)).getHoveredSocket(node, mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double $$0, double $$1, int $$2) {
        if ($$2 == 0) {
            lastHoveredNode = null;
            lastHoveredNodeMinX = -1000;
            lastHoveredNodeMaxX = 1000;
            lastMouseX = -1;
            lastMouseY = -1;
            return true;
        }
        else if ($$2 == 1) {
            lastMouseX = -1;
            lastMouseY = -1;
            return true;
        }
        return super.mouseReleased($$0, $$1, $$2);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        double scale = minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor((int) ((leftPos + backgroundTileOffsetX) * scale), (int) ((topPos) * scale),
                (int) ((window.width() - backgroundTileOffsetX * 2) * scale), (int) ((window.height() - backgroundTileOffsetY) * scale));

        renderBackgroundTiles(stack, mouseX, mouseY, partialTick);

        // Nodes
        stack.pushPose();
        stack.translate(leftPos + window.width() / 2 + offsetX, topPos + window.height() / 2 + offsetY, 0);
        renderers.keySet().forEach(node -> drawNode(node, stack, mouseX, mouseY, partialTick));
        renderers.keySet().forEach(node -> renderConnection(stack, node));
        stack.popPose();

        RenderSystem.disableScissor();
        renderWindow(stack, mouseX, mouseY, partialTick);
        renderLabel(stack, mouseX, mouseY, partialTick);
    }

    @SuppressWarnings("unchecked")
    protected <T, O extends Operator<T>> void renderConnection(PoseStack stack, Node<T, O> node) {
        NodeRenderer<T, O> renderer = (NodeRenderer<T, O>) renderers.get(node);
        Socket<T> socket = node.getOutputSocket();

        if (node.getOutputSocket() != null && socket.getConnectedSocket() != null) {
            Socket<?> destSocket = socket.getConnectedSocket();
            Node<?, ?> destNode = destSocket.node;
            IntIntPair destOffset = getSocketOffset(destNode, destSocket.side, destSocket.index);

            // TODO: More readable socket definitions with width to avoid the hardcoded offsets here + elsewhere
            int x1 = node.getX() + renderer.getSocketOffsetX(node, Socket.Side.OUTPUT, 0) + 5;
            int y1 = node.getY() + renderer.getSocketOffsetY(node, Socket.Side.OUTPUT, 0) + 1;
            int x2 = destOffset.firstInt() + destNode.getX() + 1;
            int y2 = destOffset.secondInt() + destNode.getY() + 1;

            drawLine(stack, x1, y1, x2, y2, socket.signalType.color);
        }
    }

    @SuppressWarnings("unchecked")
    <T, O extends Operator<T>> IntIntPair getSocketOffset(Node<T, O> node, Socket.Side side, int index) {
        NodeRenderer<T, O> renderer = (NodeRenderer<T, O>) renderers.get(node);
        return IntIntPair.of(renderer.getSocketOffsetX(node, side, index), renderer.getSocketOffsetY(node, side, index));
    }

    public void renderBackgroundTiles(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        int backgroundTileCountX = Mth.ceil((float) window.width() / backgroundTile.width()) + 1;
        int backgroundTileCountY = Mth.ceil((float) window.width() / backgroundTile.height()) + 1;

        int ox = backgroundTile.width() - offsetX % backgroundTile.width();
        int oy = backgroundTile.height() - offsetY % backgroundTile.height();

        backgroundTile.setup(1, 1, 1, 1);
        for (int x = 0; x < backgroundTileCountX; x++) {
            for (int y = 0; y < backgroundTileCountY; y++) {
                int tx = leftPos + backgroundTileOffsetX + x * backgroundTile.fileWidth() - ox;
                int ty = topPos + backgroundTileOffsetY + y * backgroundTile.fileHeight() - oy;
                Screen.blit(stack, tx, ty, 0, 0, backgroundTile.fileWidth(), backgroundTile.fileHeight(), backgroundTile.fileWidth(), backgroundTile.fileHeight());
            }
        }
    }

    public void renderWindow(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        window.draw(stack, leftPos, topPos, 0, 1, 1, 1, 1, true);
    }

    public void renderLabel(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        font.draw(stack, title, leftPos + 8, topPos + 6, 0x404040);
    }

    public void drawLine(PoseStack stack, int fromX, int fromY, int toX, int toY, int color) {
        float[] rgba = new float[4];
        RenderUtils.unpackRGBA(color, rgba);

        if (fromY == toY|| Mth.abs(toY - fromY) <= 1) {
            LINE_HORIZONTAL.draw(stack, fromX, fromY, 225, toX - fromX, LINE_HORIZONTAL.height(), rgba[0], rgba[1], rgba[2], rgba[3], false);
            return;
        }

        if (toY - fromY > 0 && toY - fromY <= 2) {
            toY ++;
        } else if (fromY - toY > 0 && fromY - toY <= 2) {
            toY --;
        }

        Texture corner1, corner2;
        int halfWidth, bendX, height, vertY;

        if (fromY < toY) {
            corner1 = LINE_RIGHT_DOWN;
            corner2 = LINE_DOWN_RIGHT;
            halfWidth = (toX - fromX) / 2 - corner1.width() / 2;
            bendX = fromX + halfWidth;
            height = Math.abs(toY - fromY) - corner1.height();
            vertY = fromY + corner1.height();
        } else {
            corner1 = LINE_RIGHT_UP;
            corner2 = LINE_UP_RIGHT;
            halfWidth = (toX - fromX) / 2 - corner1.width() / 2;
            bendX = fromX + halfWidth;
            height = Math.abs(toY - fromY) - corner1.height();
            vertY = fromY - height;
        }

        LINE_HORIZONTAL.draw(stack, fromX, fromY, 600, halfWidth, LINE_HORIZONTAL.height(), rgba[0], rgba[1], rgba[2], rgba[3], false);
        corner1.draw(stack, bendX, fromY, 600, corner1.width(), corner1.height(), rgba[0], rgba[1], rgba[2], rgba[3], false);
        LINE_VERTICAL.draw(stack, bendX, vertY, 600, LINE_VERTICAL.width(), height, rgba[0], rgba[1], rgba[2], rgba[3], false);
        corner2.draw(stack, bendX, toY, 600, corner2.width(), corner2.height(), rgba[0], rgba[1], rgba[2], rgba[3], false);
        LINE_HORIZONTAL.draw(stack, bendX + corner2.width(), toY, 600, (toX - fromX) - halfWidth - corner1.width(), LINE_HORIZONTAL.height(), rgba[0], rgba[1], rgba[2], rgba[3], false);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    protected <T, O extends Operator<T>> Node<T, O> getHoveredNode(int mouseX, int mouseY) {
        for (Node<?, ?> node : renderers.keySet()) {
            if (isHovered(renderers.get(node), node, mouseX, mouseY)) {
                return (Node<T, O>) node;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected <T, O extends Operator<T>> boolean isHovered(NodeRenderer<T, O> renderer, Node<?, ?> node, int mouseX, int mouseY) {
        return renderer.isHovered((Node<T, O>) node, mouseX - leftPos - window.width() / 2 - offsetX, mouseY - topPos - window.height() / 2 - offsetY);
    }

    @SuppressWarnings("unchecked")
    protected <T, O extends Operator<T>> void drawNode(Node<T, O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        NodeRenderer<T, O> renderer = (NodeRenderer<T, O>) renderers.get(node);
        if (
                node.getX() + offsetX + renderer.getWidth(node) < leftPos - window.width() ||
                node.getX() + offsetX > leftPos + window.width() - backgroundTileOffsetX ||
                node.getY() + offsetY + renderer.getHeight(node) < topPos - window.height() / 2 ||
                node.getY() + offsetY > topPos + window.height() - backgroundTileOffsetY) {
            return;
        }
        renderer.draw(node, stack, mouseX - leftPos - window.width() / 2 - offsetX, mouseY - topPos - window.height() / 2 - offsetY, partialTicks);
    }
}
