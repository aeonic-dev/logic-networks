package design.aeonic.logicnetworks.impl.builtin.redstone;

import design.aeonic.logicnetworks.impl.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.SignalType;

import javax.annotation.Nullable;

/**
 * Redstone signal type, values between 0 and 15.
 * Vanilla uses int values; we use bytes. Performance, maybe? We'll see.
 */
public final class AnalogSignalType extends SignalType<Integer> {
    public AnalogSignalType() {
        super(Integer.class, 0xFF0000);
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return other == this || other == BuiltinSignalTypes.BOOLEAN;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <S> S convert(Integer value, SignalType<S> type) {
        if (type == BuiltinSignalTypes.BOOLEAN) {
            return (S) (Boolean) (value > 0);
        }
        return super.convert(value, type);
    }
}
