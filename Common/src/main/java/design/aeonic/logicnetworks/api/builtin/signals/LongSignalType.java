package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

public class LongSignalType extends SignalType<Long> {
    public LongSignalType(int color) {
        super(Long.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.LONG;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, Long value) {}

    @Override
    public Long read(NetworkAnchor anchor, Direction side) {
        return null;
    }
}
