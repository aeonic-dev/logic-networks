package design.aeonic.logicnetworks.api.builtin.nodes.world;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSinkNode;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class BooleanWriteNode extends AnchorSinkNode<BooleanWriteNode> {
    public BooleanWriteNode(NodeType<BooleanWriteNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public Component getName() {
        return Translations.Nodes.BOOLEAN_WRITE;
    }

    @Override
    public SignalType<?>[] getInputSlots() {
        return BuiltinSignalTypes.BOOLEAN.arrayOf();
    }
}
