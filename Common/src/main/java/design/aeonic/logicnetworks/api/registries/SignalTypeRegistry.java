package design.aeonic.logicnetworks.api.registries;

import design.aeonic.logicnetworks.api.logic.SignalType;
import design.aeonic.logicnetworks.impl.registries.SignalTypeRegistryImpl;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * A registry for {@link SignalType}s.
 */
public interface SignalTypeRegistry {
    SignalTypeRegistry INSTANCE = SignalTypeRegistryImpl.INSTANCE;

    /**
     * Registers a {@link SignalType} with the given {@link ResourceLocation}, then returns it.
     */
    <S, T extends SignalType<S>> T register(ResourceLocation identifier, T signalType);

    /**
     * Gets the key for the given {@link SignalType}, or null if it isn't registered.
     */
    <T> ResourceLocation getKey(SignalType<T> operator);

    /**
     * Gets a {@link SignalType} by its identifier, throwing an error if it isn't registered.
     */
    <T> SignalType<T> get(ResourceLocation identifier);

    /**
     * Gets a {@link SignalType} by its identifier, or null if it isn't registered.
     */
    @Nullable
    <T> SignalType<T> getOrNull(ResourceLocation identifier);
}
