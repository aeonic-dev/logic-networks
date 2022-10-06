package design.aeonic.logicnetworks.api.client.nodes;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.client.Texture;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * A default node renderer implementation that draws just the barebones -- no options, etc.
 */
public class BaseNodeRenderer<T, O extends Operator<T>> implements NodeRenderer<T, O> {
    public static final Texture DEFAULT_NODE_TEXTURE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/node.png"), 16, 16);

    public final Component title;
    public final int width;
    public final int height;

    public BaseNodeRenderer(Component title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Node<T, O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderUtils.drawRect(stack, boxTexture(node), node.getX(), node.getY(), 200, width, height);
        Minecraft.getInstance().font.draw(stack, title, node.getX() + 5, node.getY() + 5, textColor(node));
    }

    protected int textColor(Node<T, O> node) {
        return 0x1D1E27;
    }

    protected Texture boxTexture(Node<T, O> node) {
        return DEFAULT_NODE_TEXTURE;
    }
}
