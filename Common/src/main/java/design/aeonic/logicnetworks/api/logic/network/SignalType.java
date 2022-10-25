package design.aeonic.logicnetworks.api.logic.network;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.client.screen.LineSet;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

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

    /**
     * Gets the line set for this signal type, only called on the client.
     * The defined line set is used instead of the default for connections with this signal type on the left.
     */
    public LineSet getLineSet() {
        return LineSet.DEFAULT;
    }

    /**
     * Gets the part of the socket tooltip describing this signal. By default it's added to the tooltip returned
     * by
     */
    public abstract Component getSocketTooltip(boolean isOutput);

    /**
     * Writes to the world. The given position is actually the position of the network anchor that's currently writing;
     * the side parameter is a given side relative to the network anchor block.
     */
    public abstract void write(NetworkAnchor anchor, Direction side, T value);

    /**
     * Reads from the world. See {@link #write(NetworkAnchor, Direction, Object)}
     */
    public abstract T read(NetworkAnchor anchor, Direction side);

    public SignalType<?>[] arrayOf() {
        return new SignalType<?>[]{this};
    }

    public SignalType<?>[] arrayOf(int size) {
        SignalType<?>[] ret = new SignalType<?>[size];
        for (int i = 0; i < size; i++) {
            ret[i] = this;
        }
        return ret;
    }

    public static SignalType<?>[] arrayOf(SignalType<?>... types) {
        return types;
    }

    public final boolean is(Class<?> type) {
        return this.type.isAssignableFrom(type);
    }

    public final boolean is(SignalType<?> type) {
        return this == type;
    }

    public T cast(Object value) {
        return type.cast(value);
    }

    public <S> boolean canConnect(SignalType<S> other) {
        return is(other) || is(other.type);
    }

    @SuppressWarnings("unchecked")
    public final <S> S convertUnchecked(Object value, SignalType<S> type) {
        return convert((T) value, type);
    }

    public <S> S convert(T value, SignalType<S> type) {
        return type.cast(value);
    }
}
