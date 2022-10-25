package design.aeonic.logicnetworks.api.logic.network.node.base;

import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public abstract class SimpleOperatorNode<T extends SimpleOperatorNode<T>> extends AbstractOperatorNode<T> {
    protected Component name;
    protected SignalType<?>[] inputSlots;
    protected SignalType<?>[] outputSlots;

    public SimpleOperatorNode(NodeType<T> nodeType, Component name, SignalType<?>[] inputSlots, SignalType<?>[] outputSlots, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
        this.name = name;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(getName()) + 12;
    }

    @Override
    public int getHeight() {
        int maxSlots = Math.max(getInputSlots().length, getOutputSlots().length);
        return 19 + Math.min((maxSlots - 2), 0) * 6;
    }

    @Override
    public SignalType<?>[] getInputSlots() {
        return inputSlots;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return outputSlots;
    }
}
