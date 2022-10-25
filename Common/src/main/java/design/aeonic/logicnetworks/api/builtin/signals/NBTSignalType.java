package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.CacheWritableSignalType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NBTSignalType extends CacheWritableSignalType<CompoundTag> {
    public NBTSignalType(int color) {
        super(CompoundTag.class, color);
    }

    @Override
    public Component getSocketTooltip(boolean isOutput) {
        return Translations.Signals.NBT;
    }

    @Override
    public void write(NetworkAnchor anchor, Direction side, CompoundTag value) {}

    @Override
    public CompoundTag read(NetworkAnchor anchor, Direction side) {
        BlockEntity blockEntity = anchor.getAnchorLevel().getBlockEntity(anchor.getAnchorPos().relative(side));
        if (blockEntity != null) {
            return blockEntity.saveWithoutMetadata();
        }
        return null;
    }

    @Override
    public void writeValue(CompoundTag tag, CompoundTag value) {
        tag.put("NBT", value);
    }

    @Override
    public CompoundTag readValue(CompoundTag tag) {
        if (!tag.contains("NBT", Tag.TAG_COMPOUND)) return null;
        return tag.getCompound("NBT");
    }

    @Override
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other.is(BuiltinSignalTypes.STRING);
    }

    @Override
    public <S> S convert(CompoundTag value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.STRING)) return type.cast(value.toString());
        return super.convert(value, type);
    }
}
