package design.aeonic.logicnetworks.api.core;

import design.aeonic.logicnetworks.api.logic.NodeType;
import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.api.util.SimpleRegistry;

/**
 * A class containing common registries for the Logic Networks API.
 */
public final class CommonRegistries {
    public static final SimpleRegistry<SignalType<?>> SIGNAL_TYPES = new SimpleRegistry.ConcurrentRegistry<>();
    public static final SimpleRegistry<NodeType<?>> NODE_TYPES = new SimpleRegistry.ConcurrentRegistry<>();

    private CommonRegistries() {}
}
