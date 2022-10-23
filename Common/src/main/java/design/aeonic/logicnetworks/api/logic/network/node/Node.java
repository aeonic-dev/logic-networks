package design.aeonic.logicnetworks.api.logic.network.node;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;

public interface Node<T extends Node<T>> {

    Component getName();

    NodeType<T> getNodeType();

    /**
     * Gets this node's unique identifier.
     */
    UUID getUUID();

    /**
     * Gets the x position of this node's top left corner. Called only on the client.
     */
    int getX();

    /**
     * Gets the y position of this node's top left corner. Called only on the client.
     */
    int getY();

    /**
     * Sets the position of this node's top left corner. Called only on the client.
     */
    default void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    /**
     * Called only on the client.
     */
    void setX(int x);

    /**
     * Called only on the client.
     */
    void setY(int y);

    /**
     * Used for UI interactions on the client; add any custom input widgets here to receive interaction events.
     */
    default List<InputWidget> getInputWidgets() {
        return List.of();
    }

    /**
     * Gets the width of this node in pixels. Called only on the client.
     */
    int getWidth();

    /**
     * Gets the height of this node in pixels. Called only on the client.
     */
    int getHeight();

    default List<Component> getSocketTooltip(boolean isOutput, int index) {
        return List.of(getSocketTooltipSignalComponent(isOutput, index));
    }

    default Component getSocketTooltipSignalComponent(boolean isOutput, int index) {
        return (isOutput ? getOutputSlots() : getInputSlots())[index].getSocketTooltip(isOutput);
    }

    /**
     * Gets the y positions of input slots, respective to the return of {@link #getInputSlots()}. The node itself does not handle drawing these slots or the connections
     * to them; the network graph screen will take care of it for you.
     */
    int[] getInputPositions();

    /**
     * Gets the y positions of output slots, respective to the return of {@link #getOutputSlots()}.. The node itself does not handle drawing these slots or the connections
     * to them; the network graph screen will take care of it for you.
     */
    int[] getOutputPositions();

    /**
     * Gets input socket types for this node. Can be empty or null if none.
     */
    SignalType<?>[] getInputSlots();

    /**
     * Gets output socket types for this node. Can be empty or null if none.
     */
    SignalType<?>[] getOutputSlots();

    default void saveAdditional(CompoundTag tag) {}

    default void readAdditional(CompoundTag tag) {}

    /**
     * Called on the server after the network is reconstructed from a client's modification packet or from disk.
     * As an example, this is used by anchor nodes to check their link status.
     */
    default void loadOnServer(NetworkController controller) {}

}
