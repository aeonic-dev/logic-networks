package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.NetworkAnchor;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AnalogSignalType extends SignalType<Integer> {
    public AnalogSignalType(int color) {
        super(Integer.class, color);
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
        BlockPos pos = anchor.getAnchorPos();
        BlockPos relative = pos.relative(side);
        BlockState relativeState = level.getBlockState(relative);
        return relativeState.hasAnalogOutputSignal() ? relativeState.getAnalogOutputSignal(level, relative) : level.getSignal(pos, side);
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other == BuiltinSignalTypes.BOOLEAN;
    }

    @Override
    public <S> S convert(Integer value, SignalType<S> type) {
        if (type == BuiltinSignalTypes.BOOLEAN) return type.cast(value > 0);
        return super.convert(value, type);
    }
}
