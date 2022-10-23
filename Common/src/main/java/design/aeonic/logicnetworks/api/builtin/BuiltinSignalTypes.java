package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.signals.BooleanSignalType;
import design.aeonic.logicnetworks.api.builtin.signals.AnalogSignalType;
import design.aeonic.logicnetworks.api.builtin.signals.IntegerSignalType;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSignalTypes {
    /**
     * Integer signal type, full int range.
     */
    public static final SignalType<Integer> INTEGER = new IntegerSignalType(0x1E90FF);

    /**
     * Analog redstone signal type, values 0-15.
     */
    public static final SignalType<Integer> ANALOG = new AnalogSignalType(0xDC143C);

    /**
     * Digital redstone signal type, boolean values.
     */
    public static final SignalType<Boolean> BOOLEAN = new BooleanSignalType(0x3CB371);

    public static void register() {
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer"), INTEGER);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog"), ANALOG);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean"), BOOLEAN);
    }

}
