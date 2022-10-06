package design.aeonic.logicnetworks.impl.registry;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.registry.OperatorRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class OperatorRegistryImpl implements OperatorRegistry {
    public static final OperatorRegistryImpl INSTANCE = new OperatorRegistryImpl();

    private ConcurrentMap<ResourceLocation, Operator> operators = new ConcurrentHashMap<>();

    private OperatorRegistryImpl() {}

    @Override
    public <T extends Operator> T register(ResourceLocation identifier, T operator) {
        operators.put(identifier, operator);
        return operator;
    }

    @Override
    public ResourceLocation getKey(Operator operator) {
        return operators.entrySet().stream()
                .filter(entry -> entry.getValue() == operator)
                .map(ConcurrentMap.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Operator get(ResourceLocation identifier) {
        Operator op = getOrNull(identifier);
        if (op == null) throw new IllegalArgumentException("Operator " + identifier + " is not registered!");
        return op;
    }

    @Override
    public Optional<Operator> getOptional(ResourceLocation identifier) {
        return Optional.ofNullable(getOrNull(identifier));
    }

    @Nullable
    @Override
    public Operator getOrNull(ResourceLocation identifier) {
        return operators.get(identifier);
    }
}
