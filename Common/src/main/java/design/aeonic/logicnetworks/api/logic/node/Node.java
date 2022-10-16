package design.aeonic.logicnetworks.api.logic.node;

import design.aeonic.logicnetworks.api.logic.NodeType;
import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;

import java.util.UUID;

public interface Node<T extends Node<T>> {

    NodeType<T> getNodeType();

    /**
     * Gets this node's unique identifier.
     */
    UUID getUuid();

    /**
     * Gets the x position of this node's top left corner.
     */
    int getX();

    /**
     * Gets the y position of this node's top left corner.
     */
    int getY();

    /**
     * Sets the position of this node's top left corner.
     * This should only be called from within a network, or otherwise in a way that updates any attached change
     * listeners.
     */
    void setPosition(int x, int y);

    /**
     * Used for UI interactions on the client; add any custom input widgets here to receive interaction events.
     */
    default void addInputWidgets(WidgetScreen screen) {}

    /**
     * Gets the width of this node in pixels.
     */
    int getWidth();

    /**
     * Gets the height of this node in pixels.
     */
    int getHeight();

    /**
     * Gets input socket types for this node. Can be empty or null if none.
     */
    SignalType<?>[] getInputSlots();

    /**
     * Gets output socket types for this node. Can be empty or null if none.
     */
    SignalType<?>[] getOutputSlots();
}
