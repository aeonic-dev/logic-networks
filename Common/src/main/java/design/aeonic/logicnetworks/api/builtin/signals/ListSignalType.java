package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.CacheWritableSignalType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class ListSignalType extends CacheWritableSignalType<ListTag> {
    /**
     * Constructs a signal type.
     */
    public ListSignalType(int color) {
        super(ListTag.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.LIST;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, ListTag value) {}

    @Override
    public ListTag read(NetworkAnchor anchor, Direction side) {
        return null;
    }

    @Override
    public void writeValue(CompoundTag tag, ListTag value) {
        tag.put("List", value);
    }

    @Nullable
    @Override
    public ListTag readValue(CompoundTag tag) {
        return tag.get("List") instanceof ListTag list ? list : null;
    }
}
