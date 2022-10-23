package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.client.screen.input.widgets.IntInputWidget;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AbstractSourceNode;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;

public class IntegerConstantNode extends AbstractSourceNode<IntegerConstantNode> {

    protected IntInputWidget input;
    protected int value;

    public IntegerConstantNode(NodeType<IntegerConstantNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(input = new IntInputWidget(6, 15, Integer.MIN_VALUE, Integer.MAX_VALUE, 11, true, 100, value) {
            @Override
            public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
                return List.of(Translations.Generic.CONSTANT);
            }
        });
    }

    @Override
    public Component getName() {
        return Translations.Nodes.ANALOG_CONSTANT;
    }

    @Override
    public int getWidth() {
        return Math.max(Minecraft.getInstance().font.width(getName()), input.getWidth()) + 12;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.INTEGER.arrayOf();
    }

    @Override
    public Object[] get(NetworkController controller) {
        return new Object[]{value};
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        value = tag.getInt("Value");
        if (input != null) input.setValue(value);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (input != null) value = input.getValue();
        tag.putInt("Value", value);
    }
}
