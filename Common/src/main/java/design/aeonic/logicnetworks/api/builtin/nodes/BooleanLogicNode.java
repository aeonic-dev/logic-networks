package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public class BooleanLogicNode extends SimpleOperatorNode<BooleanLogicNode> {
    public final Function<Boolean[], Boolean[]> function;

    public BooleanLogicNode(NodeType<BooleanLogicNode> nodeType, Component name, int inputSize, int outputSize, Function<Boolean[], Boolean[]> function, UUID uuid, int x, int y) {
        super(nodeType, name, BuiltinSignalTypes.BOOLEAN.arrayOf(inputSize), BuiltinSignalTypes.BOOLEAN.arrayOf(outputSize), uuid, x, y);
        this.function = function;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return function.apply(Arrays.copyOf(inputs, inputs.length, Boolean[].class));
    }

    public static class Not extends BooleanLogicNode {
        public Not(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_INVERT, 1, 1, inputs -> new Boolean[]{!inputs[0]}, uuid, x, y);
        }
    }

    public static class And extends BooleanLogicNode {
        public And(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_AND, 2, 1, inputs -> new Boolean[]{inputs[0] && inputs[1]}, uuid, x, y);
        }
    }

    public static class Or extends BooleanLogicNode {
        public Or(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_OR, 2, 1, inputs -> new Boolean[]{inputs[0] || inputs[1]}, uuid, x, y);
        }
    }

    public static class Xor extends BooleanLogicNode {
        public Xor(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_XOR, 2, 1, inputs -> new Boolean[]{inputs[0] ^ inputs[1]}, uuid, x, y);
        }
    }

    public static class Nand extends BooleanLogicNode {
        public Nand(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_NAND, 2, 1, inputs -> new Boolean[]{!(inputs[0] && inputs[1])}, uuid, x, y);
        }
    }

    public static class Nor extends BooleanLogicNode {
        public Nor(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_NOR, 2, 1, inputs -> new Boolean[]{!(inputs[0] || inputs[1])}, uuid, x, y);
        }
    }

    public static class Xnor extends BooleanLogicNode {
        public Xnor(NodeType<BooleanLogicNode> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.BOOLEAN_XNOR, 2, 1, inputs -> new Boolean[]{!(inputs[0] ^ inputs[1])}, uuid, x, y);
        }
    }
}
