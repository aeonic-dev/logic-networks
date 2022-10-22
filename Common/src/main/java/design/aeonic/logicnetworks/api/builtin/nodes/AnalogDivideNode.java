package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;

import java.util.UUID;

public class AnalogDivideNode extends AnalogNode<AnalogDivideNode> {

    public AnalogDivideNode(NodeType<AnalogDivideNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.ANALOG_DIVIDE,
                BuiltinSignalTypes.ANALOG.arrayOf(2),
                BuiltinSignalTypes.ANALOG.arrayOf(), uuid, x, y);
    }

    @Override
    public boolean validate(Object[] inputs) {
        return super.validate(inputs) && !inputs[1].equals(0);
    }

    @Override
    public Object[] evaluateInternal(Object[] inputs) {
        return new Object[]{(Integer) inputs[0] / (Integer) inputs[1]};
    }
}
