package design.aeonic.logicnetworks.api.client.nodes;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.client.Texture;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.logic.Operator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class MissingNodeRenderer extends BaseNodeRenderer<Object, Operator<Object>> {
    public static final MissingNodeRenderer INSTANCE = new MissingNodeRenderer();

    public static final Texture DEFAULT_NODE_TEXTURE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node_missing.png"), 16, 16);
    public static final Texture DEFAULT_HOVERED_NODE_TEXTURE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node_missing_hovered.png"), 16, 16);

    public MissingNodeRenderer() {
        super(Component.literal("Missing Renderer"), 100, 50);
    }

    @Override
    protected Texture boxTexture(Node<Object, Operator<Object>> node, int mouseX, int mouseY) {
        return isHovered(node, mouseX, mouseY) ? DEFAULT_HOVERED_NODE_TEXTURE: DEFAULT_NODE_TEXTURE;
    }

    @Override
    protected int textColor(Node<Object, Operator<Object>> node) {
        return 0xFFFFFF;
    }
}
