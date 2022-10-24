package design.aeonic.logicnetworks.api.logic.network;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public abstract class CacheWritableSignalType<T> extends SignalType<T> {

    /**
     * Constructs a signal type.
     *
     * @param type  The underlying type of the signal's mutable value.
     * @param color
     */
    public CacheWritableSignalType(Class<T> type, int color) {
        super(type, color);
    }

    /**
     * Write the given value to an NBT tag, for use within network caches.
     */
    public abstract void writeValue(CompoundTag tag, T value);

    /**
     * Read the given value from an NBT tag, for use within network caches.
     */
    @Nullable
    public abstract T readValue(CompoundTag tag);

}
