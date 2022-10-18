package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.logic.node.Node;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

/**
 * A node type to be registered with {@link CommonRegistries#NODE_TYPES}. Defines a factory for nodes + serialization
 * and deserialization methods.
 */
public interface NodeType<T extends Node<T>> {
    T createNode(UUID uuid, int x, int y);

    default CompoundTag serialize(T node, boolean isClientNode) {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("uuid", node.getUUID());
        tag.putInt("x", node.getX());
        tag.putInt("y", node.getY());

        saveAdditional(node, tag, isClientNode);
        return tag;
    }

    default void saveAdditional(T node, CompoundTag tag, boolean isClientNode) {
        node.saveAdditional(tag, isClientNode);
    }

    default T load(CompoundTag tag) {
        T node = createNode(tag.getUUID("uuid"), tag.getInt("x"), tag.getInt("y"));
        readAdditional(node, tag);
        return node;
    }

    default void readAdditional(T node, CompoundTag tag) {
        node.readAdditional(tag);
    }
}
