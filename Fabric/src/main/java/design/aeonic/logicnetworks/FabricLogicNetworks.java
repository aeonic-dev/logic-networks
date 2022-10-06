package design.aeonic.logicnetworks;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.LogicNetworks;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import design.aeonic.logicnetworks.impl.content.NetworkBlocks;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;

public class FabricLogicNetworks implements ModInitializer {
    @Override
    public void onInitialize() {
        LogicNetworks.init();

        NetworkBlocks.register(registrar(Registry.BLOCK));
        NetworkBlockEntities.register(registrar(Registry.BLOCK_ENTITY_TYPE));
        NetworkItems.register(registrar(Registry.ITEM));
    }

    <T> Registrar<T> registrar(Registry<T> registry) {
        return Registrar.of(Constants.MOD_ID, (key, value) -> Registry.register(registry, key, value));
    }
}
