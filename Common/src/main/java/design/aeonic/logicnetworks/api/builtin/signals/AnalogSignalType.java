package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AnalogSignalType extends SignalType<Integer> {
    public AnalogSignalType(int color) {
        super(Integer.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.ANALOG;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, Integer value) {
        Level level = anchor.getAnchorLevel();
        BlockPos pos = anchor.getAnchorPos();
        anchor.setRedstone(side, value);
    }

    @Override
    public Integer read(NetworkAnchor anchor, Direction side) {
        Level level = anchor.getAnchorLevel();
        BlockPos relative = anchor.getAnchorPos().relative(side);
        BlockState relativeState = level.getBlockState(relative);
        return relativeState.hasAnalogOutputSignal() ? relativeState.getAnalogOutputSignal(level, relative) : level.getSignal(relative, side.getOpposite());
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other.is(BuiltinSignalTypes.BOOLEAN) || other.is(BuiltinSignalTypes.INTEGER);
    }

    @Override
    public <S> S convert(Integer value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.BOOLEAN)) return type.cast(value > 0);
        if (type.is(BuiltinSignalTypes.INTEGER)) return type.cast(value);
        return super.convert(value, type);
    }
}
