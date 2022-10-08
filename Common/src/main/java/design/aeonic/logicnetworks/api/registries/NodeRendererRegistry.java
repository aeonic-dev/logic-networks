package design.aeonic.logicnetworks.api.registries;

import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.impl.registries.NodeRendererRegistryImpl;

import javax.annotation.Nullable;

/**
 * A client registry for {@link NodeRenderer}s.
 */
public interface NodeRendererRegistry {
    NodeRendererRegistry INSTANCE = NodeRendererRegistryImpl.INSTANCE;

    /**
     * Registers a {@link NodeRenderer} for the given operator, then returns it.
     */
    <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R register(O operator, R renderer);

    /**
     * Gets a {@link NodeRenderer} by its associated operator, or {@link design.aeonic.logicnetworks.api.client.nodes.MissingNodeRenderer#INSTANCE} if it isn't registered.
     */
    <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R get(O operator);

    /**
     * Gets a {@link NodeRenderer} by its associated operator, or null if it isn't registered.
     */
    @Nullable
    <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R getOrNull(O operator);
}
