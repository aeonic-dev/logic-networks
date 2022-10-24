package design.aeonic.logicnetworks.api.data;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class NetworkTags {
    public static final class Blocks {
        public static final TagKey<Block> FACADE_BLACKLIST = create("facade_blacklist");

        private static TagKey<Block> create(String key) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Constants.MOD_ID, key));
        }
    }

    public static final class Items {
        public static final TagKey<Item> WRENCH = platformTag("wrenches");

        private static TagKey<Item> platformTag(String tag) {
            return platformTag(tag, tag);
        }

        private static TagKey<Item> platformTag(String forgeTag, String fabricTag) {
            return NetworkTags.platformTag(Registry.ITEM_REGISTRY, forgeTag, fabricTag);
        }
    }

    public static <T> TagKey<T> platformTag(ResourceKey<? extends Registry<T>>registry, String forgeTag, String fabricTag) {
        return TagKey.create(registry, new ResourceLocation(Services.PLATFORM.getPlatform().isForge() ? "forge" : "c", Services.PLATFORM.getPlatform().isForge() ? forgeTag : fabricTag));
    }
}
