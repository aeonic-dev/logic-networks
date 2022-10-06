package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.builtin.redstone.AnalogSignalType;
import design.aeonic.logicnetworks.api.builtin.redstone.BooleanSignalType;
import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.api.registry.SignalTypeRegistry;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSignalTypes {
    public static final SignalType<Integer> ANALOG = new AnalogSignalType();
    public static final SignalType<Boolean> BOOLEAN = new BooleanSignalType();

    public static void register() {
        SignalTypeRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "analog"), ANALOG);
        SignalTypeRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "boolean"), BOOLEAN);
    }
}
