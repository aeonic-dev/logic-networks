package design.aeonic.logicnetworks.api.builtin;

import design.aeonic.logicnetworks.api.builtin.nodes.AnalogAddNode;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.BaseNodeType;
import design.aeonic.logicnetworks.api.logic.NodeType;
import net.minecraft.resources.ResourceLocation;

public final class BuiltinNodeTypes {
    public static final NodeType<?> ANALOG_ADD = new BaseNodeType<>(AnalogAddNode::new);

    public static void register() {
        CommonRegistries.NODE_TYPES.register(new ResourceLocation(Constants.MOD_ID, "analog_add"), ANALOG_ADD);
    }
}
