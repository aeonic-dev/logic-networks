package design.aeonic.logicnetworks.api.core;

import design.aeonic.logicnetworks.api.logic.LinkStatus;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Function;

public final class Translations {
    public static final class Generic {
        public static final Component CONSTANT = Component.translatable("gui.logicnetworks.generic.constant");
        public static final Component TICKS = Component.translatable("gui.logicnetworks.generic.ticks");
        public static final Component REDSTONE_CONTROL = Component.translatable("gui.logicnetworks.generic.redstone_control");
        public static final Component CLAMPS = Component.translatable("gui.logicnetworks.generic.clamps");
    }

    public static final class Side {
        public static final Component UP = Component.translatable("gui.logicnetworks.side.up");
        public static final Component DOWN = Component.translatable("gui.logicnetworks.side.down");
        public static final Component NORTH = Component.translatable("gui.logicnetworks.side.north");
        public static final Component SOUTH = Component.translatable("gui.logicnetworks.side.south");
        public static final Component EAST = Component.translatable("gui.logicnetworks.side.east");
        public static final Component WEST = Component.translatable("gui.logicnetworks.side.west");

        public static Component translate(Direction side) {
            return switch (side) {
                case UP -> UP;
                case DOWN -> DOWN;
                case NORTH -> NORTH;
                case SOUTH -> SOUTH;
                case EAST -> EAST;
                case WEST -> WEST;
            };
        }
    }

    public static final class RedstoneControl {
        public static final Component ALWAYS = Component.translatable("gui.logicnetworks.redstone_control.always");
        public static final Component HIGH = Component.translatable("gui.logicnetworks.redstone_control.high");
        public static final Component LOW = Component.translatable("gui.logicnetworks.redstone_control.low");
        public static final Component NEVER = Component.translatable("gui.logicnetworks.redstone_control.never");
    }

    public static final class Link {
        public static final DynamicComponent LINKED_TO = dynamic("gui.logicnetworks.link.linked_to", c -> c.withStyle(ChatFormatting.BLUE));
        public static final DynamicComponent LINKED_SIDE = dynamic("gui.logicnetworks.link.linked_side", c -> c.withStyle(ChatFormatting.BLUE));
        public static final Component VALID = Component.translatable("gui.logicnetworks.link.valid").withStyle(ChatFormatting.GREEN);
        public static final Component UNLOADED = Component.translatable("gui.logicnetworks.link.unloaded").withStyle(ChatFormatting.DARK_PURPLE);
        public static final Component DESTROYED = Component.translatable("gui.logicnetworks.link.destroyed").withStyle(ChatFormatting.DARK_RED);
        public static final Component INVALID = Component.translatable("gui.logicnetworks.link.invalid").withStyle(ChatFormatting.DARK_RED);

        public static Component status(LinkStatus status) {
            return switch (status) {
                case VALID -> VALID;
                case UNLOADED -> UNLOADED;
                case DESTROYED -> DESTROYED;
                case INVALID -> INVALID;
            };
        }
    }

    public static final class NetworkController {
        public static final Component TITLE = Component.translatable("gui.logicnetworks.network_controller.title");
        public static final Component TICKS_PER_OP = Component.translatable("gui.logicnetworks.network_controller.ticks_per_operation");
        public static final Component EDIT_NETWORK = Component.translatable("gui.logicnetworks.network_controller.edit_network");
        public static final Component EDIT_NETWORK_TOOLTIP = Component.translatable("gui.logicnetworks.network_controller.edit_network_tooltip");
    }

    public static final class NetworkAnchor {
        public static final Component TITLE = Component.translatable("gui.logicnetworks.network_anchor.title");
        public static final Component NAME_TOOLTIP = Component.translatable("gui.logicnetworks.network_anchor.name_tooltip");
    }

    public static final class NetworkGraph {
        public static final Component TITLE = Component.translatable("gui.logicnetworks.network_graph.title");
    }

