package design.aeonic.logicnetworks.api.builtin.nodes.logic;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class IntegerPackNode extends SimpleOperatorNode<IntegerPackNode> {
    public IntegerPackNode(NodeType<IntegerPackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.INTEGER_PACK,
                BuiltinSignalTypes.ANALOG.arrayOf(8), BuiltinSignalTypes.INTEGER.arrayOf(), uuid, x, y);
    }

    @Override
    public boolean validate(Object[] inputs) {
        for (Object object : inputs) {
            if (object != null) return true;
        }
        return false;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.MOST_SIGNIFICANT;
            case 7 -> Translations.Generic.LEAST_SIGNIFICANT;
            default -> super.getInputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        int value = 0;
        for (int i = 0; i < 8; i++) {
            if (inputs[i] != null) value |= (((Integer) inputs[i] & 0xF) << ((7 - i) << 2));
        }
        return new Object[] {value};
    }
}
