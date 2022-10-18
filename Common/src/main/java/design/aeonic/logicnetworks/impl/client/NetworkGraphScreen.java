package design.aeonic.logicnetworks.impl.client;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.builtin.BuiltinNodeTypes;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.logic.node.Node;
import design.aeonic.logicnetworks.api.screen.AbstractWidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class NetworkGraphScreen extends AbstractWidgetScreen {
    public static final Texture NODE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node.png"), 16, 16, 16, 16, 0, 0);
    public static final Texture NODE_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node_hovered.png"), 16, 16, 16, 16, 0, 0);

    private final Texture window = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/window.png"), 512, 512, 378, 231, 0, 0);
    private final Texture backgroundTile = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/background.png"), 16, 16);

    private final int boundLowerX = 9;
    private final int boundLowerY = 18;
    private final int boundUpperX = window.width() - 9;
    private final int boundUpperY = window.height() - 9;

    private final Network network;
    private final Map<UUID, Integer> layerMap = new HashMap<>();
    private final Multimap<UUID, InputWidget> nodeWidgets = HashMultimap.create();
    private final Consumer<Network> onClose;

    private int leftPos;
    private int topPos;
    private int graphX = 0;
    private int graphY = 0;
    private float graphScale = 1;

    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int dragMouseX = 0;
    private int dragMouseY = 0;
    private Node<?> dragging = null;
    private boolean graphDragging = false;

    public NetworkGraphScreen(Component title, Network network, Consumer<Network> onClose) {
        super(title);
        this.network = network;
        this.onClose = onClose;
    }

    public static void open(Network network, Consumer<Network> onClose) {
        NetworkGraphScreen screen = new NetworkGraphScreen(Translations.NetworkGraph.TITLE, network, onClose);
        Minecraft.getInstance().setScreen(screen);
    }

    @Override
    protected void init() {
        this.leftPos = (this.width - window.width()) / 2;
        this.topPos = (this.height - window.height()) / 2;

        network.addNode(BuiltinNodeTypes.ANALOG_ADD.createNode(
                UUID.randomUUID(), 0, 0));

        network.getNodes().forEach(node -> {
            int x = node.getX();
            int y = node.getY();
            node.getInputWidgets().forEach(widget -> {
                widget.setX(x + widget.getX());
                widget.setY(y + widget.getY());
                // Add to the default widget list for interactions; actually only rendered by the widget multimap
                addWidget(widget);
                nodeWidgets.put(node.getUUID(), widget);
            });
        });
    }

    @Override
    public void onClose() {
        onClose.accept(network);
        super.onClose();
    }

    @Override
    public int getLeftPos() {
        return leftPos;
    }

    @Override
    public int getTopPos() {
        return topPos;
    }

    @Override
    public boolean keyPressed(int $$0, int $$1, int $$2) {
        // Allow the inventory key to close the screen akin to a menu screen
        if (super.keyPressed($$0, $$1, $$2)) return true;
        if (minecraft.options.keyInventory.matches($$0, $$1)) {
            this.onClose();
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;

        int mx = adjustMouseX((int) mouseX);
        int my = adjustMouseY((int) mouseY);
        dragging = getNodeAt(mx, my);

        // Dragging a node
        if (dragging != null) {
            if (button == 0) {
                dragMouseX = mx - dragging.getX();
                dragMouseY = my - dragging.getY();
                layerMap.put(dragging.getUUID(), layerMap.values().stream().max(Integer::compareTo).orElse(0) + 1);
                GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_POINTING_HAND_CURSOR));
            } else {
                dragging = null;
                graphDragging = true;
                lastMouseX = mx + graphX;
                lastMouseY = my + graphY;
                GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_RESIZE_ALL_CURSOR));
            }
            return true;
        }

        // Dragging the graph
        if (mouseX >= leftPos + boundLowerX && mouseX < leftPos + boundUpperX && mouseY >= topPos + boundLowerY && mouseY < topPos + boundUpperY) {
            graphDragging = true;
            lastMouseX = mx + graphX;
            lastMouseY = my + graphY;
            GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_RESIZE_ALL_CURSOR));
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) return true;

        if (graphDragging) {
            graphDragging = false;
            GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        }

        if (dragging != null) {
            dragging = null;
            GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        }

        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int key, double $$3, double $$4) {
        if (super.mouseDragged(mouseX, mouseY, key, $$3, $$4)) return true;

        if (key == 0 || (graphDragging && key == 1)) {
            int mx = adjustMouseX((int) mouseX);
            int my = adjustMouseY((int) mouseY);
            if (dragging != null) {
                int oldX = dragging.getX();
                int oldY = dragging.getY();
                dragging.setX(mx - dragMouseX);
                dragging.setY(my - dragMouseY);
                for (InputWidget widget : nodeWidgets.get(dragging.getUUID())) {
                    widget.setX(widget.getX() + mx - dragMouseX - oldX);
                    widget.setY(widget.getY() + my - dragMouseY - oldY);
                }
                lastMouseX = mx + graphX;
                lastMouseY = my + graphY;
                return true;
            } else if (graphDragging) {
                mx += graphX;
                my += graphY;
                graphX += mx - lastMouseX;
                graphY += my - lastMouseY;
                lastMouseX = mx;
                lastMouseY = my;
                return true;
            }
        }
        return false;
    }

    public int adjustMouseX(int mouseX) {
        return mouseX - (leftPos + window.width() / 2 + graphX);
    }

    public int adjustMouseY(int mouseY) {
        return mouseY - (topPos + window.height() / 2 + graphY);
    }

    @Override
    public InputWidget getHoveredWidget(int mouseX, int mouseY) {
        return getWidgetAt(adjustMouseX(mouseX), adjustMouseY(mouseY));
    }

    @Nullable
    @Override
    public InputWidget getWidgetAt(int x, int y) {
        InputWidget highestWidget = null;
        int highestLayer = -1;
        for (var entry : nodeWidgets.entries()) {
            InputWidget widget = entry.getValue();
            if (widget.isWithinBounds(x, y)) {
                int layer = getNodeLayer(entry.getKey());
                if (highestWidget == null || layer > highestLayer) {
                    highestWidget = widget;
                    highestLayer = layer;
                }
            }
        }

        return highestWidget;
    }

    @Nullable
    public Node<?> getNodeAt(int x, int y) {
        return network.getNodes()
                .filter(node -> node.getX() <= x && node.getY() <= y && node.getX() + node.getWidth() >= x && node.getY() + node.getHeight() >= y)
                .min((a, b) -> Integer.compare(getNodeLayer(b.getUUID()), getNodeLayer(a.getUUID())))
                .orElse(null);
    }

    public int getNodeLayer(UUID uuid) {
        return layerMap.computeIfAbsent(uuid, $ -> 0);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        double scale = minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor(
                (int) ((leftPos + boundLowerX) * scale),
                (int) (minecraft.getWindow().getHeight() - (topPos + boundUpperY) * scale),
                (int) ((boundUpperX - boundLowerX) * scale),
                (int) ((boundUpperY - boundLowerY) * scale));

        renderBackgroundTiles(stack, mouseX, mouseY, partialTick);

        stack.pushPose();
        stack.translate(leftPos + window.width() / 2 + graphX, topPos + window.height() / 2 + graphY, 0);

        List<Node<?>> nodes = network.getNodes().sorted(Comparator.comparingInt(node -> this.getNodeLayer(node.getUUID()))).toList();
        Node<?> hovered = getNodeAt(adjustMouseX(mouseX), adjustMouseY(mouseY));
        if (getWidgetAt(adjustMouseX(mouseX), adjustMouseY(mouseY)) != null) hovered = null;

        for (Node<?> node : nodes) {
            RenderUtils.drawRect(stack, node == hovered ? NODE_HOVERED : NODE, node.getX(), node.getY(), getBlitOffset(), node.getWidth(), node.getHeight());
            for (InputWidget widget : nodeWidgets.get(node.getUUID())) {
                widget.draw(stack, this, adjustMouseX(mouseX), adjustMouseY(mouseY), partialTick);
            }
        }

        stack.popPose();
//
//        // Nodes
//        stack.pushPose();
//        stack.translate(leftPos + window.width() / 2 + offsetX, topPos + window.height() / 2 + offsetY, 0);
//        renderers.keySet().forEach(node -> drawNode(node, stack, mouseX, mouseY, partialTick));
//        renderers.keySet().forEach(node -> renderConnection(stack, node));
//        stack.popPose();

//        stack.pushPose();
//        stack.translate(getLeftPos(), getTopPos(), 0);
//        for (InputWidget widget : inputWidgets) {
//            if (widget.getX)
//            widget.draw(stack, this, mouseX - getLeftPos(), mouseY - getTopPos(), partialTick);
//        }
//        stack.popPose();
//
//        InputWidget hovered = getWidgetAt(mouseX - getLeftPos(), mouseY - getTopPos());
//        if (hovered != null) {
//            var tooltip = hovered.getTooltip(this, mouseX - getLeftPos(), mouseY - getTopPos());
//            if (tooltip != null) renderTooltip(stack, mouseX, mouseY, tooltip);
//        }

        RenderSystem.disableScissor();
        renderWindow(stack, mouseX, mouseY, partialTick);
        renderLabel(stack, mouseX, mouseY, partialTick);

        InputWidget hoveredWidget = getWidgetAt(adjustMouseX(mouseX), adjustMouseY(mouseY));
        if (hoveredWidget != null) {
            var tooltip = hoveredWidget.getTooltip(this, mouseX - getLeftPos(), mouseY - getTopPos());
            if (tooltip != null) renderTooltip(stack, mouseX, mouseY, tooltip);
        }
    }

    public void renderBackgroundTiles(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        int tileCountX = Mth.ceil((float) window.width() / backgroundTile.width()) + 1;
        int tileCountY = Mth.ceil((float) window.width() / backgroundTile.height()) + 1;

        int ox = backgroundTile.width() - graphX % backgroundTile.width();
        int oy = backgroundTile.height() - graphY % backgroundTile.height();

        backgroundTile.setup(1, 1, 1, 1);
        for (int x = 0; x < tileCountX; x++) {
            for (int y = 0; y < tileCountY; y++) {
                int tileX = leftPos + boundLowerX + x * backgroundTile.fileWidth() - ox;
                int tileY = topPos + boundLowerY + y * backgroundTile.fileHeight() - oy;
                backgroundTile.draw(stack, tileX, tileY,getBlitOffset());
//                Screen.blit(stack, tx, ty, 0, 0, backgroundTile.fileWidth(), backgroundTile.fileHeight(), backgroundTile.fileWidth(), backgroundTile.fileHeight());
            }
        }
    }

    public void renderWindow(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        window.draw(stack, leftPos, topPos, 0, 1, 1, 1, 1, true);
    }

    public void renderLabel(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        font.draw(stack, title, leftPos + 8, topPos + 6, 0x404040);
    }

    //    @Override
