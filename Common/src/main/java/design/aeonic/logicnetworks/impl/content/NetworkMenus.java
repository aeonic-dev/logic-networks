package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.content.anchor.NetworkAnchorMenu;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerMenu;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.world.inventory.MenuType;

public final class NetworkMenus {
    public static final MenuType<NetworkControllerMenu> NETWORK_CONTROLLER = Services.ACCESS.menuType(NetworkControllerMenu::new);
    public static final MenuType<NetworkAnchorMenu> NETWORK_ANCHOR = Services.ACCESS.menuType(NetworkAnchorMenu::new);

    public static void register(Registrar<MenuType<?>> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
        registrar.accept("network_anchor", NETWORK_ANCHOR);
    }
}
