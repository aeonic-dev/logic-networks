package design.aeonic.logicnetworks.api.client.nodes;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.client.Texture;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.graph.Socket;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * A default node renderer implementation that draws just the barebones -- no options, etc.
 */
public class BaseNodeRenderer<T, O extends Operator<T>> implements NodeRenderer<T, O> {
    // Default node textures
    public static final Texture NODE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node.png"), 16, 16);
    public static final Texture NODE_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node_hovered.png"), 16, 16);

    // Default socket textures
    public static final Texture SOCKET = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 6, 6);
    public static final Texture SOCKET_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 6, 6, 6, 0);
    public static final Texture SOCKET_BACKGROUND = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 6, 6, 0, 6);
    public static final Texture SOCKET_BACKGROUND_HOVERED = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/socket.png"), 16, 16, 6, 6, 6, 6);

    public final Component title;
    public final int width;
    public final int height;

    public BaseNodeRenderer(Component title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Node<T, O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRect(stack, boxTexture(node, mouseX, mouseY), node.getX(), node.getY(), 200, width, height);

        stack.pushPose();
        // Fixes text z-fighting with the node
        stack.translate(0, 0, 250);
        Minecraft.getInstance().font.draw(stack, title, node.getX() + 5, node.getY() + 5, textColor(node));
        stack.popPose();

        drawSockets(node, stack, mouseX, mouseY, partialTicks);
    }

    protected void drawSockets(Node<T,O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Socket<?> hovered = getHoveredSocket(node, mouseX, mouseY);
        Socket<?> socket;
        float[] rgba = new float[4];
        int x, y;

        // Draw input sockets
        for (int i = 0; i < node.getInputSockets().length; i++) {
            socket = node.getInputSockets()[i];
            x = node.getX() + getSocketOffsetX(node, Socket.Side.INPUT, i);
            y = node.getY() + getSocketOffsetY(node, Socket.Side.INPUT, i);
            RenderUtils.unpackRGBA(socket.signalType.color, rgba);
            (socket == hovered ? SOCKET_BACKGROUND_HOVERED: SOCKET_BACKGROUND).draw(stack, x, y, 300);
            (socket == hovered ? SOCKET_HOVERED: SOCKET).draw(stack, x, y, 350, rgba[0], rgba[1], rgba[2], rgba[3]);
        }

        // Draw output socket
        socket = node.getOutputSocket();
        x = node.getX() + getSocketOffsetX(node, Socket.Side.OUTPUT, 0);
        y = node.getY() + getSocketOffsetY(node, Socket.Side.OUTPUT, 0);
        RenderUtils.unpackRGBA(socket.signalType.color, rgba);
        (socket == hovered ? SOCKET_BACKGROUND_HOVERED: SOCKET_BACKGROUND).draw(stack, x, y, 300);
        (socket == hovered ? SOCKET_HOVERED: SOCKET).draw(stack, x, y, 300, rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    @Override
    public int getSocketOffsetX(Node<T, O> node, Socket.Side side, int socketIndex) {
        return side == Socket.Side.INPUT ? - SOCKET.width() / 2 + 1 : getWidth(node) - SOCKET.width() / 2 - 1;
    }

    @Override
    public int getSocketOffsetY(Node<T, O> node, Socket.Side side, int socketIndex) {
        return side == Socket.Side.INPUT ? (socketIndex) * (SOCKET.height() + 4) + 15 : (getHeight(node) - SOCKET.height()) / 2;
    }

    @Override
    public int getSocketWidth(Node<T, O> node, Socket.Side side, int socketIndex) {
        return SOCKET.width();
    }

    @Override
    public int getSocketHeight(Node<T, O> node, Socket.Side side, int socketIndex) {
        return SOCKET.height();
    }

    @Override
    public int getWidth(Node<T, O> node) {
        return width;
    }

    @Override
    public int getHeight(Node<T, O> node) {
        return height;
    }

    protected int textColor(Node<T, O> node) {
        return 0x404040;
    }

    protected Texture boxTexture(Node<T, O> node, int mouseX, int mouseY) {
        // Will only iterate over sockets if the node itself is hovered.
        return isHovered(node, mouseX, mouseY) && getHoveredSocket(node, mouseX, mouseY) == null ? NODE_HOVERED : NODE;
    }
}
