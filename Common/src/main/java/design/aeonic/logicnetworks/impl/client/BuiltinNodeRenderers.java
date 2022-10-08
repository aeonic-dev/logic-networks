package design.aeonic.logicnetworks.impl.client;

import design.aeonic.logicnetworks.api.client.nodes.BaseNodeRenderer;
import design.aeonic.logicnetworks.api.registries.NodeRendererRegistry;
import design.aeonic.logicnetworks.api.builtin.redstone.RedstoneOperators;
import net.minecraft.network.chat.Component;

public final class BuiltinNodeRenderers {
    public static void register() {
        NodeRendererRegistry.INSTANCE.register(RedstoneOperators.ANALOG_ADD, new BaseNodeRenderer<>(Component.literal("Add"), 40, 40));
        NodeRendererRegistry.INSTANCE.register(RedstoneOperators.ANALOG_INVERT, new BaseNodeRenderer<>(Component.literal("Invert"), 40, 30));
    }
}
