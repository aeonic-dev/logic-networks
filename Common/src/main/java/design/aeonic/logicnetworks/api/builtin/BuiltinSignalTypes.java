package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinSignalTypes {
    /**
     * Analog redstone signal type, values 0-15.
     */
    public static final SignalType<Integer> ANALOG = new SignalType<>(Integer.class, 0xDC143C) {
        @Override
        public <S> boolean canConnect(SignalType<S> other) {
            return super.canConnect(other) || other == BOOLEAN;
        }

        @Override
        public <S> S convert(Integer value, SignalType<S> type) {
            if (type == BOOLEAN) return type.cast(value > 0);
            return super.convert(value, type);
        }
    };
    /**
     * Digital redstone signal type, boolean values.
     */
    public static final SignalType<Boolean> BOOLEAN = new SignalType<>(Boolean.class, 0x3CB371) {
        @Override
        public <S> boolean canConnect(SignalType<S> other) {
            return super.canConnect(other) || other == ANALOG;
        }

        @Override
        public <S> S convert(Boolean value, SignalType<S> type) {
            if (type == ANALOG) return type.cast(value ? 15 : 0);
            return super.convert(value, type);
        }
    };

    public static void register() {
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog"), ANALOG);
        CommonRegistries.SIGNAL_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean"), BOOLEAN);
    }
}
