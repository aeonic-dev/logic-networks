package design.aeonic.logicnetworks.impl.client;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.Edge;
import design.aeonic.logicnetworks.api.logic.network.Network;
import design.aeonic.logicnetworks.api.logic.network.node.Node;
import design.aeonic.logicnetworks.api.screen.AbstractWidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.NodeSearchWidget;
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

    public static final Texture SOCKET = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 5, 5, 0, 0);
    public static final Texture SOCKET_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 5, 5, 5, 0);
    public static final Texture SOCKET_OUTLINE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 5, 5, 0, 5);
    public static final Texture SOCKET_OUTLINE_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 5, 5, 5, 5);

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

    private int currentMouseX = 0;
    private int currentMouseY = 0;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private int dragMouseX = 0;
    private int dragMouseY = 0;
    private Node<?> dragging = null;
    private Node<?> connecting = null;
    private boolean connectingOutput = false;
    private int connectingSocket = -1;
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

    public void addNode(Node<?> node) {
        network.addNode(node);
        layerMap.put(node.getUUID(), layerMap.values().stream().min(Comparator.naturalOrder()).orElse(0) - 1);
        node.getInputWidgets().forEach(widget -> {
            widget.setX(node.getX() + widget.getX());
            widget.setY(node.getY() + widget.getY());
            // Add to the default widget list for interactions; actually only rendered by the widget multimap
            addWidget(widget);
            nodeWidgets.put(node.getUUID(), widget);
        });
    }

    @Override
    public void onClose() {
        onClose.accept(network);
        super.onClose();
    }

    @Override
    public int getRenderLeftPos() {
        return leftPos + graphX + window.width() / 2;
    }

    @Override
    public int getRenderTopPos() {
        return topPos + graphY + window.height() / 2;
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int mods) {
        // Allow the inventory key to close the screen akin to a menu screen
        if (super.keyPressed(key, scanCode, mods)) return true;
        if (minecraft.options.keyInventory.matches(key, scanCode)) {
            this.onClose();
        }
        if (NetworkKeybinds.DELETE_NODE.matches(key, scanCode)) {
            Node<?> node = getNodeAt(adjustMouseX(currentMouseX), adjustMouseY(currentMouseY));
            if (node != null) {
                network.removeNode(node.getUUID());
                network.getEdges().toList().forEach(edge -> {
                    if (edge.getFromNode().equals(node.getUUID()) || edge.getToNode().equals(node.getUUID())) {
                        network.removeEdge(edge);
                    }
                });
                layerMap.remove(node.getUUID());
                nodeWidgets.get(node.getUUID()).forEach(this::removeWidget);
                nodeWidgets.removeAll(node.getUUID());
            }
        } else if (NetworkKeybinds.ADD_NODE.matches(key, scanCode)) {
            int mx = currentMouseX - getRenderLeftPos();
            int my = currentMouseY - getRenderTopPos();
            addWidget(NodeSearchWidget.create(this, mx, my, nodeType -> {
                if (nodeType != null) addNode(nodeType.createNode(UUID.randomUUID(), mx, my));
            }, $ -> true));
        }
        return true;
    }

    @Override
    public void mouseMoved(double $$0, double $$1) {
        super.mouseMoved($$0, $$1);
        currentMouseX = (int) $$0;
        currentMouseY = (int) $$1;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!(mouseX >= leftPos + boundLowerX && mouseX < leftPos + boundUpperX && mouseY >= topPos + boundLowerY && mouseY < topPos + boundUpperY)) return false;
        if (super.mouseClicked(mouseX, mouseY, button)) return true;

        int mx = adjustMouseX((int) mouseX);
        int my = adjustMouseY((int) mouseY);

        for (Node<?> node : network.getNodes().toList()) {
            int inputSocket = getHoveredInputSocket(node, mx, my);
            if (inputSocket != -1) {
                connecting = node;
                connectingOutput = false;
                connectingSocket = inputSocket;
                return true;
            }
            int outputSocket = getHoveredOutputSocket(node, mx, my);
            if (outputSocket != -1) {
                connecting = node;
                connectingOutput = true;
                connectingSocket = outputSocket;
                return true;
            }
        }

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
        graphDragging = true;
        lastMouseX = mx + graphX;
        lastMouseY = my + graphY;
        GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_RESIZE_ALL_CURSOR));
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (super.mouseReleased(mouseX, mouseY, button)) return true;

        if (graphDragging) {
            graphDragging = false;
            GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        }

        if (button == 0) {
            if (connecting != null) {
                int mx = adjustMouseX((int) mouseX);
                int my = adjustMouseY((int) mouseY);

                boolean connected = false;
                for (Node<?> node : network.getNodes().toList()) {
                    int inputSocket = getHoveredInputSocket(node, mx, my);
                    if (inputSocket != -1 && connectingOutput) {
                        // Network validates edges for us; nothing happens if we pass an invalid edge
                        network.addEdge(Edge.of(connecting, connectingSocket, node, inputSocket));
                        connected = true;
                        break;
                    } else if (!connectingOutput) {
                        int outputSocket = getHoveredOutputSocket(node, mx, my);
                        if (outputSocket != -1) {
                            network.addEdge(Edge.of(node, outputSocket, connecting, connectingSocket));
                            connected = true;
                            break;
                        }
                    }
                }
                if (!connected) {
                    final Node<?> localConnecting = connecting;
                    final int localConnectingSocket = connectingSocket;
                    if (connectingOutput) {
                        addWidget(NodeSearchWidget.createForInput(this, mx, my, nodeType -> {
                            if (nodeType != null) {
                                var node = nodeType.createNode(UUID.randomUUID(), mx, my);
                                int index = 0;
                                for (int i = 0; i < node.getInputSlots().length; i++) {
                                    if (localConnecting.getOutputSlots()[localConnectingSocket].canConnect(node.getInputSlots()[i])) {
                                        index = i;
                                        break;
                                    }
                                }
                                addNode(node);
                                network.addEdge(Edge.of(localConnecting, localConnectingSocket, node, index));
                            }
                        }, connecting.getOutputSlots()[connectingSocket]));
                    } else {
                        addWidget(NodeSearchWidget.createForOutput(this, mx, my, nodeType -> {
                            if (nodeType != null) {
                                var node = nodeType.createNode(UUID.randomUUID(), mx, my);
                                int index = 0;
                                for (int i = 0; i < node.getOutputSlots().length; i++) {
                                    if (node.getOutputSlots()[i].canConnect(localConnecting.getInputSlots()[localConnectingSocket])) {
                                        index = i;
                                        break;
                                    }
                                }
                                addNode(node);
                                network.addEdge(Edge.of(node, index, localConnecting, localConnectingSocket));
                            }
                        }, connecting.getInputSlots()[connectingSocket]));
                    }
                }
                connecting = null;
                connectingSocket = -1;
            }

            if (dragging != null) {
                dragging = null;
                GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
            }
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
        List<InputWidget> checked = new ArrayList<>();
        InputWidget highestWidget = null;
        int highestLayer = -1;
        for (var entry : nodeWidgets.entries()) {
            InputWidget widget = entry.getValue();
            checked.add(widget);
            if (widget.isWithinBounds(x, y)) {
                int layer = getNodeLayer(entry.getKey());
                if (highestWidget == null || layer > highestLayer) {
                    highestWidget = widget;
                    highestLayer = layer;
                }
            }
        }

        for (InputWidget widget : inputWidgets.stream().filter(widget -> !checked.contains(widget)).toList()) {
            if (widget.isWithinBounds(x, y)) return widget;
        }

        Node<?> node = getNodeAt(x, y);
        if (node != null && getNodeLayer(node.getUUID()) > highestLayer) {
            return null;
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
        if (dragging != null || connecting != null) {
            if (mouseX <= leftPos + boundLowerX + 20) graphX += 5;
            if (mouseX >= leftPos + boundUpperX - 20) graphX -= 5;
            if (mouseY <= topPos + boundLowerY + 20) graphY += 5;
            if (mouseY >= topPos + boundUpperY - 20) graphY -= 5;
            mouseDragged(mouseX, mouseY, 0, 0, 0);
        }

        int mx = adjustMouseX(mouseX);
        int my = adjustMouseY(mouseY);
        double scale = minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor(
                (int) ((leftPos + boundLowerX) * scale),
                (int) (minecraft.getWindow().getHeight() - (topPos + boundUpperY) * scale),
                (int) ((boundUpperX - boundLowerX) * scale),
                (int) ((boundUpperY - boundLowerY) * scale));

        renderBackgroundTiles(stack, mouseX, mouseY, partialTick);

        stack.pushPose();
        stack.translate(leftPos + window.width() / 2 + graphX, topPos + window.height() / 2 + graphY, 0);

        // Connections
        network.getEdges().forEach(edge -> {
            Node<?> from = network.getNode(edge.getFromNode());
            Node<?> to = network.getNode(edge.getToNode());

            int fromX = from.getX() + from.getWidth();
            int fromY = from.getY() + from.getOutputPositions()[edge.getFromIndex()];
            int toX = to.getX();
            int toY = to.getY() + to.getInputPositions()[edge.getToIndex()];
            int fromColor = from.getOutputSlots()[edge.getFromIndex()].color;
            int toColor = to.getInputSlots()[edge.getToIndex()].color;

            renderConnection(stack, fromX, fromY, toX, toY, fromColor, toColor);
        });

        // Nodes
        List<InputWidget> renderedWidgets = new ArrayList<>();
        List<Node<?>> nodes = network.getNodes().sorted(Comparator.comparingInt(node -> this.getNodeLayer(node.getUUID()))).toList();
        Node<?> hoveredNode = getNodeAt(mx, my);
        InputWidget hoveredWidget = getWidgetAt(mx, my);
        // Only accept a widget belonging to the hovered node
        if (hoveredNode != null) {
            if (nodeWidgets.get(hoveredNode.getUUID()).contains(hoveredWidget)) hoveredNode = null;
            if (hoveredNode != null && (getHoveredInputSocket(hoveredNode, mx, my) != -1 || getHoveredOutputSocket(hoveredNode, mx, my) != -1)) hoveredNode = null;
            else hoveredWidget = null;
        }

        Node<?> hoveredSocketNode = null;
        boolean hoveredOutput = false;
        int hoveredSocket = -1;

        for (Node<?> node : nodes) {
            RenderUtils.drawRect(stack, node == hoveredNode ? NODE_HOVERED : NODE, node.getX(), node.getY(), getBlitOffset(), node.getWidth(), node.getHeight());
            font.draw(stack, node.getName(), node.getX() + 6, node.getY() + 5, 0x232323);

            int[] inputPositions = node.getInputPositions();
            int[] outputPositions = node.getOutputPositions();
            int inputHovered = getHoveredInputSocket(node, mx, my);
            int outputHovered = getHoveredOutputSocket(node, mx, my);
            boolean hovered;

            float[] rgba = new float[4];
            for (int i = 0; i < node.getInputSlots().length; i++) {
                RenderUtils.unpackRGBA(node.getInputSlots()[i].color, rgba);
                hovered = i == inputHovered && shouldHighlightSocket(node, false, i, mx, my);
                if (hovered) {
                    hoveredSocketNode = node;
                    hoveredOutput = false;
                    hoveredSocket = i;
                }
                renderSocket(stack, node.getX() - SOCKET.width() / 2, node.getY() + inputPositions[i] - SOCKET.height() / 2, rgba[0], rgba[1], rgba[2], rgba[3], hovered);
            }
            for (int i = 0; i < node.getOutputSlots().length; i++) {
                RenderUtils.unpackRGBA(node.getOutputSlots()[i].color, rgba);
                hovered = i == outputHovered && shouldHighlightSocket(node, true, i, mx, my);
                if (hovered) {
                    hoveredSocketNode = node;
                    hoveredOutput = true;
                    hoveredSocket = i;
                }
                renderSocket(stack, node.getX() + node.getWidth() - SOCKET.width() / 2 - 1, node.getY() + outputPositions[i] - SOCKET.height() / 2, rgba[0], rgba[1], rgba[2], rgba[3], hovered);
            }
            for (InputWidget widget : nodeWidgets.get(node.getUUID())) {
                widget.draw(stack, this, mx, my, partialTick);
                renderedWidgets.add(widget);
            }
        }

        // Dragging connection
        if (connecting != null) {
            if (connectingOutput) {
                int fromX = connecting.getX() + connecting.getWidth();
                int fromY = connecting.getY() + connecting.getOutputPositions()[connectingSocket];
                int toX = (hoveredSocketNode == null || hoveredOutput) ? mx : hoveredSocketNode.getX();
                int toY = (hoveredSocketNode == null || hoveredOutput) ? my : hoveredSocketNode.getY() + hoveredSocketNode.getInputPositions()[hoveredSocket];
                int fromColor = connecting.getOutputSlots()[connectingSocket].color;
                int toColor = (hoveredSocketNode == null || hoveredOutput) ? fromColor : hoveredSocketNode.getInputSlots()[hoveredSocket].color;
                renderConnection(stack, fromX, fromY, toX, toY, fromColor, toColor);
            } else {
                int toX = connecting.getX();
                int toY = connecting.getY() + connecting.getInputPositions()[connectingSocket];
                int fromX = (hoveredSocketNode == null || !hoveredOutput) ? mx : hoveredSocketNode.getX() + hoveredSocketNode.getWidth();
                int fromY = (hoveredSocketNode == null || !hoveredOutput) ? my : hoveredSocketNode.getY() + hoveredSocketNode.getOutputPositions()[hoveredSocket];
                int toColor = connecting.getInputSlots()[connectingSocket].color;
                int fromColor = (hoveredSocketNode == null || !hoveredOutput) ? toColor : hoveredSocketNode.getOutputSlots()[hoveredSocket].color;
                renderConnection(stack, fromX, fromY, toX, toY, fromColor, toColor);
            }
        }

        stack.popPose();

        if (hoveredWidget != null) {
            var tooltip = hoveredWidget.getTooltip(this, mx, my);
            if (tooltip != null) renderTooltip(stack, mouseX, mouseY, tooltip);
        }

        RenderSystem.disableScissor();
        renderWindow(stack, mouseX, mouseY, partialTick);
        renderLabel(stack, mouseX, mouseY, partialTick);

        stack.pushPose();
        stack.translate(leftPos + window.width() / 2 + graphX, topPos + window.height() / 2 + graphY, 0);
        for (InputWidget widget : inputWidgets.stream().filter(widget -> !renderedWidgets.contains(widget)).toList()) {
            widget.draw(stack, this, mx, my, partialTick);
        }
        stack.popPose();
    }

    public void renderConnection(PoseStack stack, int fromX, int fromY, int toX, int toY, int fromColor, int toColor) {
        RenderUtils.renderLine(stack, fromX + 2, fromY - 1, toX - 2, toY - 1, getBlitOffset(), fromColor, toColor);
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

    public boolean shouldHighlightSocket(Node<?> node, boolean isOutputSocket, int socket, int mx, int my) {
        if (connecting == null) return true;
        if (isOutputSocket) return !connectingOutput && node.getOutputSlots()[socket].canConnect(connecting.getInputSlots()[connectingSocket]);
        return connectingOutput && connecting.getOutputSlots()[connectingSocket].canConnect(node.getInputSlots()[socket]);
    }

    public int getHoveredInputSocket(Node<?> node, int mouseX, int mouseY) {
        int[] inputPositions = node.getInputPositions();
        if (!(mouseX >= node.getX() - SOCKET.width() / 2 && mouseX <= node.getX() + SOCKET.width() / 2)) return -1;

        for (int i = 0; i < node.getInputSlots().length; i++) {
            if (mouseY >= node.getY() + inputPositions[i] - SOCKET.height() / 2 && mouseY <= node.getY() + inputPositions[i] + SOCKET.height() / 2) return i;
        }
        return -1;
    }

    public int getHoveredOutputSocket(Node<?> node, int mouseX, int mouseY) {
        int[] outputPositions = node.getOutputPositions();
        if (!(mouseX >= node.getX() + node.getWidth() - SOCKET.width() / 2 - 1 && mouseX <= node.getX() + node.getWidth() + SOCKET.width() / 2 - 1)) return -1;

        for (int i = 0; i < node.getOutputSlots().length; i++) {
            if (mouseY >= node.getY() + outputPositions[i] - SOCKET.height() / 2 && mouseY <= node.getY() + outputPositions[i] + SOCKET.height() / 2) return i;
        }
        return -1;
    }

    public void renderSocket(PoseStack stack, int x, int y, float r, float g, float b, float a, boolean hovered) {
        (hovered ? SOCKET_OUTLINE_HOVERED : SOCKET_OUTLINE).draw(stack, x, y, getBlitOffset());
        (hovered ? SOCKET_HOVERED : SOCKET).draw(stack, x, y, getBlitOffset(), r, g, b, a);
    }
}
