package design.aeonic.logicnetworks.impl.registry;

import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.api.registry.SignalTypeRegistry;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class SignalTypeRegistryImpl implements SignalTypeRegistry {
    public static final SignalTypeRegistryImpl INSTANCE = new SignalTypeRegistryImpl();

    private ConcurrentMap<ResourceLocation, SignalType<?>> signalTypes = new ConcurrentHashMap<>();

    private SignalTypeRegistryImpl() {}

    @Override
    public <S, T extends SignalType<S>> T register(ResourceLocation identifier, T signalType) {
        signalTypes.put(identifier, signalType);
        return signalType;
    }

    @Override
    public <T> ResourceLocation getKey(SignalType<T> operator) {
        return signalTypes.entrySet().stream()
                .filter(entry -> entry.getValue() == operator)
                .map(ConcurrentMap.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public <T> SignalType<T> get(ResourceLocation identifier) {
        SignalType<T> signalType = getOrNull(identifier);
        if (signalType == null) throw new IllegalArgumentException("Signal type " + identifier + " is not registered!");
        return signalType;
    }

    @Override
    public <T> Optional<SignalType<T>> getOptional(ResourceLocation identifier) {
        return Optional.ofNullable(getOrNull(identifier));
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> SignalType<T> getOrNull(ResourceLocation identifier) {
        return (SignalType<T>) signalTypes.get(identifier);
    }
}
