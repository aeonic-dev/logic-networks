package design.aeonic.logicnetworks.api.builtin.nodes.math;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class LongPackNode extends SimpleOperatorNode<LongPackNode> {
    public LongPackNode(NodeType<LongPackNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.LONG_PACK,
                BuiltinSignalTypes.INTEGER.arrayOf(2), BuiltinSignalTypes.LONG.arrayOf(), uuid, x, y);
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 0 -> Translations.Generic.MOST_SIGNIFICANT;
            case 1 -> Translations.Generic.LEAST_SIGNIFICANT;
            default -> super.getInputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[] {((long) (int) inputs[0] << 32) | ((int) inputs[1] & 0xFFFFFFFFL)};
    }
}
