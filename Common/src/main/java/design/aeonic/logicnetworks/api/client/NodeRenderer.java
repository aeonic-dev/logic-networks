package design.aeonic.logicnetworks.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.graph.Socket;
import design.aeonic.logicnetworks.api.logic.Operator;

import javax.annotation.Nullable;

/**
 * A renderer for a specific operator type, registered via
 */
public interface NodeRenderer<T, O extends Operator<T>> {
    /**
     * Draws the node. Passed positions are relative to the node's position and the posestack is already translated,
     * so you don't need to worry about screen coordinates. That is, (0, 0) is the top-left corner of the node being rendered.
     */
    void draw(Node<T, O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks);

    int getWidth(Node<T, O> node);

    int getHeight(Node<T, O> node);

    /**
     * Gets the currently hovered socket, or null if there are none being hovered.
     */
    @Nullable
    default Socket<?> getHoveredSocket(Node<T, O> node, int mouseX, int mouseY) {
        int x, y;
        for (int i = 0; i < node.getInputSockets().length; i++) {
            x = node.getX() + getSocketOffsetX(node, Socket.Side.INPUT, i);
            y = node.getY() + getSocketOffsetY(node, Socket.Side.INPUT, i);
            if (mouseX >= x && mouseX <= x + getSocketWidth(node, Socket.Side.INPUT, i) && mouseY >= y && mouseY <= y + getSocketHeight(node, Socket.Side.INPUT, i)) {
                return node.getInputSockets()[i];
            }
        }
        // Only one output socket with current implementation
        x = node.getX() + getSocketOffsetX(node, Socket.Side.OUTPUT, 0);
        y = node.getY() + getSocketOffsetY(node, Socket.Side.OUTPUT, 0);
        if (mouseX >= x && mouseX <= x + getSocketWidth(node, Socket.Side.OUTPUT, 0) && mouseY >= y && mouseY <= y + getSocketHeight(node, Socket.Side.OUTPUT, 0)) {
            return node.getOutputSocket();
        }
        return null;
    }

    /**
     * Gets the x position of the given socket relative to the node's position.
     * Used for rendering and mouse interactions (connections).
     */
    int getSocketOffsetX(Node<T, O> node, Socket.Side side, int socketIndex);

    /**
     * Gets the y position of the given socket relative to the node's position.
     * Used for rendering and mouse interactions (connections).
     */
    int getSocketOffsetY(Node<T, O> node, Socket.Side side, int socketIndex);

    int getSocketWidth(Node<T, O> node, Socket.Side side, int socketIndex);

    int getSocketHeight(Node<T, O> node, Socket.Side side, int socketIndex);

    default boolean isHovered(Node<T, O> node, int mouseX, int mouseY) {
        return  mouseX >= node.getX() && mouseX <= node.getX() + getWidth(node) &&
                mouseY >= node.getY() && mouseY <= node.getY() + getHeight(node);
    }
}
