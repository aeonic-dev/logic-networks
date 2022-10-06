package design.aeonic.logicnetworks.impl.registries;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.registries.OperatorRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class OperatorRegistryImpl implements OperatorRegistry {
    public static final OperatorRegistryImpl INSTANCE = new OperatorRegistryImpl();

    private ConcurrentMap<ResourceLocation, Operator<?>> operators = new ConcurrentHashMap<>();

    private OperatorRegistryImpl() {}

    @Override
    public <T, O extends Operator<T>> O register(ResourceLocation identifier, O operator) {
        operators.put(identifier, operator);
        return operator;
    }

    @Override
    public ResourceLocation getKey(Operator<?> operator) {
        return operators.entrySet().stream()
                .filter(entry -> entry.getValue() == operator)
                .map(ConcurrentMap.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public <T, O extends Operator<T>> O get(ResourceLocation identifier) {
        O op = getOrNull(identifier);
        if (op == null) throw new IllegalArgumentException("Operator " + identifier + " is not registered!");
        return op;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T, O extends Operator<T>> O getOrNull(ResourceLocation identifier) {
        return (O) operators.get(identifier);
    }
}