//    public boolean mouseDragged(double $$0, double $$1, int key, double $$3, double $$4) {
//        if (key == 0) {
//            if (lastMouseX == -1 || lastMouseY == -1 || lastHoveredNode == null) {
//                lastHoveredNode = getHoveredNode((int) $$0, (int) $$1);
//                int mouseX = (int) $$0 - leftPos - window.width() / 2 - offsetX;
//                int mouseY = (int) $$1 - topPos - window.height() / 2 - offsetY;
//
//                if (lastHoveredNode != null) {
//                    Socket<?> socket = getHoveredSocket(lastHoveredNode, mouseX, mouseY);
//                    if (socket != null) {
//                        // TODO: Start socket dragging
//                        return false;
//                    } else if ($$0 >= leftPos && $$0 <= leftPos + window.width() && $$1 >= topPos && $$1 <= topPos + window.height()) {
//                        lastMouseX = (int) $$0;
//                        lastMouseY = (int) $$1;
//                        Socket<?> connectedSocket;
//                        if (lastHoveredNode.getOutputSocket() != null && (connectedSocket = lastHoveredNode.getOutputSocket().getConnectedSocket()) != null) {
//                            lastHoveredNodeMaxX = connectedSocket.node.getX() - getWidth(lastHoveredNode) - 10;
//                        } else lastHoveredNodeMaxX = 1000;
//                        lastHoveredNodeMinX = -1000;
//                        for (Socket<?> inputSocket : lastHoveredNode.getInputSockets()) {
//                            if ((connectedSocket = inputSocket.getConnectedSocket()) != null) {
//                                lastHoveredNodeMinX = Math.max(lastHoveredNodeMinX, connectedSocket.node.getX() + getWidth(connectedSocket.node) + 10);
//                            }
//                        }
//                    } else {
//                        lastHoveredNode = null;
//                        return false;
//                    }
//                } else return false;
//            } else {
//                lastHoveredNode.setX(Mth.clamp(lastHoveredNode.getX() + (int) $$0 - lastMouseX, lastHoveredNodeMinX, lastHoveredNodeMaxX));
//                lastHoveredNode.setY(lastHoveredNode.getY() + (int) $$1 - lastMouseY);
//                lastMouseX = (int) $$0;
//                lastMouseY = (int) $$1;
//            }
//            return true;
//        }
//        else if (key == 1) {
//            // Just using the last two params results in jerky/weird movement
//            if (lastMouseX == -1 || lastMouseY == -1) {
//                if ($$0 >= leftPos && $$0 <= leftPos + window.width() &&
//                        $$1 >= topPos && $$1 <= topPos + window.height()) {
//                    lastMouseX = (int) $$0;
//                    lastMouseY = (int) $$1;
//                } else return false;
//            } else {
//                offsetX += (int) $$0 - lastMouseX;
//                offsetY += (int) $$1 - lastMouseY;
//                lastMouseX = (int) $$0;
//                lastMouseY = (int) $$1;
//            }
//            return true;
//        }
//        return super.mouseDragged($$0, $$1, key, $$3, $$4);
//    }
//
//    @SuppressWarnings("unchecked")
//    <T, O extends Operator<T>> int getWidth(Node<T, O> node) {
//        return ((NodeRenderer<T, O>) renderers.get(node)).getWidth(node);
//    }
//
//    @SuppressWarnings("unchecked")
//    public <T, O extends Operator<T>> Socket<?> getHoveredSocket(Node<T, O> node, int mouseX, int mouseY) {
//        return ((NodeRenderer<T, O>) renderers.get(node)).getHoveredSocket(node, mouseX, mouseY);
//    }
//
//    @Override
//    public boolean mouseReleased(double $$0, double $$1, int $$2) {
//        if ($$2 == 0) {
//            lastHoveredNode = null;
//            lastHoveredNodeMinX = -1000;
//            lastHoveredNodeMaxX = 1000;
//            lastMouseX = -1;
//            lastMouseY = -1;
//            return true;
//        }
//        else if ($$2 == 1) {
//            lastMouseX = -1;
//            lastMouseY = -1;
//            return true;
//        }
//        return super.mouseReleased($$0, $$1, $$2);
//    }
//
//    @Override
//    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
//        double scale = minecraft.getWindow().getGuiScale();
//        RenderSystem.enableScissor((int) ((leftPos + backgroundTileOffsetX) * scale), (int) ((topPos) * scale),
//                (int) ((window.width() - backgroundTileOffsetX * 2) * scale), (int) ((window.height() - backgroundTileOffsetY) * scale));
//
//        renderBackgroundTiles(stack, mouseX, mouseY, partialTick);
//
//        // Nodes
//        stack.pushPose();
//        stack.translate(leftPos + window.width() / 2 + offsetX, topPos + window.height() / 2 + offsetY, 0);
//        renderers.keySet().forEach(node -> drawNode(node, stack, mouseX, mouseY, partialTick));
//        renderers.keySet().forEach(node -> renderConnection(stack, node));
//        stack.popPose();
//
//        RenderSystem.disableScissor();
//        renderWindow(stack, mouseX, mouseY, partialTick);
//        renderLabel(stack, mouseX, mouseY, partialTick);
//    }
//
//    @SuppressWarnings("unchecked")
//    protected <T, O extends Operator<T>> void renderConnection(PoseStack stack, Node<T, O> node) {
//        NodeRenderer<T, O> renderer = (NodeRenderer<T, O>) renderers.get(node);
//        Socket<T> socket = node.getOutputSocket();
//
//        if (node.getOutputSocket() != null && socket.getConnectedSocket() != null) {
//            Socket<?> destSocket = socket.getConnectedSocket();
//            Node<?, ?> destNode = destSocket.node;
//            IntIntPair destOffset = getSocketOffset(destNode, destSocket.side, destSocket.index);
//
//            // TODO: More readable socket definitions with width to avoid the hardcoded offsets here + elsewhere
//            int x1 = node.getX() + renderer.getSocketOffsetX(node, Socket.Side.OUTPUT, 0) + 5;
//            int y1 = node.getY() + renderer.getSocketOffsetY(node, Socket.Side.OUTPUT, 0) + 1;
//            int x2 = destOffset.firstInt() + destNode.getX() + 1;
//            int y2 = destOffset.secondInt() + destNode.getY() + 1;
//
//            drawLine(stack, x1, y1, x2, y2, socket.signalType.color);
//        }
//    }
}
