package design.aeonic.logicnetworks.impl.content.cache;

import design.aeonic.logicnetworks.api.block.NetworkCache;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import net.minecraft.core.BlockPos;
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
        cache.put(type, value);
        setChanged();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T readValue(SignalType<T> type) {
        return (T) cache.get(type);
    }
}
