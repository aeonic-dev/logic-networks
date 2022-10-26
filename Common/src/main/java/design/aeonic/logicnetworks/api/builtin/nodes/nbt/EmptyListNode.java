package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AbstractSourceNode;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class EmptyListNode extends AbstractSourceNode<EmptyListNode> {
    private static final SignalType<?>[] outputs = BuiltinSignalTypes.LIST.arrayOf();

    public EmptyListNode(NodeType<EmptyListNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public Component getName() {
        return Translations.Nodes.EMPTY_LIST;
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(getName());
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return outputs;
    }

    @Override
    public Object[] get(NetworkController controller) {
        return new Object[]{new ListTag()};
    }
}
