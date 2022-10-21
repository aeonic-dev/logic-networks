package design.aeonic.logicnetworks.api.logic.network.node.base;

import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.SourceNode;

import java.util.UUID;

public abstract class AbstractSourceNode<T extends AbstractSourceNode<T>> implements SourceNode<T> {
    protected final NodeType<T> nodeType;
    protected final UUID uuid;
    protected int[] defaultOutputPositions;

    protected int x;
    protected int y;

    public AbstractSourceNode(NodeType<T> nodeType, UUID uuid, int x, int y) {
        this.nodeType = nodeType;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    @Override
    public NodeType<T> getNodeType() {
        return nodeType;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int[] getOutputPositions() {
        if (defaultOutputPositions == null) {
            defaultOutputPositions = new int[getOutputSlots().length];
            for (int i = 0; i < getOutputSlots().length; i++) {
                defaultOutputPositions[i] = getHeight() / (getOutputSlots().length + 1) * (i + 1);
            }
        }
        return defaultOutputPositions;
    }
}
