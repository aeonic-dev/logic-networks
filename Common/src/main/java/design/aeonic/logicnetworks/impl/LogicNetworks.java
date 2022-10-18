package design.aeonic.logicnetworks.impl;

import design.aeonic.logicnetworks.api.builtin.BuiltinNodeTypes;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.impl.content.NetworkScreens;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerUpdatePacket;
import design.aeonic.logicnetworks.impl.networking.packets.ServerboundNetworkChangePacket;
import design.aeonic.logicnetworks.impl.services.Services;

import java.util.function.Consumer;

public class LogicNetworks {
    public static void init() {
        BuiltinSignalTypes.register();
        BuiltinNodeTypes.register();

        Services.NETWORKING.registerServerboundPacketHandler(NetworkControllerUpdatePacket.HANDLER);
        Services.NETWORKING.registerServerboundPacketHandler(ServerboundNetworkChangePacket.HANDLER);
    }

    public static void clientInit(Consumer<Runnable> clientTaskQueue) {
        clientTaskQueue.accept(NetworkScreens::register);
    }
}