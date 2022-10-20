package design.aeonic.logicnetworks.impl.logic;

import design.aeonic.logicnetworks.api.logic.network.Edge;
import design.aeonic.logicnetworks.api.logic.network.Network;
import design.aeonic.logicnetworks.api.logic.network.node.Node;

import java.util.*;
import java.util.stream.Stream;

public class NetworkImpl implements Network {

    private final Map<UUID, Node<?>> nodeMap = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    @Override
    public void addNode(Node<?> node) {
        nodeMap.put(node.getUUID(), node);
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
        if (isCyclicEdge(getNode(edge.getToNode()), edge)) return;

        edges.removeIf(oldEdge -> oldEdge.getToNode().equals(edge.getToNode()) && oldEdge.getToIndex() == edge.getToIndex());
        edges.add(edge);
    }

    public boolean isCyclicEdge(Node<?> node, Edge edge) {
        // TODO: Probably a better way to do this
        if (edge.getFromNode().equals(node.getUUID())) return true;
        for (Edge e : getEdgesTo(getNode(edge.getFromNode())).toList()) {
            if (e.getFromNode().equals(node.getUUID())) return true;
            if (isCyclicEdge(node, e)) return true;
        }
        return false;
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
