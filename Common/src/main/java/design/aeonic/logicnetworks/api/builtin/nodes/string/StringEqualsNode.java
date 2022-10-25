package design.aeonic.logicnetworks.api.builtin.nodes.string;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;

import java.util.UUID;

public class StringEqualsNode extends SimpleOperatorNode<StringEqualsNode> {
    public StringEqualsNode(NodeType<StringEqualsNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.STRING_EQUALS, BuiltinSignalTypes.STRING.arrayOf(2), BuiltinSignalTypes.BOOLEAN.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{(inputs[0]).equals(inputs[1])};
    }
}
