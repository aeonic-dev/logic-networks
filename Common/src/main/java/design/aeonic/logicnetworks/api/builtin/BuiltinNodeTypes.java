package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.nodes.*;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.BaseNodeType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinNodeTypes {
    public static final NodeType<IntegerConstantNode> INTEGER_CONSTANT = new BaseNodeType<>(IntegerConstantNode::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_EQUALS = new BaseNodeType<>(IntegerComparisonNode.Equals::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_GREATER_THAN = new BaseNodeType<>(IntegerComparisonNode.GreaterThan::new);
    public static final NodeType<IntegerComparisonNode> INTEGER_LESS_THAN = new BaseNodeType<>(IntegerComparisonNode.LessThan::new);
    public static final NodeType<IntegerMathNode> INTEGER_ADD = new BaseNodeType<>(IntegerMathNode.Add::new);
    public static final NodeType<IntegerMathNode> INTEGER_SUBTRACT = new BaseNodeType<>(IntegerMathNode.Subtract::new);
    public static final NodeType<IntegerMathNode> INTEGER_MULTIPLY = new BaseNodeType<>(IntegerMathNode.Multiply::new);
    public static final NodeType<IntegerMathNode> INTEGER_DIVIDE = new BaseNodeType<>(IntegerMathNode.Divide::new);
    public static final NodeType<IntegerMathNode> INTEGER_MODULO = new BaseNodeType<>(IntegerMathNode.Modulo::new);
    public static final NodeType<IntegerMathNode> INTEGER_EXPONENT = new BaseNodeType<>(IntegerMathNode.Exponent::new);
    public static final NodeType<IntegerAbsNode> INTEGER_ABS = new BaseNodeType<>(IntegerAbsNode::new);
    public static final NodeType<IntegerNegateNode> INTEGER_NEGATE = new BaseNodeType<>(IntegerNegateNode::new);
    public static final NodeType<IntegerClampNode> INTEGER_CLAMP = new BaseNodeType<>(IntegerClampNode::new);

    public static final NodeType<LongCurrentTickNode> CURRENT_TICK = new BaseNodeType<>(LongCurrentTickNode::new);
    public static final NodeType<RandomNode> RANDOM = new BaseNodeType<>(RandomNode::new);

    public static final NodeType<AnalogReadNode> ANALOG_READ = new BaseNodeType<>(AnalogReadNode::new);
    public static final NodeType<AnalogWriteNode> ANALOG_WRITE = new BaseNodeType<>(AnalogWriteNode::new);
    public static final NodeType<AnalogConstantNode> ANALOG_CONSTANT = new BaseNodeType<>(AnalogConstantNode::new);
    public static final NodeType<AnalogInvertNode> ANALOG_INVERT = new BaseNodeType<>(AnalogInvertNode::new);

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

    public static void register() {
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_constant"), INTEGER_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_equals"), INTEGER_EQUALS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_greater_than"), INTEGER_GREATER_THAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_less_than"), INTEGER_LESS_THAN);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_add"), INTEGER_ADD);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_subtract"), INTEGER_SUBTRACT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_multiply"), INTEGER_MULTIPLY);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_exponent"), INTEGER_EXPONENT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_divide"), INTEGER_DIVIDE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_modulo"), INTEGER_MODULO);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_abs"), INTEGER_ABS);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_negate"), INTEGER_NEGATE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "integer_clamp"), INTEGER_CLAMP);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "current_tick"), CURRENT_TICK);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "random"), RANDOM);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_read"), ANALOG_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_write"), ANALOG_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_constant"), ANALOG_CONSTANT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_invert"), ANALOG_INVERT);

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
    }
}
