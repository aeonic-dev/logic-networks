package design.aeonic.logicnetworks;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.LogicNetworks;
import design.aeonic.logicnetworks.impl.content.NetworkBlockEntities;
import design.aeonic.logicnetworks.impl.content.NetworkBlocks;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class ForgeLogicNetworks {
    
    public ForgeLogicNetworks() {
        LogicNetworks.init();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener((RegisterEvent event) -> {
            NetworkBlocks.register(registrar(event, ForgeRegistries.Keys.BLOCKS));
            NetworkBlockEntities.register(registrar(event, ForgeRegistries.Keys.BLOCK_ENTITY_TYPES));
            NetworkItems.register(registrar(event, ForgeRegistries.Keys.ITEMS));
        });
    }

    <T> Registrar<T> registrar(RegisterEvent event, ResourceKey<? extends Registry<T>> registry) {
        return Registrar.of(Constants.MOD_ID, (key, value) -> event.register(registry, key, () -> value));
    }
}