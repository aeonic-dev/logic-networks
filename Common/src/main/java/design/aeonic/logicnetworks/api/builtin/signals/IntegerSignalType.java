package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class IntegerSignalType extends SignalType<Integer> {
    public IntegerSignalType(int color) {
        super(Integer.class, color);
    }

    @Override
    public List<Component> getSocketTooltip(boolean isOutput) {
        return List.of(Translations.Signals.INTEGER);
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, Integer value) {
        Level level = anchor.getAnchorLevel();
        BlockPos pos = anchor.getAnchorPos();
        anchor.setRedstone(side, Mth.clamp(value, 0, 15));
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
        return super.canConnect(other) || other.is(BuiltinSignalTypes.ANALOG);
    }

    @Override
    public <S> S convert(Integer value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.ANALOG)) return type.cast(Mth.clamp(value, 0, 15));
        return super.convert(value, type);
    }
}
