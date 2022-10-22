package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.nodes.*;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.BaseNodeType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinNodeTypes {
    public static final NodeType<AnalogReadNode> ANALOG_READ = new BaseNodeType<>(AnalogReadNode::new);
    public static final NodeType<AnalogWriteNode> ANALOG_WRITE = new BaseNodeType<>(AnalogWriteNode::new);
    public static final NodeType<AnalogInvertNode> ANALOG_INVERT = new BaseNodeType<>(AnalogInvertNode::new);
    public static final NodeType<AnalogAddNode> ANALOG_ADD = new BaseNodeType<>(AnalogAddNode::new);
    public static final NodeType<AnalogSubtractNode> ANALOG_SUBTRACT = new BaseNodeType<>(AnalogSubtractNode::new);
    public static final NodeType<AnalogMultiplyNode> ANALOG_MULTIPLY = new BaseNodeType<>(AnalogMultiplyNode::new);
    public static final NodeType<AnalogDivideNode> ANALOG_DIVIDE = new BaseNodeType<>(AnalogDivideNode::new);

    public static final NodeType<BooleanReadNode> BOOLEAN_READ = new BaseNodeType<>(BooleanReadNode::new);
    public static final NodeType<BooleanWriteNode> BOOLEAN_WRITE = new BaseNodeType<>(BooleanWriteNode::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NOT = new BaseNodeType<>(BooleanLogicNode.Not::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_AND = new BaseNodeType<>(BooleanLogicNode.And::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_OR = new BaseNodeType<>(BooleanLogicNode.Or::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_XOR = new BaseNodeType<>(BooleanLogicNode.Xor::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NAND = new BaseNodeType<>(BooleanLogicNode.Nand::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_NOR = new BaseNodeType<>(BooleanLogicNode.Nor::new);
    public static final NodeType<BooleanLogicNode> BOOLEAN_XNOR = new BaseNodeType<>(BooleanLogicNode.Xnor::new);

    public static void register() {
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_read"), ANALOG_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_write"), ANALOG_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_invert"), ANALOG_INVERT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_add"), ANALOG_ADD);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_subtract"), ANALOG_SUBTRACT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_multiply"), ANALOG_MULTIPLY);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_divide"), ANALOG_DIVIDE);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_read"), BOOLEAN_READ);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_write"), BOOLEAN_WRITE);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_not"), BOOLEAN_NOT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_and"), BOOLEAN_AND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_or"), BOOLEAN_OR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xor"), BOOLEAN_XOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nand"), BOOLEAN_NAND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nor"), BOOLEAN_NOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xnor"), BOOLEAN_XNOR);
    }
}
