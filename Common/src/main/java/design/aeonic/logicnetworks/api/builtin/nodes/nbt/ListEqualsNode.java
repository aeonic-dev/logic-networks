package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.ListTag;

import java.util.UUID;

public class ListEqualsNode extends SimpleOperatorNode<ListEqualsNode> {
    public ListEqualsNode(NodeType<ListEqualsNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.LIST_EQUALS, BuiltinSignalTypes.LIST.arrayOf(2), BuiltinSignalTypes.BOOLEAN.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((ListTag) inputs[0]).containsAll((ListTag) inputs[1])};
    }
}
