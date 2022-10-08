package design.aeonic.logicnetworks.impl;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.builtin.redstone.RedstoneOperators;
import design.aeonic.logicnetworks.impl.client.BuiltinNodeRenderers;

public class LogicNetworks {
    public static void init() {
        BuiltinSignalTypes.register();
        RedstoneOperators.register();
    }

    public static void clientInit() {
        BuiltinNodeRenderers.register();
    }
}