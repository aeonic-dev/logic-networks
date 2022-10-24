package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.LogicNetworks;
import design.aeonic.logicnetworks.impl.content.link.LinkingCardItem;
import design.aeonic.logicnetworks.impl.content.wrench.WrenchItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class NetworkItems {
    public static final BlockItem NETWORK_CONTROLLER = new BlockItem(NetworkBlocks.NETWORK_CONTROLLER, new BlockItem.Properties().tab(LogicNetworks.CREATIVE_TAB));
    public static final BlockItem NETWORK_ANCHOR = new BlockItem(NetworkBlocks.NETWORK_ANCHOR, new BlockItem.Properties().tab(LogicNetworks.CREATIVE_TAB));
    public static final BlockItem NETWORK_CACHE = new BlockItem(NetworkBlocks.NETWORK_CACHE, new BlockItem.Properties().tab(LogicNetworks.CREATIVE_TAB));

    public static final WrenchItem WRENCH = new WrenchItem(new Item.Properties().stacksTo(1).tab(LogicNetworks.CREATIVE_TAB));
    public static final LinkingCardItem LINKING_CARD = new LinkingCardItem(new Item.Properties().stacksTo(1));
    public static final Item LOGIC_CORE = new Item(new Item.Properties().stacksTo(64).tab(LogicNetworks.CREATIVE_TAB));
    public static final Item CONTROL_CIRCUIT = new Item(new Item.Properties().stacksTo(64).tab(LogicNetworks.CREATIVE_TAB));

    public static void register(Registrar<Item> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
        registrar.accept("network_anchor", NETWORK_ANCHOR);
        registrar.accept("network_cache", NETWORK_CACHE);

        registrar.accept("wrench", WRENCH);
        registrar.accept("linking_card", LINKING_CARD);
        registrar.accept("logic_core", LOGIC_CORE);
        registrar.accept("control_circuit", CONTROL_CIRCUIT);
    }
}
