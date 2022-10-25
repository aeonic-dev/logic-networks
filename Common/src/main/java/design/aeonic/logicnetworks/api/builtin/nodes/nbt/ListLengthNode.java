package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.ListTag;

import java.util.UUID;

public class ListLengthNode extends SimpleOperatorNode<ListLengthNode> {
    public ListLengthNode(NodeType<ListLengthNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.LIST_LENGTH, BuiltinSignalTypes.LIST.arrayOf(), BuiltinSignalTypes.INTEGER.arrayOf(), uuid, x, y);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{((ListTag) inputs[0]).size()};
    }
}
