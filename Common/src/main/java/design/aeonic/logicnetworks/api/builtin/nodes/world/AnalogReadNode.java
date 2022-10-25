package design.aeonic.logicnetworks.api.builtin.nodes.world;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSourceNode;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class AnalogReadNode extends AnchorSourceNode<AnalogReadNode> {
    public AnalogReadNode(NodeType<AnalogReadNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public Component getName() {
        return Translations.Nodes.ANALOG_READ;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return BuiltinSignalTypes.ANALOG.arrayOf();
    }
}
