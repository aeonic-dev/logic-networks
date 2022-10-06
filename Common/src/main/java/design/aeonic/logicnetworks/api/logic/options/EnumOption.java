package design.aeonic.logicnetworks.api.logic.options;

import design.aeonic.logicnetworks.api.graph.Option;
import net.minecraft.nbt.CompoundTag;

public class EnumOption<T extends Enum<T>> extends Option<T>  {
    public EnumOption(EnumOptionType<T> type) {
        super(type);
    }

    @SuppressWarnings("unchecked")
    public T[] getValues() {
        return ((EnumOptionType<T>) type).values;
    }

    @Override
    public void serialize(CompoundTag tag) {
        tag.putString("value", getValue().name());
    }

    @Override
    public void deserialize(CompoundTag tag) {
        setValue(Enum.valueOf(getValues()[0].getDeclaringClass(), tag.getString("value")));
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }
}
