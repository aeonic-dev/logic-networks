package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.logic.network.Network;

public interface NetworkController {
    Network getNetwork();

    void setNetwork(Network network);
}
