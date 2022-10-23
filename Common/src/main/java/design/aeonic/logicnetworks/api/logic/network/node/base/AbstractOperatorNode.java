package design.aeonic.logicnetworks.api.logic.network.node.base;

import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.OperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class AbstractOperatorNode<T extends AbstractOperatorNode<T>> implements OperatorNode<T> {
    protected final NodeType<T> nodeType;
    protected final UUID uuid;
    protected int[] defaultInputPositions;
    protected int[] defaultOutputPositions;

    protected int x;
    protected int y;

    public AbstractOperatorNode(NodeType<T> nodeType, UUID uuid, int x, int y) {
        this.nodeType = nodeType;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    @Nullable
    protected Component getInputSocketName(int index) {
        return null;
    }

    @Nullable
    protected Component getOutputSocketName(int index) {
        return null;
    }

    @Override
    public List<Component> getSocketTooltip(boolean isOutput, int index) {
        Component socket = isOutput ? getOutputSocketName(index) : getInputSocketName(index);
        return socket == null ? OperatorNode.super.getSocketTooltip(isOutput, index) : List.of(socket, getSocketTooltipSignalComponent(isOutput, index));
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
    public int[] getInputPositions() {
        if (defaultInputPositions == null) {
            defaultInputPositions = new int[getInputSlots().length];
            for (int i = 0; i < getInputSlots().length; i++) {
                defaultInputPositions[i] = getHeight() / (getInputSlots().length + 1) * (i + 1) + 1;
            }
        }
        return defaultInputPositions;
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
