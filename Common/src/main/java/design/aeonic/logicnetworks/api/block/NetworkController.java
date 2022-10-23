package design.aeonic.logicnetworks.api.block;

import design.aeonic.logicnetworks.api.logic.network.Network;
import net.minecraft.world.level.Level;

/**
 * Interface for the actual network controller.
 */
public interface NetworkController {
    Network getNetwork();

    void setNetwork(Network network);

    Level getControllerLevel();
}
