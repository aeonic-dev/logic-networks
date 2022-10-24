package design.aeonic.logicnetworks.impl.services;

import design.aeonic.logicnetworks.api.services.PlatformInfo;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformInfo implements PlatformInfo {

    @Override
    public Platform getPlatform() {
        return Platform.NOT_FORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
