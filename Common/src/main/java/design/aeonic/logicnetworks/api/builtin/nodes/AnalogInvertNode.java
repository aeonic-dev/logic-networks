package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;

import java.util.UUID;

public class AnalogInvertNode extends AnalogNode<AnalogInvertNode> {
    public AnalogInvertNode(NodeType<AnalogInvertNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_INVERT,
                BuiltinSignalTypes.ANALOG.arrayOf(), BuiltinSignalTypes.ANALOG.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluateInternal(Object[] inputs) {
        return new Object[]{15 - (Integer) inputs[0]};
    }
}
