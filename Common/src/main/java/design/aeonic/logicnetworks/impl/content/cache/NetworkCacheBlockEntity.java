package design.aeonic.logicnetworks.impl.content.cache;

import design.aeonic.logicnetworks.api.block.NetworkCache;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class NetworkCacheBlockEntity extends BlockEntity implements NetworkCache {
    private final Map<SignalType<?>, Object> cache = new HashMap<>();

    public NetworkCacheBlockEntity(BlockPos pos, BlockState state) {
        super(NetworkBlockEntities.NETWORK_CACHE, pos, state);
    }

    @Override
    public <T> void writeValue(SignalType<T> type, T value) {
        if (value == null) return;
        cache.put(type, value);
        setChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readValue(SignalType<T> type) {
        return (T) cache.get(type);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag cacheTag = tag.getCompound("Cache");
        for (SignalType<?> type : CommonRegistries.SIGNAL_TYPES.getValues()) {
            Object value = type.readValue(cacheTag);
            if (value != null) cache.put(type, value);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag cacheTag = new CompoundTag();
        for (Map.Entry<SignalType<?>, Object> entry : cache.entrySet()) {
            if (entry.getValue() != null) writeUnchecked(entry.getKey(), cacheTag, entry.getValue());
        }
        tag.put("Cache", cacheTag);
    }

    @SuppressWarnings("unchecked")
    private <T> void writeUnchecked(SignalType<T> type, CompoundTag tag, Object value) {
        type.writeValue(tag, (T) value);
    }
}
