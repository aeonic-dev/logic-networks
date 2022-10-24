package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class AnalogPackNode extends SimpleOperatorNode<AnalogPackNode> {
    public AnalogPackNode(NodeType<AnalogPackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_PACK,
                BuiltinSignalTypes.BOOLEAN.arrayOf(4), BuiltinSignalTypes.ANALOG.arrayOf(), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.BIT_THREE;
            case 1 -> Translations.Generic.BIT_TWO;
            case 2 -> Translations.Generic.BIT_ONE;
            case 3 -> Translations.Generic.BIT_ZERO;
            default -> super.getInputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((Boolean) inputs[0] ? 8 : 0) + ((Boolean) inputs[1] ? 4 : 0) + ((Boolean) inputs[2] ? 2 : 0) + ((Boolean) inputs[3] ? 1 : 0)};
    }
}
