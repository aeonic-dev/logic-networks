package design.aeonic.logicnetworks.api.logic;

/**
 * A type of signal that can be processed by a logic network.
 * ie: item containers, redstone, etc.<br><br>
 * One instance will exist for each type of signal and is registered at runtime.
 * @param <T> The underlying type of the signal's mutable value.
 */
public class SignalType<T> {
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

    public SignalType<?>[] arrayOf() {
        return new SignalType<?>[]{this};
    }

    public static SignalType<?>[] arrayOf(SignalType<?>... types) {
        return types;
    }

    public boolean is(Class<?> type) {
        return this.type.isAssignableFrom(type);
    }

    public boolean is(SignalType<?> type) {
        return this == type;
    }

    public T cast(Object value) {
        return type.cast(value);
    }

    public <S> boolean canConnect(SignalType<S> other) {
        return is(other) || is(other.type);
    }

    public <S> S convert(T value, SignalType<S> type) {
        return type.cast(value);
    }
}
