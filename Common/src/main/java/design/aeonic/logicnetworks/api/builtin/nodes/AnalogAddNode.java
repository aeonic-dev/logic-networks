package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.NodeType;
import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.api.logic.node.base.AbstractOperatorNode;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.widgets.CheckboxInputWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.UUID;

public class AnalogAddNode extends AbstractOperatorNode<AnalogAddNode> {
    private static final SignalType<?>[] inputs = new SignalType<?>[]{BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.ANALOG};
    private static final SignalType<?>[] output = new SignalType<?>[]{BuiltinSignalTypes.ANALOG};

    private CheckboxInputWidget clampCheckbox;
    private boolean clamp = true;

    public AnalogAddNode(NodeType<AnalogAddNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(clampCheckbox = new CheckboxInputWidget(6, 15, clamp) {
            @Override
            public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
                return List.of(Translations.Generic.CLAMPS);
            }
        });
    }

    @Override
    public Component getName() {
        return Translations.Nodes.ANALOG_ADD;
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
    public SignalType<?>[] getInputSlots() {
        return inputs;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return output;
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{clamp ? Mth.clamp((Integer) inputs[0] + (Integer) inputs[1], 0, 15) : (Integer) inputs[0] + (Integer) inputs[1]};
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (clampCheckbox != null) clamp = clampCheckbox.getValue();
        tag.putBoolean("clamp", clamp);
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        clamp = tag.getBoolean("clamp");
        if (clampCheckbox != null) clampCheckbox.setValue(clamp);
    }
}
