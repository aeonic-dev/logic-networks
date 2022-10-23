package design.aeonic.logicnetworks.api.block;

import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

/**
 * Interface for block entities that can serve as I/O anchors for a network.
 */
public interface NetworkAnchor {

    Level getAnchorLevel();

    BlockPos getAnchorPos();

    /**
     * Janky redstone signal cache for per-side values.
     */
    void setRedstone(Direction side, int signal);

    /**
     * Only use if you need access to a *different* signal. By default this just defers to {@link SignalType#read}.
     */
    <T> T read(Direction side, SignalType<T> type);

    /**
     * Only use if you need access to a *different* signal. By default this just defers to {@link SignalType#write}.
     */
    <T> void write(Direction side, SignalType<T> type, T value);

    /**
     * An extension applied per-platform for extra functionality. IE on Forge, this caches capabilities.
     * Used to avoid registering different block entity classes on either platform.
     */
    interface Extension {

        void deserialize(CompoundTag tag);

        void serialize(CompoundTag tag);
    }
}
