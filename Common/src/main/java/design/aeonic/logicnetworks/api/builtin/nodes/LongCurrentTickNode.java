package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AbstractSourceNode;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class LongCurrentTickNode extends AbstractSourceNode<LongCurrentTickNode> {
    public LongCurrentTickNode(NodeType<LongCurrentTickNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(getName()) + 12;
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Override
    public Component getName() {
        return Translations.Nodes.CURRENT_TICK;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.LONG.arrayOf();
    }

    @Override
    public Object[] get(NetworkController controller) {
        return new Object[]{controller.getControllerLevel().getGameTime()};
    }
}
