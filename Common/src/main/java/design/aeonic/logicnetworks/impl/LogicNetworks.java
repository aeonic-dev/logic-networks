package design.aeonic.logicnetworks.impl;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.builtin.redstone.RedstoneOperators;

public class LogicNetworks {

    public static void init() {
        BuiltinSignalTypes.register();
        RedstoneOperators.register();
    }

}