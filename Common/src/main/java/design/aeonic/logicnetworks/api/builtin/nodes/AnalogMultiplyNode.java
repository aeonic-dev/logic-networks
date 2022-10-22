package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;

import java.util.UUID;

public class AnalogMultiplyNode extends AnalogNode<AnalogMultiplyNode> {

    public AnalogMultiplyNode(NodeType<AnalogMultiplyNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_MULTIPLY,
                BuiltinSignalTypes.ANALOG.arrayOf(2),
                BuiltinSignalTypes.ANALOG.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluateInternal(Object[] inputs) {
        return new Object[]{(Integer) inputs[0] * (Integer) inputs[1]};
    }
}
