package design.aeonic.logicnetworks.api.logic.network;

import design.aeonic.logicnetworks.api.logic.NetworkController;

@FunctionalInterface
public interface CompiledNetwork {
    /**
     * Empty compiled network for use on the client.
     */
    CompiledNetwork EMPTY = ($) -> {};

    /**
     * Attempts to execute the network's operations for each source input to each sink output.
     */
    void tick(NetworkController controller);

    @FunctionalInterface
    interface NetworkCompiler {
        CompiledNetwork compile(Network network);
    }
}
