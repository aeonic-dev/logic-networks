package design.aeonic.logicnetworks;

import design.aeonic.logicnetworks.api.client.FacadeRenderer;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.LogicNetworks;
import design.aeonic.logicnetworks.impl.client.NetworkKeybinds;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import design.aeonic.logicnetworks.impl.content.NetworkBlocks;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import design.aeonic.logicnetworks.impl.content.NetworkMenus;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;

public class FabricLogicNetworks implements ModInitializer {

    public static void onInitializeClient() {
        LogicNetworks.clientInit(Minecraft.getInstance()::submit);
        NetworkKeybinds.register(($, mapping) -> KeyBindingHelper.registerKeyBinding(mapping));
        BlockEntityRendererRegistry.register(NetworkBlockEntities.NETWORK_ANCHOR, FacadeRenderer::new);
    }

    @Override
    public void onInitialize() {
        LogicNetworks.init();

        NetworkBlocks.register(registrar(Registry.BLOCK));
        NetworkItems.register(registrar(Registry.ITEM));
        NetworkBlockEntities.register(registrar(Registry.BLOCK_ENTITY_TYPE));
        NetworkMenus.register(registrar(Registry.MENU));
    }

    <T> Registrar<T> registrar(Registry<T> registry) {
        return Registrar.of(Constants.MOD_ID, (key, value) -> Registry.register(registry, key, value));
    }
}
