package design.aeonic.logicnetworks.api.util;

import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public interface SimpleRegistry<T> {
    /**
     * Registers the given object to the registry.
     */
    void register(ResourceLocation key, T value);

    /**
     * Gets the object with the given key, throwing an error if it hasn't been registered.
     */
    T get(ResourceLocation key);

    /**
     * Gets the object with the given key, or the default value if it hasn't been registered.
     */
    T getOrDefault(ResourceLocation key, T defaultValue);

    /**
     * Gets the key for the given object, or null if it hasn't been registered.
     */
    T getOrNull(ResourceLocation key);

    ResourceLocation getKey(T value);

    class ConcurrentRegistry<T> implements SimpleRegistry<T> {
        private final ConcurrentMap<ResourceLocation, T> registry = new ConcurrentHashMap<>();
        private final ConcurrentMap<T, ResourceLocation> inverse = new ConcurrentHashMap<>();

        @Override
        public void register(ResourceLocation key, T value) {
            registry.put(key, value);
            inverse.put(value, key);
        }

        @Override
        public T get(ResourceLocation key) {
            T obj = getOrNull(key);
            if (obj == null) throw new IllegalArgumentException("Object " + key + " is not registered!");
            return obj;
        }

        @Override
        public T getOrDefault(ResourceLocation key, T defaultValue) {
            T obj = getOrNull(key);
            return obj == null ? defaultValue : obj;
        }

        @Override
        public T getOrNull(ResourceLocation key) {
            return registry.get(key);
        }

        @Override
        public ResourceLocation getKey(T value) {
            return inverse.get(value);
        }
    }
}
