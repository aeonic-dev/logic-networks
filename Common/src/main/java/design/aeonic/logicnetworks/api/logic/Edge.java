package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.logic.node.Node;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

/**
 * An edge links a connected node and a slot index within another fromIndex's connected slots.
 */
public class Edge {
    public static final Edge EMPTY = new Edge(null, -1, null, -1);

    private final UUID fromNode;
    private final int fromIndex;
    private final UUID toNode;
    private final int toIndex;

    private Edge(UUID fromNode, int fromIndex, UUID toNode, int toIndex) {
        this.fromNode = fromNode;
        this.fromIndex = fromIndex;
        this.toNode = toNode;
        this.toIndex = toIndex;
    }

    public static Edge of(Node fromNode, int fromIndex, Node toNode, int toIndex) {
        // Validate the connection; if invalid, we return the constant empty edge to notify the network.
        if (fromNode == null || toNode == null) return EMPTY;
        if (fromIndex < 0 || toIndex < 0) return EMPTY;
        if (fromIndex >= fromNode.getOutputSlots().length || toIndex >= toNode.getInputSlots().length) return EMPTY;
        if (fromNode.getOutputSlots()[fromIndex] == null || toNode.getInputSlots()[toIndex] == null) return EMPTY;
        if (!fromNode.getOutputSlots()[fromIndex].canConnect(toNode.getInputSlots()[toIndex])) return EMPTY;

        return new Edge(fromNode.getUuid(), fromIndex, toNode.getUuid(), toIndex);
    }

    public boolean isValid() {
        return this != EMPTY;
    }

    public void serialize(CompoundTag tag) {
        if (this == EMPTY) return;
        tag.putUUID("From", fromNode);
        tag.putInt("FromIndex", fromIndex);
        tag.putUUID("To", toNode);
        tag.putInt("ToIndex", toIndex);
    }

    public static Edge deserialize(CompoundTag tag) {
        if (!tag.hasUUID("From")) return EMPTY;
        return new Edge(
                tag.getUUID("From"),
                tag.getInt("FromIndex"),
                tag.getUUID("To"),
                tag.getInt("ToIndex")
        );
    }

    public UUID getFromNode() {
        return fromNode;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public UUID getToNode() {
        return toNode;
    }

    public int getToIndex() {
        return toIndex;
    }
}
