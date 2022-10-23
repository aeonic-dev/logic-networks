package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * The status of a linking card or other link.
 */
public enum LinkStatus {
    /**
     * Valid link
     */
    VALID,
    /**
     * Link may or may not be valid - chunk is unloaded
     */
    UNLOADED,
    /**
     * Link is invalid - block is destroyed
     */
    DESTROYED,
    /**
     * Link is invalid - block is not an anchor or is unset
     */
    INVALID;

    public static LinkStatus get(Level level, BlockPos pos) {
        if (level.hasChunkAt(pos)) {
            if (level.getBlockState(pos).isAir()) return DESTROYED;
            return level.getBlockEntity(pos) instanceof NetworkAnchor ? VALID : INVALID;
        } else {
            return UNLOADED;
        }
    }
}
