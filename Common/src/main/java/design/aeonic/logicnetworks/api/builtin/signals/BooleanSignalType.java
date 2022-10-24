package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public class BooleanSignalType extends CacheWritableSignalType<Boolean> {
    public BooleanSignalType(int color) {
        super(Boolean.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.BOOLEAN;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, Boolean value) {
        anchor.setRedstone(side, value ? 15 : 0);
    }

    @Override
    public Boolean read(NetworkAnchor anchor, Direction side) {
        return anchor.getAnchorLevel().getSignal(anchor.getAnchorPos().relative(side), side.getOpposite()) > 0;
    }

    @Override
    public void writeValue(CompoundTag tag, Boolean value) {
        tag.putBoolean("Boolean", value);
    }

    @Override
    public Boolean readValue(CompoundTag tag) {
        if (!tag.contains("Boolean", Tag.TAG_BYTE)) return null;
        return tag.getBoolean("Boolean");
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other.is(BuiltinSignalTypes.ANALOG);
    }

    @Override
    public <S> S convert(Boolean value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.ANALOG)) return type.cast(value ? 15 : 0);
        return super.convert(value, type);
    }
}
