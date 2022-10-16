package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerMenu;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.world.inventory.MenuType;

public final class NetworkMenus {

    public static final MenuType<NetworkControllerMenu> NETWORK_CONTROLLER = Services.ACCESS.menuType(NetworkControllerMenu::new);

}
