package design.aeonic.logicnetworks.api.builtin.nodes.math;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.client.screen.input.widgets.CheckboxInputWidget;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomNode extends SimpleOperatorNode<RandomNode> {
    private final Random random = new Random();

    protected CheckboxInputWidget tickSeedCheckbox;
    protected boolean tickSeed = true;

    public RandomNode(NodeType<RandomNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, Translations.Nodes.RANDOM, BuiltinSignalTypes.LONG.arrayOf(),
                SignalType.arrayOf(BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.BOOLEAN, BuiltinSignalTypes.INTEGER), uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(tickSeedCheckbox = new CheckboxInputWidget(6, 15, tickSeed) {
            @Override
            public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
                return List.of(Translations.Generic.TICK_SEED);
            }
        });
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return index == 0 ? Translations.Generic.SEED : super.getInputSocketName(index);
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

    private long getSeed(Long inputSeed) {
        // TODO: Need some way to access game time here to use actual ticks
        return (uuid.getMostSignificantBits()) * inputSeed * (tickSeed ? Util.getMillis() : 1);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (tickSeedCheckbox != null) tickSeed = tickSeedCheckbox.getValue();
        tag.putBoolean("TickSeed", tickSeed);
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        tickSeed = tag.getBoolean("TickSeed");
        if (tickSeedCheckbox != null) tickSeedCheckbox.setValue(tickSeed);
    }
}
