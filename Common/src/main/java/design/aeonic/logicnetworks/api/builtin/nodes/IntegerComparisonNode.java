package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public abstract class IntegerComparisonNode extends SimpleOperatorNode<IntegerComparisonNode> {
    public IntegerComparisonNode(NodeType<IntegerComparisonNode> nodeType, Component name, UUID uuid, int x, int y) {
        super(nodeType, name, BuiltinSignalTypes.INTEGER.arrayOf(2), BuiltinSignalTypes.BOOLEAN.arrayOf(), uuid, x, y);
    }

    @Override
    public final Object[] evaluate(Object[] inputs) {
        return new Object[]{evaluateInternal((Integer) inputs[0], (Integer) inputs[1])};
    }

    public abstract Boolean evaluateInternal(Integer a, Integer b);

    public static class Equals extends IntegerComparisonNode {
        public Equals(NodeType<IntegerComparisonNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.INTEGER_EQUALS, uuid, x, y);
        }

        @Override
        public Boolean evaluateInternal(Integer a, Integer b) {
            return a.equals(b);
        }
    }

    public static class GreaterThan extends IntegerComparisonNode {
        public GreaterThan(NodeType<IntegerComparisonNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.INTEGER_GREATER_THAN, uuid, x, y);
        }

        @Override
        public Boolean evaluateInternal(Integer a, Integer b) {
            return a > b;
        }
    }

    public static class LessThan extends IntegerComparisonNode {
        public LessThan(NodeType<IntegerComparisonNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.INTEGER_LESS_THAN, uuid, x, y);
        }

        @Override
        public Boolean evaluateInternal(Integer a, Integer b) {
            return a < b;
        }
    }
}
