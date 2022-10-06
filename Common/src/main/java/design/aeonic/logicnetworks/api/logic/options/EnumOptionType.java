package design.aeonic.logicnetworks.api.logic.options;

import design.aeonic.logicnetworks.api.logic.OptionType;

public class EnumOptionType<T extends Enum<T>> extends OptionType<T, EnumOption<T>> {
    protected final T[] values;

    public EnumOptionType(String label, T defaultValue) {
        super(label, defaultValue);
        this.values = defaultValue.getDeclaringClass().getEnumConstants();
    }

    @Override
    public EnumOption<T> create() {
        return new EnumOption<>(this);
    }
}