    public static final class Signals {
        public static final Component INTEGER = Component.translatable("gui.logicnetworks.signals.integer");
        public static final Component FLOAT = Component.translatable("gui.logicnetworks.signals.float");
        public static final Component ANALOG = Component.translatable("gui.logicnetworks.signals.analog");
        public static final Component BOOLEAN = Component.translatable("gui.logicnetworks.signals.boolean");
    }

    public static final class Nodes {
        public static final Component INTEGER_CONSTANT = Component.translatable("gui.logicnetworks.nodes.integer_constant");
        public static final Component INTEGER_ADD = Component.translatable("gui.logicnetworks.nodes.integer_add");
        public static final Component INTEGER_SUBTRACT = Component.translatable("gui.logicnetworks.nodes.integer_subtract");
        public static final Component INTEGER_MULTIPLY = Component.translatable("gui.logicnetworks.nodes.integer_multiply");
        public static final Component INTEGER_EXPONENT = Component.translatable("gui.logicnetworks.nodes.integer_exponent");
        public static final Component INTEGER_DIVIDE = Component.translatable("gui.logicnetworks.nodes.integer_divide");
        public static final Component INTEGER_MODULO = Component.translatable("gui.logicnetworks.nodes.integer_modulo");
        public static final Component INTEGER_ABS = Component.translatable("gui.logicnetworks.nodes.integer_absolute");
        public static final Component INTEGER_NEGATE = Component.translatable("gui.logicnetworks.nodes.integer_negate");
        public static final Component INTEGER_CLAMP = Component.translatable("gui.logicnetworks.nodes.integer_clamp");
        public static final Component INTEGER_EQUALS = Component.translatable("gui.logicnetworks.nodes.equals");
        public static final Component INTEGER_GREATER_THAN = Component.translatable("gui.logicnetworks.nodes.greater_than");
        public static final Component INTEGER_LESS_THAN = Component.translatable("gui.logicnetworks.nodes.less_than");

        public static final Component ANALOG_READ = Component.translatable("gui.logicnetworks.nodes.analog_read");
        public static final Component ANALOG_WRITE = Component.translatable("gui.logicnetworks.nodes.analog_write");
        public static final Component ANALOG_CONSTANT = Component.translatable("gui.logicnetworks.nodes.analog_constant");
        public static final Component ANALOG_INVERT = Component.translatable("gui.logicnetworks.nodes.analog_invert");

        public static final Component BOOLEAN_READ = Component.translatable("gui.logicnetworks.nodes.boolean_read");
        public static final Component BOOLEAN_WRITE = Component.translatable("gui.logicnetworks.nodes.boolean_write");
        public static final Component BOOLEAN_CONSTANT = Component.translatable("gui.logicnetworks.nodes.boolean_constant");
        public static final Component BOOLEAN_INVERT = Component.translatable("gui.logicnetworks.nodes.boolean_invert");
        public static final Component BOOLEAN_AND = Component.translatable("gui.logicnetworks.nodes.boolean_and");
        public static final Component BOOLEAN_OR = Component.translatable("gui.logicnetworks.nodes.boolean_or");
        public static final Component BOOLEAN_XOR = Component.translatable("gui.logicnetworks.nodes.boolean_xor");
        public static final Component BOOLEAN_NAND = Component.translatable("gui.logicnetworks.nodes.boolean_nand");
        public static final Component BOOLEAN_NOR = Component.translatable("gui.logicnetworks.nodes.boolean_nor");
        public static final Component BOOLEAN_XNOR = Component.translatable("gui.logicnetworks.nodes.boolean_xnor");
    }

    private static DynamicComponent dynamic(String key) {
        return (Object... args) -> Component.translatable(key, args);
    }

    private static DynamicComponent dynamic(String key, Function<MutableComponent, Component> mutator) {
        return (Object... args) -> mutator.apply(Component.translatable(key, args));
    }

    public interface DynamicComponent {
        Component get(Object... args);
    }
}
