package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.SignalType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSignalTypes {
    public static final SignalType<Integer> ANALOG = new SignalType<>(Integer.class, 0xFF0000);
    public static final SignalType<Boolean> BOOLEAN = new SignalType<>(Boolean.class, 0x00FF00);

    public static void register() {
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog"), ANALOG);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean"), BOOLEAN);
    }
}
