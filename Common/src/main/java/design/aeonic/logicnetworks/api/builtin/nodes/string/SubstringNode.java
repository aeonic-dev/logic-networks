package design.aeonic.logicnetworks.api.builtin.nodes.string;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class SubstringNode extends SimpleOperatorNode<SubstringNode> {
    public SubstringNode(NodeType<SubstringNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.STRING_SUBSTRING,
                SignalType.arrayOf(BuiltinSignalTypes.STRING, BuiltinSignalTypes.INTEGER, BuiltinSignalTypes.INTEGER),
                BuiltinSignalTypes.STRING.arrayOf(), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 1 -> Translations.Generic.START_INDEX;
            case 2 -> Translations.Generic.END_INDEX;
            default -> super.getInputSocketName(index);
        };
    }

    @Nullable
    @Override
    protected Component getOutputSocketName(int index) {
        return Translations.Generic.SUBSTRING;
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{
                ((String) inputs[0]).substring((Integer) inputs[1], (Integer) inputs[2])};
    }
}
