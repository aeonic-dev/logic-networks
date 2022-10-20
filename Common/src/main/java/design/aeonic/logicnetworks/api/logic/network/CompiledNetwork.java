package design.aeonic.logicnetworks.api.logic.network;

public interface CompiledNetwork {
    /**
     * Empty compiled network for use on the client.
     */
    CompiledNetwork EMPTY = new CompiledNetwork() {
        @Override
        public void tick() {
        }
    };

    /**
     * Attempts to execute the network's operations for each source input to each sink output.
     */
    void tick();

    @FunctionalInterface
    interface NetworkCompiler {
        CompiledNetwork compile(Network network);
    }
}
