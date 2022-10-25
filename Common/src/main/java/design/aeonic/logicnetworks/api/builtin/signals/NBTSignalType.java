package design.aeonic.logicnetworks.api.builtin.signals;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class NBTSignalType extends SignalType<CompoundTag> {
    // This one isn't cache writable because it feels too all-encompassing--could be a problem with NBT depth as well
    // but really it's just because I don't want to encourage using multiple caches
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
    public <S> boolean canConnect(SignalType<S> other) {
        return super.canConnect(other) || other.is(BuiltinSignalTypes.STRING);
    }

    @Override
    public <S> S convert(CompoundTag value, SignalType<S> type) {
        if (type.is(BuiltinSignalTypes.STRING)) return type.cast(value.toString());
        return super.convert(value, type);
    }
}
