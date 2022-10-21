package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.NetworkAnchor;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AnalogSignalType extends SignalType<Integer> {
    public AnalogSignalType(int color) {
        super(Integer.class, color);
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, Integer value) {
        Level level = anchor.getLevel();
        BlockPos pos = anchor.getBlockPos();
        anchor.setRedstone(side, value, Block.UPDATE_ALL);
    }

    @Override
    public Integer read(NetworkAnchor anchor, Direction side) {
        Level level = anchor.getLevel();
        BlockPos pos = anchor.getBlockPos();
        BlockState state = level.getBlockState(pos);
        return state.hasAnalogOutputSignal() ? state.getAnalogOutputSignal(level, pos) : level.getSignal(pos, side);
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
