package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.content.anchor.NetworkAnchorBlock;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public final class NetworkBlocks {
    public static final NetworkControllerBlock NETWORK_CONTROLLER = new NetworkControllerBlock(Block.Properties
            .of(Material.METAL).strength(4F, 6.0F).sound(SoundType.COPPER).lightLevel(state -> 13));

    public static final NetworkAnchorBlock NETWORK_ANCHOR = new NetworkAnchorBlock(Block.Properties
            .of(Material.METAL).isRedstoneConductor(($, $$, $$$) -> true).strength(2F, 6.0F).sound(SoundType.COPPER).lightLevel(state -> 7).noOcclusion());

    public static void register(Registrar<Block> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
        registrar.accept("network_anchor", NETWORK_ANCHOR);
    }
}
