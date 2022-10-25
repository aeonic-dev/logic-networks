package design.aeonic.logicnetworks.api.builtin.nodes.string;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public class StringReplaceNode extends SimpleOperatorNode<StringReplaceNode> {
    public StringReplaceNode(NodeType<StringReplaceNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.STRING_REPLACE,
                BuiltinSignalTypes.STRING.arrayOf(3),
                BuiltinSignalTypes.STRING.arrayOf(), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch (index) {
            case 1 -> Translations.Generic.TARGET;
            case 2 -> Translations.Generic.REPLACE_WITH;
            default -> super.getInputSocketName(index);
        };
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((String) inputs[0]).replace((String) inputs[1], (String) inputs[2])};
    }
}
