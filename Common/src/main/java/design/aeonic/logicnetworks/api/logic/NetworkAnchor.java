package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

/**
 * Interface for block entities that can serve as I/O anchors for a network.
 */
public interface NetworkAnchor {

    Level getLevel();

    BlockPos getBlockPos();

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
}
