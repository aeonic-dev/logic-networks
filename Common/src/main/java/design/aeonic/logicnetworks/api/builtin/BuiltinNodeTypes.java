package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.nodes.AnalogAddNode;
import design.aeonic.logicnetworks.api.builtin.nodes.AnalogInvertNode;
import design.aeonic.logicnetworks.api.builtin.nodes.BooleanLogicNode;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.network.BaseNodeType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinNodeTypes {
    public static final NodeType<?> ANALOG_ADD = new BaseNodeType<>(AnalogAddNode::new);
    public static final NodeType<?> ANALOG_INVERT = new BaseNodeType<>(AnalogInvertNode::new);

    public static final NodeType<?> BOOLEAN_NOT = new BaseNodeType<>(BooleanLogicNode.Not::new);
    public static final NodeType<?> BOOLEAN_AND = new BaseNodeType<>(BooleanLogicNode.And::new);
    public static final NodeType<?> BOOLEAN_OR = new BaseNodeType<>(BooleanLogicNode.Or::new);
    public static final NodeType<?> BOOLEAN_XOR = new BaseNodeType<>(BooleanLogicNode.Xor::new);
    public static final NodeType<?> BOOLEAN_NAND = new BaseNodeType<>(BooleanLogicNode.Nand::new);
    public static final NodeType<?> BOOLEAN_NOR = new BaseNodeType<>(BooleanLogicNode.Nor::new);
    public static final NodeType<?> BOOLEAN_XNOR = new BaseNodeType<>(BooleanLogicNode.Xnor::new);

    public static void register() {
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_add"), ANALOG_ADD);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_invert"), ANALOG_INVERT);

        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_not"), BOOLEAN_NOT);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_and"), BOOLEAN_AND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_or"), BOOLEAN_OR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xor"), BOOLEAN_XOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nand"), BOOLEAN_NAND);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_nor"), BOOLEAN_NOR);
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "boolean_xnor"), BOOLEAN_XNOR);
    }
}
