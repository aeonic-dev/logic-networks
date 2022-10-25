package design.aeonic.logicnetworks.api.core;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
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
        public static final Component SEED = Component.translatable("gui.logicnetworks.generic.seed");
        public static final Component MOST_SIGNIFICANT = Component.translatable("gui.logicnetworks.generic.most_significant");
        public static final Component LEAST_SIGNIFICANT = Component.translatable("gui.logicnetworks.generic.least_significant");
        public static final Component MIN_VALUE = Component.translatable("gui.logicnetworks.generic.min_value");
        public static final Component MAX_VALUE = Component.translatable("gui.logicnetworks.generic.max_value");
        public static final Component TICK_SEED = Component.translatable("gui.logicnetworks.generic.tick_seed");
        public static final Component TIME_OF_DAY = Component.translatable("gui.logicnetworks.generic.time_of_day");
        public static final Component ANCHOR_LINK = Component.translatable("gui.logicnetworks.generic.anchor_link");

        public static final Component BIT_ZERO = Component.translatable("gui.logicnetworks.generic.bit_zero");
        public static final Component BIT_ONE = Component.translatable("gui.logicnetworks.generic.bit_one");
        public static final Component BIT_TWO = Component.translatable("gui.logicnetworks.generic.bit_two");
        public static final Component BIT_THREE = Component.translatable("gui.logicnetworks.generic.bit_three");
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
        public static final Component INTEGER = Component.translatable("gui.logicnetworks.signals.integer")
                .withStyle(s -> s.withColor(BuiltinSignalTypes.INTEGER.color));
        public static final Component LONG = Component.translatable("gui.logicnetworks.signals.long")
                .withStyle(s -> s.withColor(BuiltinSignalTypes.LONG.color));
        public static final Component ANALOG = Component.translatable("gui.logicnetworks.signals.analog")
                .withStyle(s -> s.withColor(BuiltinSignalTypes.ANALOG.color));
        public static final Component BOOLEAN = Component.translatable("gui.logicnetworks.signals.boolean")
                .withStyle(s -> s.withColor(BuiltinSignalTypes.BOOLEAN.color));
        public static final Component STRING = Component.translatable("gui.logicnetworks.signals.string")
                .withStyle(s -> s.withColor(BuiltinSignalTypes.STRING.color));
    }

    public static final class Nodes {
        public static final Component CACHE_READ = Component.translatable("gui.logicnetworks.nodes.cache_read");
        public static final Component CACHE_WRITE = Component.translatable("gui.logicnetworks.nodes.cache_write");

        public static final Component INTEGER_CONSTANT = Component.translatable("gui.logicnetworks.nodes.integer_constant");
        public static final Component INTEGER_MAX = Component.translatable("gui.logicnetworks.nodes.integer_max");
        public static final Component INTEGER_MIN = Component.translatable("gui.logicnetworks.nodes.integer_min");
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
        public static final Component BITWISE_NOT = Component.translatable("gui.logicnetworks.nodes.bitwise_not");
        public static final Component BITWISE_AND = Component.translatable("gui.logicnetworks.nodes.bitwise_and");
        public static final Component BITWISE_OR = Component.translatable("gui.logicnetworks.nodes.bitwise_or");
        public static final Component BITWISE_XOR = Component.translatable("gui.logicnetworks.nodes.bitwise_xor");
        public static final Component BITWISE_NAND = Component.translatable("gui.logicnetworks.nodes.bitwise_nand");
        public static final Component BITWISE_NOR = Component.translatable("gui.logicnetworks.nodes.bitwise_nor");
        public static final Component BITWISE_XNOR = Component.translatable("gui.logicnetworks.nodes.bitwise_xnor");

        public static final Component CURRENT_TICK = Component.translatable("gui.logicnetworks.nodes.current_tick");
        public static final Component DAY_TIME = Component.translatable("gui.logicnetworks.nodes.day_time");
        public static final Component RANDOM = Component.translatable("gui.logicnetworks.nodes.random");
        public static final Component LONG_PACK = Component.translatable("gui.logicnetworks.nodes.long_pack");
        public static final Component LONG_UNPACK = Component.translatable("gui.logicnetworks.nodes.long_unpack");

        public static final Component ANALOG_READ = Component.translatable("gui.logicnetworks.nodes.analog_read");
        public static final Component ANALOG_WRITE = Component.translatable("gui.logicnetworks.nodes.analog_write");
        public static final Component ANALOG_CONSTANT = Component.translatable("gui.logicnetworks.nodes.analog_constant");
        public static final Component ANALOG_INVERT = Component.translatable("gui.logicnetworks.nodes.analog_invert");
        public static final Component ANALOG_PACK = Component.translatable("gui.logicnetworks.nodes.analog_pack");
        public static final Component ANALOG_UNPACK = Component.translatable("gui.logicnetworks.nodes.analog_unpack");

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

        public static final Component STRING_CONSTANT = Component.translatable("gui.logicnetworks.nodes.string_constant");
        public static final Component STRING_LENGTH = Component.translatable("gui.logicnetworks.nodes.string_length");
        public static final Component STRING_EQUALS = Component.translatable("gui.logicnetworks.nodes.string_equals");
        public static final Component STRING_CONTAINS = Component.translatable("gui.logicnetworks.nodes.string_contains");
        public static final Component STRING_INDEX_OF = Component.translatable("gui.logicnetworks.nodes.string_index_of");
        public static final Component STRING_SUBSTRING = Component.translatable("gui.logicnetworks.nodes.string_substring");
        public static final Component STRING_REPLACE = Component.translatable("gui.logicnetworks.nodes.string_replace");
        public static final Component STRING_SPLIT = Component.translatable("gui.logicnetworks.nodes.string_split");
        public static final Component STRING_JOIN = Component.translatable("gui.logicnetworks.nodes.string_join");
        public static final Component STRING_TO_LOWER = Component.translatable("gui.logicnetworks.nodes.string_to_lower");
        public static final Component STRING_TO_UPPER = Component.translatable("gui.logicnetworks.nodes.string_to_upper");
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
