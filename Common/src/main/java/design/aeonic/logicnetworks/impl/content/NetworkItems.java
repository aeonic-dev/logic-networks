package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.content.link.LinkingCardItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class NetworkItems {
    public static final BlockItem NETWORK_CONTROLLER = new BlockItem(NetworkBlocks.NETWORK_CONTROLLER, new BlockItem.Properties());
    public static final BlockItem NETWORK_ANCHOR = new BlockItem(NetworkBlocks.NETWORK_ANCHOR, new BlockItem.Properties());

    public static final LinkingCardItem LINKING_CARD = new LinkingCardItem(new Item.Properties().stacksTo(1));
    public static final Item LOGIC_CORE = new Item(new Item.Properties().stacksTo(64));

    public static void register(Registrar<Item> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
        registrar.accept("network_anchor", NETWORK_ANCHOR);
        registrar.accept("linking_card", LINKING_CARD);
    }
}
