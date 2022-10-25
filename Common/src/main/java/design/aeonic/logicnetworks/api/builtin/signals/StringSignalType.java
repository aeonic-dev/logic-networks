package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.CacheWritableSignalType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public class StringSignalType extends CacheWritableSignalType<String> {
    public StringSignalType(int color) {
        super(String.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.STRING;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, String value) {}

    @Override
    public String read(NetworkAnchor anchor, Direction side) {
        return null;
    }

    @Override
    public void writeValue(CompoundTag tag, String value) {
        tag.putString("String", value);
    }

    @Override
    public String readValue(CompoundTag tag) {
        if (!tag.contains("String", Tag.TAG_STRING)) return null;
        return tag.getString("String");
    }
}
