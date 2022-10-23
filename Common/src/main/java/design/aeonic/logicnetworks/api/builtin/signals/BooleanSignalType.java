package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.NetworkAnchor;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.Direction;

public class BooleanSignalType extends SignalType<Boolean> {
    public BooleanSignalType(int color) {
        super(Boolean.class, color);
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
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other.is(BuiltinSignalTypes.ANALOG);
    }

    @Override
    public <S> S convert(Boolean value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.ANALOG)) return type.cast(value ? 15 : 0);
        return super.convert(value, type);
    }
}
