package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;

import java.util.UUID;

public class AnalogSubtractNode extends AnalogNode<AnalogSubtractNode> {

    public AnalogSubtractNode(NodeType<AnalogSubtractNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_SUBTRACT,
                BuiltinSignalTypes.ANALOG.arrayOf(2),
                BuiltinSignalTypes.ANALOG.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluateInternal(Object[] inputs) {
        return new Object[]{(Integer) inputs[0] - (Integer) inputs[1]};
    }
}
