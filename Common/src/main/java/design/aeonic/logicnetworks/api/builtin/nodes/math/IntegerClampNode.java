package design.aeonic.logicnetworks.api.builtin.nodes.math;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.UUID;

public class IntegerClampNode extends SimpleOperatorNode<IntegerClampNode> {
    public IntegerClampNode(NodeType<IntegerClampNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.INTEGER_CLAMP, BuiltinSignalTypes.INTEGER.arrayOf(3), BuiltinSignalTypes.INTEGER.arrayOf(), uuid, x, y);
    }

    @Override
    public int getHeight() {
        return 25;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 1 -> Translations.Generic.MIN_VALUE;
            case 2 -> Translations.Generic.MAX_VALUE;
            default -> super.getInputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{Mth.clamp((Integer) inputs[0], (Integer) inputs[1], (Integer) inputs[2])};
    }
}
