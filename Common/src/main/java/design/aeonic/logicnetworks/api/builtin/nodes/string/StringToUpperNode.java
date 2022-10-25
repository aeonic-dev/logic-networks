package design.aeonic.logicnetworks.api.builtin.nodes.string;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;

import java.util.UUID;

public class StringToUpperNode extends SimpleOperatorNode<StringToUpperNode> {
    public StringToUpperNode(NodeType<StringToUpperNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.STRING_TO_UPPER,
                BuiltinSignalTypes.STRING.arrayOf(),
                BuiltinSignalTypes.STRING.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((String) inputs[0]).toUpperCase()};
    }
}
