package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public final class NetworkBlocks {
    public static final NetworkControllerBlock NETWORK_CONTROLLER = new NetworkControllerBlock(Block.Properties
            .of(Material.METAL).strength(5.0F, 6.0F).sound(SoundType.COPPER));

    public static void register(Registrar<Block> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
    }
}
