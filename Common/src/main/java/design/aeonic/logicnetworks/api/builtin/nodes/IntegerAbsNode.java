package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;

import java.util.UUID;

public class IntegerAbsNode extends SimpleOperatorNode<IntegerAbsNode> {
    public IntegerAbsNode(NodeType<IntegerAbsNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.INTEGER_ABS, BuiltinSignalTypes.INTEGER.arrayOf(), BuiltinSignalTypes.INTEGER.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{Math.abs((Integer) inputs[0])};
    }
}
