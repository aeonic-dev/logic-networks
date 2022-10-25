package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.signals.*;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSignalTypes {
    /**
     * Integer signal type, full int range.
     */
    public static final SignalType<Integer> INTEGER = new IntegerSignalType(0x1E90FF);

    /**
     * Long signal type, full long range.
     */
    public static final SignalType<Long> LONG = new LongSignalType(0x9966FF);

    /**
     * Analog redstone signal type, values 0-15.
     */
    public static final SignalType<Integer> ANALOG = new AnalogSignalType(0xDC143C);

    /**
     * Digital redstone signal type, boolean values.
     */
    public static final SignalType<Boolean> BOOLEAN = new BooleanSignalType(0x3CB371);

    /**
     * String signal type, arbitrary strings.
     */
    public static final SignalType<String> STRING = new StringSignalType(0xFCCE4C);

    /**
     * NBT signal type, compound tags.
     */
    public static final SignalType<CompoundTag> NBT = new NBTSignalType(0xE7EBCD);

    /**
     * NBT list signal type.
     */
    public static final SignalType<ListTag> LIST = new ListSignalType(0x6A4C62);

    public static void register() {
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer"), INTEGER);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "long"), LONG);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog"), ANALOG);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean"), BOOLEAN);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string"), STRING);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt"), NBT);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list"), LIST);
    }

}
