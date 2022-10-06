package design.aeonic.logicnetworks.api.registries;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.impl.registries.OperatorRegistryImpl;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * A registry for {@link Operator}s.
 */
public interface OperatorRegistry {
    OperatorRegistry INSTANCE = OperatorRegistryImpl.INSTANCE;

    /**
     * Registers an {@link Operator} with the given {@link ResourceLocation}, then returns it.
     */
    <T, O extends Operator<T>> O register(ResourceLocation identifier, O operator);

    /**
     * Gets the key for the given {@link Operator}, or null if it isn't registered.
     */
    ResourceLocation getKey(Operator<?> operator);

    /**
     * Gets an {@link Operator} by its identifier, throwing an error if it isn't registered.
     */
    <T, O extends Operator<T>> O get(ResourceLocation identifier);

    /**
     * Gets an {@link Operator} by its identifier, or null if it isn't registered.
     */
    @Nullable
    <T, O extends Operator<T>> O getOrNull(ResourceLocation identifier);
}
