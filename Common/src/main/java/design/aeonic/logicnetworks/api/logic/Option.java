package design.aeonic.logicnetworks.api.logic;

import net.minecraft.nbt.CompoundTag;

/**
 * An instance of an {@link OptionType} used to configure an {@link Operator}.
 */
public class Option<T> {
    public final OptionType<T> type;
    private T value;

    public Option(OptionType<T> type) {
        this(type, type.defaultValue);
    }

    public Option(OptionType<T> type, T initialValue) {
        this.type = type;
        this.value = initialValue;
    }

    @SuppressWarnings("unchecked")
    public <O> Option<O> cast(Class<O> type) {
        return (Option<O>) this;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void serialize(CompoundTag tag) {
        type.serialize(tag, value);
    }

    public static <T> Option<T> deserialize(OptionType<T> type, CompoundTag tag) {
        return new Option<>(type, type.deserialize(tag));
    }
}
