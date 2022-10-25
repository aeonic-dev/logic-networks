package design.aeonic.logicnetworks.api.builtin.nodes.logic;

import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.client.screen.input.widgets.CheckboxInputWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.UUID;

public abstract class AnalogNode<T extends AnalogNode<T>> extends SimpleOperatorNode<T> {

    protected CheckboxInputWidget clampCheckbox;
    protected boolean clamp = true;

    public AnalogNode(NodeType<T> nodeType, Component name, SignalType<?>[] inputSlots, SignalType<?>[] outputSlots, UUID uuid, int x, int y) {
        super(nodeType, name, inputSlots, outputSlots, uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(clampCheckbox = new ClampCheckbox(6, 15));
    }

    @Override
    public final Object[] evaluate(Object[] inputs) {
        Object[] out = evaluateInternal(inputs);
        if (clamp) {
            for (int i = 0; i < out.length; i++) {
                out[i] = Mth.clamp((Integer) out[i], 0, 15);
            }
        }
        return out;
    }

    public abstract Object[] evaluateInternal(Object[] inputs);

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

    private class ClampCheckbox extends CheckboxInputWidget {
        public ClampCheckbox(int x, int y) {
            super(x, y, AnalogNode.this.clamp);
        }

        @Override
        public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
            return List.of(Translations.Generic.CLAMPS);
        }
    }
}
