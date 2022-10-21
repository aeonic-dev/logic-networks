package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSourceNode;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class BooleanReadNode extends AnchorSourceNode<BooleanReadNode> {
    public BooleanReadNode(NodeType<BooleanReadNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public Component getName() {
        return Translations.Nodes.BOOLEAN_READ;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.BOOLEAN.arrayOf();
    }
}
