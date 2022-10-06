package design.aeonic.logicnetworks.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.graph.Node;
import design.aeonic.logicnetworks.api.logic.Operator;

/**
 * A renderer for a specific operator type, registered via
 */
@FunctionalInterface
public interface NodeRenderer<T, O extends Operator<T>> {
    void draw(Node<T, O> node, PoseStack stack, int mouseX, int mouseY, float partialTicks);
}
