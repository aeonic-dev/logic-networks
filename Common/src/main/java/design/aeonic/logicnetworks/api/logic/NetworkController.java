package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.logic.network.Network;
import net.minecraft.world.level.Level;

public interface NetworkController {
    Network getNetwork();

    void setNetwork(Network network);

    Level getControllerLevel();
}
