package design.aeonic.logicnetworks.impl.client;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import design.aeonic.logicnetworks.impl.content.manual.ManualItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class NetworkItemProperties {
    public static void register(ItemPropertyRegistry registry) {
        registry.accept(NetworkItems.MANUAL, new ResourceLocation(Constants.MOD_ID, "is_open"), ((itemStack, clientLevel, livingEntity, i) -> ManualItem.getIsOpenPropety()));
    }

    @FunctionalInterface
    public interface ItemPropertyRegistry {
        void accept(Item item, ResourceLocation id, ClampedItemPropertyFunction supplier);
    }
}
