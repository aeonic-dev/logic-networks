package design.aeonic.logicnetworks.api.logic;

public final class Signal<T> {
    public final SignalType<T> type;
    private T value;

    public Signal(SignalType<T> type, T initialValue) {
        this.type = type;
        this.value = initialValue;
    }

    public <S> Signal<S> convert(SignalType<S> type) {
        return new Signal<>(type, this.type.convert(value, type));
    }

    @SuppressWarnings("unchecked")
    public <S> Signal<S> cast(Class<S> type) {
        return (Signal<S>) this;
    }

    /**
     * Casts to the type of the given value, then sets the signal's value.
     */
    @SuppressWarnings("unchecked")
    public <S> void setAs(S value) {
        cast((Class<S>) value.getClass()).set(value);
    }

    /**
     * Casts to the given signal type and returns the value as that type.
     */
    public <S> S getAs(SignalType<S> signalType) {
        return cast(signalType.type).get();
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
