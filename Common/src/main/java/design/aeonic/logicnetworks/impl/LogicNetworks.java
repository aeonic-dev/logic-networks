package design.aeonic.logicnetworks.impl;

import design.aeonic.logicnetworks.api.builtin.BuiltinNodeTypes;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.impl.content.NetworkBlocks;
import design.aeonic.logicnetworks.impl.content.NetworkScreens;
import design.aeonic.logicnetworks.impl.content.anchor.NetworkAnchorUpdatePacket;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerUpdatePacket;
import design.aeonic.logicnetworks.impl.networking.packets.ServerboundNetworkChangePacket;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class LogicNetworks {
    public static final CreativeModeTab CREATIVE_TAB = Services.ACCESS.registerCreativeTab(
            new ResourceLocation(Constants.MOD_ID, "main"), () -> new ItemStack(NetworkBlocks.NETWORK_CONTROLLER));

    public static void init() {
        BuiltinSignalTypes.register();
        BuiltinNodeTypes.register();

        Services.NETWORKING.registerServerboundPacketHandler(ServerboundNetworkChangePacket.HANDLER);
        Services.NETWORKING.registerServerboundPacketHandler(NetworkControllerUpdatePacket.HANDLER);
        Services.NETWORKING.registerServerboundPacketHandler(NetworkAnchorUpdatePacket.HANDLER);
    }

    public static void clientInit(Consumer<Runnable> clientTaskQueue) {
        clientTaskQueue.accept(NetworkScreens::register);
        clientTaskQueue.accept(() -> {
            Services.ACCESS.setRenderLayer(NetworkBlocks.NETWORK_CONTROLLER, RenderType.cutout());
        });
    }
}