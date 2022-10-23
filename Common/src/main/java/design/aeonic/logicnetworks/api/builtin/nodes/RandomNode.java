package design.aeonic.logicnetworks.api.builtin.nodes;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class RandomNode extends SimpleOperatorNode<RandomNode> {
    private final Random random = new Random();

    public RandomNode(NodeType<RandomNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.RANDOM, BuiltinSignalTypes.LONG.arrayOf(),
                SignalType.arrayOf(BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.BOOLEAN, BuiltinSignalTypes.INTEGER), uuid, x, y);
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return index == 0 ? Translations.Generic.SEED : super.getInputSocketName(index);
    }

    private long getSeed(Long inputSeed) {
        return (uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits()) + inputSeed;
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        random.setSeed(getSeed((Long) inputs[0]));
        return new Object[] {
                random.nextInt(16),
                random.nextBoolean(),
                random.nextInt()
        };
    }
}
