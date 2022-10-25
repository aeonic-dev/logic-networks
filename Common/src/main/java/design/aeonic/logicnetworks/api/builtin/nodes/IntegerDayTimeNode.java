package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AbstractSourceNode;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;

public class IntegerDayTimeNode extends AbstractSourceNode<IntegerDayTimeNode> {
    public IntegerDayTimeNode(NodeType<IntegerDayTimeNode> nodeType, UUID uuid, int x, int y) {
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
        return Translations.Nodes.DAY_TIME;
    }

    @Override
    public List<Component> getSocketTooltip(boolean isOutput, int index) {
        return List.of(Translations.Generic.TIME_OF_DAY, getSocketTooltipSignalComponent(isOutput, index));
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.INTEGER.arrayOf();
    }

    @Override
    public Object[] get(NetworkController controller) {
        return new Object[]{(int) controller.getControllerLevel().dayTime()};
    }
}
