package design.aeonic.logicnetworks.api.builtin.redstone;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.SignalType;

import javax.annotation.Nullable;

/**
 * Redstone signal type, values between 0 and 15.
 * Vanilla uses int values; we use bytes. Performance, maybe? We'll see.
 */
public final class BooleanSignalType extends SignalType<Boolean> {
    public BooleanSignalType() {
        super(Boolean.class, 0x0000FF);
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return other == this || other == BuiltinSignalTypes.ANALOG;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <S> S convert(Boolean value, SignalType<S> type) {
        if (type == BuiltinSignalTypes.ANALOG) {
            return (S) (Integer) (value ? 15 : 0);
        }
        return super.convert(value, type);
    }
}
