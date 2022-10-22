package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AbstractSourceNode;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.widgets.CheckboxInputWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;

public class BooleanConstantNode extends AbstractSourceNode<BooleanConstantNode> {

    protected CheckboxInputWidget input;
    protected boolean value;

    public BooleanConstantNode(NodeType<BooleanConstantNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(input = new CheckboxInputWidget(6, 15, value) {
            @Override
            public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
                return List.of(Translations.Generic.CONSTANT);
            }
        });
    }

    @Override
    public Component getName() {
        return Translations.Nodes.BOOLEAN_CONSTANT;
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(getName()) + 12;
    }

    @Override
    public int getHeight() {
        return 32;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.BOOLEAN.arrayOf();
    }

    @Override
    public Object[] get(NetworkController controller) {
        return new Object[]{value};
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        value = tag.getBoolean("Value");
        if (input != null) input.setValue(value);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (input != null) value = input.getValue();
        tag.putBoolean("Value", value);
    }
}
