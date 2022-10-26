package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.nodes.logic.*;
import design.aeonic.logicnetworks.api.builtin.nodes.math.*;
import design.aeonic.logicnetworks.api.builtin.nodes.nbt.*;
import design.aeonic.logicnetworks.api.builtin.nodes.string.*;
import design.aeonic.logicnetworks.api.builtin.nodes.world.*;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.BaseNodeType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinNodeTypes {
    public static final NodeType<CacheReadNode> CACHE_READ = new BaseNodeType<>(CacheReadNode::new);
    public static final NodeType<CacheWriteNode> CACHE_WRITE = new BaseNodeType<>(CacheWriteNode::new);
    public static final NodeType<BlockInfoNode> BLOCK_INFO = new BaseNodeType<>(BlockInfoNode::new);

    public static final NodeType<IntegerConstantNode> INTEGER_CONSTANT = new BaseNodeType<>(IntegerConstantNode::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_EQUALS = new BaseNodeType<>(IntegerComparisonNode.Equals::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_GREATER_THAN = new BaseNodeType<>(IntegerComparisonNode.GreaterThan::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_LESS_THAN = new BaseNodeType<>(IntegerComparisonNode.LessThan::new);
    public static final NodeType<IntegerMathNode> INTEGER_MAX = new BaseNodeType<>(IntegerMathNode.Max::new);
    public static final NodeType<IntegerMathNode> INTEGER_MIN = new BaseNodeType<>(IntegerMathNode.Min::new);
    public static final NodeType<IntegerMathNode> INTEGER_ADD = new BaseNodeType<>(IntegerMathNode.Add::new);
    public static final NodeType<IntegerMathNode> INTEGER_SUBTRACT = new BaseNodeType<>(IntegerMathNode.Subtract::new);
    public static final NodeType<IntegerMathNode> INTEGER_MULTIPLY = new BaseNodeType<>(IntegerMathNode.Multiply::new);
    public static final NodeType<IntegerMathNode> INTEGER_DIVIDE = new BaseNodeType<>(IntegerMathNode.Divide::new);
    public static final NodeType<IntegerMathNode> INTEGER_MODULO = new BaseNodeType<>(IntegerMathNode.Modulo::new);
    public static final NodeType<IntegerMathNode> INTEGER_EXPONENT = new BaseNodeType<>(IntegerMathNode.Exponent::new);
    public static final NodeType<IntegerAbsNode> INTEGER_ABS = new BaseNodeType<>(IntegerAbsNode::new);
    public static final NodeType<IntegerNegateNode> INTEGER_NEGATE = new BaseNodeType<>(IntegerNegateNode::new);
    public static final NodeType<IntegerClampNode> INTEGER_CLAMP = new BaseNodeType<>(IntegerClampNode::new);


    public static final NodeType<IntegerMathNode> BITWISE_NOT = new BaseNodeType<>(IntegerMathNode.BitwiseNot::new);
    public static final NodeType<IntegerMathNode> BITWISE_AND = new BaseNodeType<>(IntegerMathNode.BitwiseAnd::new);
    public static final NodeType<IntegerMathNode> BITWISE_OR = new BaseNodeType<>(IntegerMathNode.BitwiseOr::new);
    public static final NodeType<IntegerMathNode> BITWISE_XOR = new BaseNodeType<>(IntegerMathNode.BitwiseXor::new);
    public static final NodeType<IntegerMathNode> BITWISE_NAND = new BaseNodeType<>(IntegerMathNode.BitwiseNand::new);
    public static final NodeType<IntegerMathNode> BITWISE_NOR = new BaseNodeType<>(IntegerMathNode.BitwiseNor::new);
    public static final NodeType<IntegerMathNode> BITWISE_XNOR = new BaseNodeType<>(IntegerMathNode.BitwiseXnor::new);


    public static final NodeType<LongCurrentTickNode> CURRENT_TICK = new BaseNodeType<>(LongCurrentTickNode::new);
    public static final NodeType<IntegerDayTimeNode> DAY_TIME = new BaseNodeType<>(IntegerDayTimeNode::new);
    public static final NodeType<RandomNode> RANDOM = new BaseNodeType<>(RandomNode::new);
    public static final NodeType<LongPackNode> LONG_PACK = new BaseNodeType<>(LongPackNode::new);
    public static final NodeType<LongUnpackNode> LONG_UNPACK = new BaseNodeType<>(LongUnpackNode::new);

    public static final NodeType<AnalogReadNode> ANALOG_READ = new BaseNodeType<>(AnalogReadNode::new);
    public static final NodeType<AnalogWriteNode> ANALOG_WRITE = new BaseNodeType<>(AnalogWriteNode::new);
    public static final NodeType<AnalogConstantNode> ANALOG_CONSTANT = new BaseNodeType<>(AnalogConstantNode::new);
    public static final NodeType<AnalogInvertNode> ANALOG_INVERT = new BaseNodeType<>(AnalogInvertNode::new);
    public static final NodeType<AnalogPackNode> ANALOG_PACK = new BaseNodeType<>(AnalogPackNode::new);
    public static final NodeType<AnalogUnpackNode> ANALOG_UNPACK = new BaseNodeType<>(AnalogUnpackNode::new);

    public static final NodeType<BooleanReadNode> BOOLEAN_READ = new BaseNodeType<>(BooleanReadNode::new);
    public static final NodeType<BooleanWriteNode> BOOLEAN_WRITE = new BaseNodeType<>(BooleanWriteNode::new);
    public static final NodeType<BooleanConstantNode> BOOLEAN_CONSTANT = new BaseNodeType<>(BooleanConstantNode::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NOT = new BaseNodeType<>(BooleanLogicNode.Not::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_AND = new BaseNodeType<>(BooleanLogicNode.And::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_OR = new BaseNodeType<>(BooleanLogicNode.Or::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_XOR = new BaseNodeType<>(BooleanLogicNode.Xor::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NAND = new BaseNodeType<>(BooleanLogicNode.Nand::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NOR = new BaseNodeType<>(BooleanLogicNode.Nor::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_XNOR = new BaseNodeType<>(BooleanLogicNode.Xnor::new);

    public static final NodeType<StringConstantNode> STRING_CONSTANT = new BaseNodeType<>(StringConstantNode::new);
    public static final NodeType<StringLengthNode> STRING_LENGTH = new BaseNodeType<>(StringLengthNode::new);
    public static final NodeType<StringEqualsNode> STRING_EQUALS = new BaseNodeType<>(StringEqualsNode::new);
    public static final NodeType<StringContainsNode> STRING_CONTAINS = new BaseNodeType<>(StringContainsNode::new);
    public static final NodeType<SubstringNode> SUBSTRING = new BaseNodeType<>(SubstringNode::new);
    public static final NodeType<StringReplaceNode> STRING_REPLACE = new BaseNodeType<>(StringReplaceNode::new);
    public static final NodeType<StringJoinNode> STRING_JOIN = new BaseNodeType<>(StringJoinNode::new);
    public static final NodeType<StringToLowerNode> STRING_TO_LOWER = new BaseNodeType<>(StringToLowerNode::new);
    public static final NodeType<StringToUpperNode> STRING_TO_UPPER = new BaseNodeType<>(StringToUpperNode::new);

    public static final NodeType<EmptyNBTNode> EMPTY_NBT_TAG = new BaseNodeType<>(EmptyNBTNode::new);
    public static final NodeType<NBTReadNode> NBT_READ = new BaseNodeType<>(NBTReadNode::new);
    public static final NodeType<NBTGetNode<CompoundTag>> NBT_GET_COMPOUND = new BaseNodeType<>(NBTGetNode.Compound::new);
    public static final NodeType<NBTGetNode<String>> NBT_GET_STRING = new BaseNodeType<>(NBTGetNode.Str::new);
    public static final NodeType<NBTGetNode<Integer>> NBT_GET_INT = new BaseNodeType<>(NBTGetNode.Int::new);
    public static final NodeType<NBTGetNode<Long>> NBT_GET_LONG = new BaseNodeType<>(NBTGetNode.Lng::new);
    public static final NodeType<NBTGetNode<Boolean>> NBT_GET_BOOLEAN = new BaseNodeType<>(NBTGetNode.Bool::new);
    public static final NodeType<NBTGetNode<ListTag>> NBT_GET_LIST = new BaseNodeType<>(NBTGetNode.List::new);
    public static final NodeType<NBTPutNode<CompoundTag>> NBT_PUT_COMPOUND = new BaseNodeType<>(NBTPutNode.Compound::new);
    public static final NodeType<NBTPutNode<String>> NBT_PUT_STRING = new BaseNodeType<>(NBTPutNode.Str::new);
    public static final NodeType<NBTPutNode<Integer>> NBT_PUT_INT = new BaseNodeType<>(NBTPutNode.Int::new);
    public static final NodeType<NBTPutNode<Long>> NBT_PUT_LONG = new BaseNodeType<>(NBTPutNode.Lng::new);
    public static final NodeType<NBTPutNode<Boolean>> NBT_PUT_BOOLEAN = new BaseNodeType<>(NBTPutNode.Bool::new);
    public static final NodeType<NBTPutNode<ListTag>> NBT_PUT_LIST = new BaseNodeType<>(NBTPutNode.List::new);

    public static final NodeType<EmptyListNode> EMPTY_LIST = new BaseNodeType<>(EmptyListNode::new);
    public static final NodeType<ListLengthNode> LIST_LENGTH = new BaseNodeType<>(ListLengthNode::new);
    public static final NodeType<ListEqualsNode>  LIST_EQUALS = new BaseNodeType<>(ListEqualsNode::new);
    public static final NodeType<ListGetNode<CompoundTag>> LIST_GET_COMPOUND = new BaseNodeType<>(ListGetNode.Compound::new);
    public static final NodeType<ListGetNode<String>> LIST_GET_STRING = new BaseNodeType<>(ListGetNode.Str::new);
    public static final NodeType<ListGetNode<Integer>> LIST_GET_INT = new BaseNodeType<>(ListGetNode.Int::new);
    public static final NodeType<ListGetNode<Long>> LIST_GET_LONG = new BaseNodeType<>(ListGetNode.Lng::new);
    public static final NodeType<ListGetNode<Boolean>> LIST_GET_BOOLEAN = new BaseNodeType<>(ListGetNode.Bool::new);
    public static final NodeType<ListAddNode<CompoundTag>> LIST_ADD_COMPOUND = new BaseNodeType<>(ListAddNode.Compound::new);
    public static final NodeType<ListAddNode<String>> LIST_ADD_STRING = new BaseNodeType<>(ListAddNode.Str::new);
    public static final NodeType<ListAddNode<Integer>> LIST_ADD_INT = new BaseNodeType<>(ListAddNode.Int::new);
    public static final NodeType<ListAddNode<Long>> LIST_ADD_LONG = new BaseNodeType<>(ListAddNode.Lng::new);
    public static final NodeType<ListAddNode<Boolean>> LIST_ADD_BOOLEAN = new BaseNodeType<>(ListAddNode.Bool::new);

    public static void register() {
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "cache_read"), CACHE_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "cache_write"), CACHE_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "block_info"), BLOCK_INFO);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_constant"), INTEGER_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_equals"), INTEGER_EQUALS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_greater_than"), INTEGER_GREATER_THAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_less_than"), INTEGER_LESS_THAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_max"), INTEGER_MAX);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_min"), INTEGER_MIN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_add"), INTEGER_ADD);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_subtract"), INTEGER_SUBTRACT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_multiply"), INTEGER_MULTIPLY);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_exponent"), INTEGER_EXPONENT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_divide"), INTEGER_DIVIDE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_modulo"), INTEGER_MODULO);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_abs"), INTEGER_ABS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_negate"), INTEGER_NEGATE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_clamp"), INTEGER_CLAMP);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_not"), BITWISE_NOT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_and"), BITWISE_AND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_or"), BITWISE_OR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_xor"), BITWISE_XOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_nand"), BITWISE_NAND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_nor"), BITWISE_NOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "bitwise_xnor"), BITWISE_XNOR);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "current_tick"), CURRENT_TICK);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "day_time"), DAY_TIME);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "random"), RANDOM);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "long_pack"), LONG_PACK);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "long_unpack"), LONG_UNPACK);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_read"), ANALOG_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_write"), ANALOG_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_constant"), ANALOG_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_invert"), ANALOG_INVERT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_pack"), ANALOG_PACK);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_unpack"), ANALOG_UNPACK);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_read"), BOOLEAN_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_write"), BOOLEAN_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_constant"), BOOLEAN_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_not"), BOOLEAN_NOT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_and"), BOOLEAN_AND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_or"), BOOLEAN_OR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xor"), BOOLEAN_XOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nand"), BOOLEAN_NAND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nor"), BOOLEAN_NOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xnor"), BOOLEAN_XNOR);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_constant"), STRING_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_length"), STRING_LENGTH);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_equals"), STRING_EQUALS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_contains"), STRING_CONTAINS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "substring"), SUBSTRING);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_replace"), STRING_REPLACE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_join"), STRING_JOIN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_to_lower"), STRING_TO_LOWER);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "string_to_upper"), STRING_TO_UPPER);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "empty_nbt_tag"), EMPTY_NBT_TAG);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_read"), NBT_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_compound"), NBT_GET_COMPOUND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_string"), NBT_GET_STRING);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_int"), NBT_GET_INT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_long"), NBT_GET_LONG);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_boolean"), NBT_GET_BOOLEAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_get_list"), NBT_GET_LIST);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_compound"), NBT_PUT_COMPOUND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_string"), NBT_PUT_STRING);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_int"), NBT_PUT_INT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_long"), NBT_PUT_LONG);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_boolean"), NBT_PUT_BOOLEAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "nbt_put_list"), NBT_PUT_LIST);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "empty_list"), EMPTY_LIST);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_length"), LIST_LENGTH);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_equals"), LIST_EQUALS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_get_compound"), LIST_GET_COMPOUND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_get_string"), LIST_GET_STRING);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_get_int"), LIST_GET_INT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_get_long"), LIST_GET_LONG);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_get_boolean"), LIST_GET_BOOLEAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_add_compound"), LIST_ADD_COMPOUND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_add_string"), LIST_ADD_STRING);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_add_int"), LIST_ADD_INT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_add_long"), LIST_ADD_LONG);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "list_add_boolean"), LIST_ADD_BOOLEAN);
    }
}
