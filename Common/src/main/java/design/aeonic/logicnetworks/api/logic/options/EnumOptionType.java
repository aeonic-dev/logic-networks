package design.aeonic.logicnetworks.api.logic.options;

import design.aeonic.logicnetworks.api.logic.OptionType;
import net.minecraft.nbt.CompoundTag;

public class EnumOptionType<T extends Enum<T>> extends OptionType<T> {
    private final T[] values;

    public EnumOptionType(String label, T defaultValue) {
        super(label, defaultValue);
        this.values = defaultValue.getDeclaringClass().getEnumConstants();
    }

    @Override
    public void serialize(CompoundTag tag, T value) {
        tag.putInt(label, value.ordinal());
    }

    @Override
    public T deserialize(CompoundTag tag) {
        int ordinal = tag.getInt(label);
        return ordinal >= 0 && ordinal < values.length ? values[ordinal] : defaultValue;
    }
}
