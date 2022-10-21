package design.aeonic.logicnetworks.api.logic.network;

import design.aeonic.logicnetworks.api.logic.network.node.Node;

import java.util.UUID;

public class BaseNodeType<T extends Node<T>> implements NodeType<T> {
    private final Constructor<T> constructor;

    public BaseNodeType(Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public T createNode(UUID uuid, int x, int y) {
        return constructor.create(this, uuid, x, y);
    }

    @FunctionalInterface
    public interface Constructor<T extends Node<T>> {
        T create(NodeType<T> nodeType, UUID uuid, int x, int y);
    }
}
