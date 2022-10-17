package design.aeonic.logicnetworks.api.control;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public enum RedstoneControl {
    /**
     * Active regardless of redstone signal.
     */
    ALWAYS,
    /**
     *Active when the redstone signal is high.
     */
    HIGH,
    /**
     * Active when the redstone signal is off.
     */
    LOW,
    /**
     * Never active.
     */
    NEVER;

    public boolean shouldRun(Level level, BlockPos pos) {
        return switch (this) {
            case ALWAYS -> true;
            case HIGH -> level.hasNeighborSignal(pos);
            case LOW -> !level.hasNeighborSignal(pos);
            default -> false;
        };
    }
}