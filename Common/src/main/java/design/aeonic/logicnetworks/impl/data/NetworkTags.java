package design.aeonic.logicnetworks.impl.data;

import design.aeonic.logicnetworks.api.core.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class NetworkTags {
    public static final class Blocks {
        public static final TagKey<Block> FACADE_BLACKLIST = create("facade_blacklist");

        private static TagKey<Block> create(String key) {
            return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Constants.MOD_ID, key));
        }
    }
}
