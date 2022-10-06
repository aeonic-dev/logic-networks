package design.aeonic.logicnetworks.api.registry;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.impl.registry.OperatorRegistryImpl;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * A registry for {@link Operator}s.
 */
public interface OperatorRegistry {
    OperatorRegistry INSTANCE = OperatorRegistryImpl.INSTANCE;

    /**
     * Registers an {@link Operator} with the given {@link ResourceLocation}, then returns it.
     */
    <T extends Operator> T register(ResourceLocation identifier, T operator);

    /**
     * Gets the key for the given {@link Operator}, or null if it isn't registered.
     */
    ResourceLocation getKey(Operator operator);

    /**
     * Gets an {@link Operator} by its identifier, throwing an error if it isn't registered.
     */
    Operator get(ResourceLocation identifier);

    /**
     * Gets an {@link Optional} wrapping an {@link Operator} by its identifier, empty if it isn't registered.
     */
    Optional<Operator> getOptional(ResourceLocation identifier);

    /**
     * Gets an {@link Operator} by its identifier, or null if it isn't registered.
     */
    @Nullable
    Operator getOrNull(ResourceLocation identifier);
}
