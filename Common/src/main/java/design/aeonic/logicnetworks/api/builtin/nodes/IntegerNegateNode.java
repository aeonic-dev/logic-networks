package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;

import java.util.UUID;

public class IntegerNegateNode extends SimpleOperatorNode<IntegerNegateNode> {
    public IntegerNegateNode(NodeType<IntegerNegateNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.INTEGER_NEGATE, BuiltinSignalTypes.INTEGER.arrayOf(), BuiltinSignalTypes.INTEGER.arrayOf(), uuid, x, y);
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{-(Integer) inputs[0]};
    }
}
