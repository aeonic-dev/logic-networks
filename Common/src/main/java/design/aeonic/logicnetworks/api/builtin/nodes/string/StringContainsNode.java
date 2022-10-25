package design.aeonic.logicnetworks.api.builtin.nodes.string;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class StringContainsNode extends SimpleOperatorNode<StringContainsNode> {
    public StringContainsNode(NodeType<StringContainsNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.STRING_CONTAINS, BuiltinSignalTypes.STRING.arrayOf(2),
                SignalType.arrayOf(BuiltinSignalTypes.BOOLEAN, BuiltinSignalTypes.INTEGER), uuid, x, y);
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return index == 1 ? Translations.Generic.SUBSTRING : super.getInputSocketName(index);
    }

    @Nullable
    @Override
    protected Component getOutputSocketName(int index) {
        return index == 1 ? Translations.Generic.FIRST_INDEX : super.getOutputSocketName(index);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((String) inputs[0]).contains((String) inputs[1]),
                ((String) inputs[0]).indexOf((String) inputs[1])};
    }
}
