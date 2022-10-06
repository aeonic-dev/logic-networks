package design.aeonic.logicnetworks.api.logic;

import net.minecraft.nbt.CompoundTag;

/**
 * Describes a type of setting used as additional data for an operator.
 * Instances (actual options within a node) are created using {@link Option}.
 */
public abstract class OptionType<T> {
    protected final String label;
    protected final T defaultValue;

    public OptionType(String label, T defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;
    }

    public Option<T> create() {
        return new Option<>(this);
    }

    public Option<T> create(T initialValue) {
        return new Option<>(this, initialValue);
    }

    public abstract void serialize(CompoundTag tag, T value);

    public abstract T deserialize(CompoundTag tag);
}
