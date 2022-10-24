package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class AnalogUnpackNode extends SimpleOperatorNode<AnalogUnpackNode> {
    public AnalogUnpackNode(NodeType<AnalogUnpackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_UNPACK,
                BuiltinSignalTypes.ANALOG.arrayOf(1), BuiltinSignalTypes.BOOLEAN.arrayOf(4), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getOutputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.BIT_THREE;
            case 1 -> Translations.Generic.BIT_TWO;
            case 2 -> Translations.Generic.BIT_ONE;
            case 3 -> Translations.Generic.BIT_ZERO;
            default -> super.getOutputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        int value = (int) inputs[0];
        return new Object[]{(value & 8) != 0, (value & 4) != 0, (value & 2) != 0, (value & 1) != 0};
    }
}
