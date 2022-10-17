package design.aeonic.logicnetworks.api.core;

import net.minecraft.network.chat.Component;

public final class Translations {
    public static final class InputLabels {
        public static final Component TICKS = Component.translatable("gui.logicnetworks.ticks");
        public static final Component REDSTONE_CONTROL = Component.translatable("gui.logicnetworks.redstone_control");
    }

    public static final class RedstoneControl {
        public static final Component ALWAYS = Component.translatable("gui.logicnetworks.redstone_control.always");
        public static final Component HIGH = Component.translatable("gui.logicnetworks.redstone_control.high");
        public static final Component LOW = Component.translatable("gui.logicnetworks.redstone_control.low");
        public static final Component NEVER = Component.translatable("gui.logicnetworks.redstone_control.never");
    }

    public static final class NetworkController {
        public static final Component TITLE = Component.translatable("gui.logicnetworks.network_controller.title");
        public static final Component TICKS_PER_OP = Component.translatable("gui.logicnetworks.network_controller.ticks_per_operation");
        public static final Component EDIT_NETWORK = Component.translatable("gui.logicnetworks.network_controller.edit_network");
        public static final Component EDIT_NETWORK_TOOLTIP = Component.translatable("gui.logicnetworks.network_controller.edit_network_tooltip");
    }
}
