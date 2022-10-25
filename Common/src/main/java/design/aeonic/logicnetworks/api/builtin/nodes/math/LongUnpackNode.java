package design.aeonic.logicnetworks.api.builtin.nodes.math;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class LongUnpackNode extends SimpleOperatorNode<LongUnpackNode> {
    public LongUnpackNode(NodeType<LongUnpackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.LONG_UNPACK,
                BuiltinSignalTypes.LONG.arrayOf(1), BuiltinSignalTypes.INTEGER.arrayOf(2), uuid, x, y);
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Nullable
    @Override
    protected Component getOutputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.MOST_SIGNIFICANT;
            case 1 -> Translations.Generic.LEAST_SIGNIFICANT;
            default -> super.getOutputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[] {(int) ((long) inputs[0] >> 32), (int) (long) inputs[0]};
    }
}
