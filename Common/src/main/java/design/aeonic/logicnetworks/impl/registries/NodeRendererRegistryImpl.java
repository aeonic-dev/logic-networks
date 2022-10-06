package design.aeonic.logicnetworks.impl.registries;

import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.registries.NodeRendererRegistry;
import design.aeonic.logicnetworks.api.registries.OperatorRegistry;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentMap;

public class NodeRendererRegistryImpl implements NodeRendererRegistry {
    public static final NodeRendererRegistryImpl INSTANCE = new NodeRendererRegistryImpl();

    private ConcurrentMap<Operator<?>, NodeRenderer<?, ?>> renderers = new java.util.concurrent.ConcurrentHashMap<>();

    private NodeRendererRegistryImpl() {}

    @Override
    public <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R register(O operator, R renderer) {
        renderers.put(operator, renderer);
        return renderer;
    }

    @Override
    public <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R get(O operator) {
        R renderer = getOrNull(operator);
        if (renderer == null) throw new IllegalArgumentException("NodeRenderer for operator " + OperatorRegistry.INSTANCE.getKey(operator) + " is not registered!");
        return renderer;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T, O extends Operator<T>, R extends NodeRenderer<T, ? super O>> R getOrNull(O operator) {
        return (R) renderers.get(operator);
    }
}
