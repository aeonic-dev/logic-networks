package design.aeonic.logicnetworks.api.core;

import net.minecraft.network.chat.Component;

public final class Translations {
    public static final class Generic {
        public static final Component TICKS = Component.translatable("gui.logicnetworks.generic.ticks");
        public static final Component REDSTONE_CONTROL = Component.translatable("gui.logicnetworks.generic.redstone_control");
        public static final Component CLAMPS = Component.translatable("gui.logicnetworks.generic.clamps");
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

    public static final class NetworkGraph {
        public static final Component TITLE = Component.translatable("gui.logicnetworks.network_graph.title");
    }

    public static final class Nodes {
        public static final Component ANALOG_INVERT = Component.translatable("gui.logicnetworks.nodes.analog_invert");
        public static final Component ANALOG_ADD = Component.translatable("gui.logicnetworks.nodes.analog_add");
        public static final Component ANALOG_SUBTRACT = Component.translatable("gui.logicnetworks.nodes.analog_subtract");
        public static final Component ANALOG_MULTIPLY = Component.translatable("gui.logicnetworks.nodes.analog_multiply");
        public static final Component ANALOG_DIVIDE = Component.translatable("gui.logicnetworks.nodes.analog_divide");
        public static final Component ANALOG_MODULO = Component.translatable("gui.logicnetworks.nodes.analog_modulo");


        public static final Component BOOLEAN_INVERT = Component.translatable("gui.logicnetworks.nodes.boolean_invert");
        public static final Component BOOLEAN_AND = Component.translatable("gui.logicnetworks.nodes.boolean_and");
        public static final Component BOOLEAN_OR = Component.translatable("gui.logicnetworks.nodes.boolean_or");
        public static final Component BOOLEAN_XOR = Component.translatable("gui.logicnetworks.nodes.boolean_xor");
        public static final Component BOOLEAN_NAND = Component.translatable("gui.logicnetworks.nodes.boolean_nand");
        public static final Component BOOLEAN_NOR = Component.translatable("gui.logicnetworks.nodes.boolean_nor");
        public static final Component BOOLEAN_XNOR = Component.translatable("gui.logicnetworks.nodes.boolean_xnor");
    }
}
