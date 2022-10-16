package design.aeonic.logicnetworks.impl.logic;

import design.aeonic.logicnetworks.api.logic.Edge;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.logic.node.Node;

import java.util.*;
import java.util.stream.Stream;

public class NetworkImpl implements Network {

    private final Map<UUID, Node<?>> nodeMap = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    @Override
    public void addNode(Node<?> node) {
        nodeMap.put(node.getUuid(), node);
    }

    @Override
    public void removeNode(UUID uuid) {
        nodeMap.remove(uuid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Node<T>> T getNode(UUID uuid) {
        return (T) nodeMap.get(uuid);
    }

    @Override
    public void addEdge(Edge edge) {
        if (!edge.isValid()) return;

        edges.removeIf(oldEdge -> oldEdge.getFromNode() == edge.getFromNode() && oldEdge.getFromIndex() == edge.getFromIndex());
        edges.add(edge);
    }

    @Override
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    @Override
    public Stream<Edge> getEdges() {
        return edges.stream();
    }

    @Override
    public Stream<Node<?>> getNodes() {
        return nodeMap.values().stream();
    }
}
