package design.aeonic.logicnetworks.api.graph;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.OptionType;
import net.minecraft.nbt.CompoundTag;

/**
 * An instance of an {@link OptionType} used to configure an {@link Operator}.
 */
public abstract class Option<T> {
    public final OptionType<T, ?> type;
    private T value;

    public Option(OptionType<T, ?> type) {
        this(type, type.defaultValue);
    }

    public Option(OptionType<T, ?> type, T initialValue) {
        this.type = type;
        this.value = initialValue;
    }

    public abstract void serialize(CompoundTag tag);

    public abstract void deserialize(CompoundTag tag);

    public abstract int width();

    public abstract int height();

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
}
