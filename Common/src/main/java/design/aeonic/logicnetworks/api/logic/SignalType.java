package design.aeonic.logicnetworks.api.logic;

import javax.annotation.Nullable;

/**
 * A type of signal that can be processed by a logic network.
 * ie: item containers, redstone, etc.<br><br>
 * One instance will exist for each type of signal and is registered at runtime.
 * @param <T> The underlying type of the signal's mutable value.
 */
public abstract class SignalType<T> {
    public final Class<T> type;
    public final int color;

    /**
     * Constructs a signal type.
     * @param type The underlying type of the signal's mutable value.
     */
    public SignalType(Class<T> type, int color) {
        this.type = type;
        this.color = color;
    }

    public <S> boolean canConnect(SignalType<S> other) {
        return other == this;
    }

    /**
     * Implicitly convert this signal to another type. If null is returned, the conversion is not possible.<br><br>
     * Allows connections between sockets of the given type and this one. Must also override {@link #canConnect(SignalType)}.
     */
    @Nullable
    public <S> S convert(T value, SignalType<S> type) {
        return (S) value;
    }
}
