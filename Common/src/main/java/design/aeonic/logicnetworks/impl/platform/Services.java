package design.aeonic.logicnetworks.impl.platform;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.platform.IPlatformAccess;
import design.aeonic.logicnetworks.api.platform.IPlatformInfo;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformInfo PLATFORM = load(IPlatformInfo.class);
    public static final IPlatformAccess ACCESS = load(IPlatformAccess.class);

    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
