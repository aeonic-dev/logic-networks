package design.aeonic.logicnetworks.impl.platform;

import design.aeonic.logicnetworks.api.platform.IPlatformInfo;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformInfo implements IPlatformInfo {

    @Override
    public String getPlatformName() {
        return "Fabric";
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
