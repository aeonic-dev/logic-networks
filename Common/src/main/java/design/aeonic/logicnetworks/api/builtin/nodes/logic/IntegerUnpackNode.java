package design.aeonic.logicnetworks.api.builtin.nodes.logic;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class IntegerUnpackNode extends SimpleOperatorNode<IntegerUnpackNode> {
    public IntegerUnpackNode(NodeType<IntegerUnpackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.INTEGER_UNPACK,
                BuiltinSignalTypes.INTEGER.arrayOf(), BuiltinSignalTypes.ANALOG.arrayOf(8), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getOutputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.MOST_SIGNIFICANT;
            case 7 -> Translations.Generic.LEAST_SIGNIFICANT;
            default -> super.getOutputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        Object[] values = new Object[8];
        for (int i = 0; i < 8; i++) {
            values[i] = ((Integer) inputs[0] & (0xF << ((7 - i) << 2))) >> ((7 - i) << 2);
        }
        return values;
    }
}
