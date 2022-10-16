package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class NetworkItems {
    public static final BlockItem NETWORK_CONTROLLER = new BlockItem(NetworkBlocks.NETWORK_CONTROLLER, new BlockItem.Properties());

    public static void register(Registrar<Item> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
    }
}
