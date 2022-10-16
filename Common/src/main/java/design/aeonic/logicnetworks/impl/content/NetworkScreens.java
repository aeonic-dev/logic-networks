package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerScreen;
import design.aeonic.logicnetworks.impl.services.Services;

public final class NetworkScreens {
    public static void register() {
        Services.ACCESS.registerScreen(NetworkMenus.NETWORK_CONTROLLER, NetworkControllerScreen::new);
    }
}
