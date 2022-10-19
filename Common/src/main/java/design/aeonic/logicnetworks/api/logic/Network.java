package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.node.Node;
import design.aeonic.logicnetworks.api.logic.node.SinkNode;
import design.aeonic.logicnetworks.api.logic.node.SourceNode;
import design.aeonic.logicnetworks.impl.logic.NetworkImpl;
import design.aeonic.logicnetworks.impl.logic.TopologicalCompiledNetwork;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * A network of logic nodes.
 */
public interface Network {

    /**
     * Compiles the network to a simplified executable representation.
     */
    default CompiledNetwork compile() {
        return new TopologicalCompiledNetwork(this);
    }

    /**
     * Adds a node to the network.
     */
    void addNode(Node<?> node);

    /**
     * Removes a node from the network.
     */
    void removeNode(UUID uuid);

    /**
     * Gets a node by its UUID.
     */
    <T extends Node<T>> T getNode(UUID uuid);

    /**
     * Adds an edge to the network.
     */
    void addEdge(Edge edge);

    /**
     * Removes an edge from the network.
     */
    void removeEdge(Edge edge);

    /**
     * Gets all edges originating from one of the given node's outputs.
     */
    default Stream<Edge> getEdgesFrom(Node<?> node) {
        return getEdges().filter(edge -> edge.getFromNode().equals(node.getUUID()));
    }

    /**
     * Gets all edges terminating at one of the given node's inputs.
     */
    default Stream<Edge> getEdgesTo(Node<?> node) {
        return getEdges().filter(edge -> edge.getToNode().equals(node.getUUID()));
    }

    /**
     * Gets a list of all edges in the network.
     */
    Stream<Edge> getEdges();

    /**
     * Gets all source nodes (inputs) in the network.
     */
    default Stream<SourceNode<?>> getSourceNodes() {
        return getNodes().filter(node -> node instanceof SourceNode<?>).map(node -> (SourceNode<?>) node);
    }

    /**
     * Gets all sink nodes (outputs) in the network.
     */
    default Stream<SinkNode<?>> getSinkNodes() {
        return getNodes().filter(node -> node instanceof SinkNode<?>).map(node -> (SinkNode<?>) node);
    }

    /**
     * `isClient` is used to determine whether values from input widgets should be serialized to normal fields.
     * Should only be true if this method is being called from an existing screen that's modifying the network's nodes.
     */
    default void serialize(CompoundTag tag) {
        ListTag nodeList = tag.getList("nodes", Tag.TAG_COMPOUND);
        getNodes().forEach(node -> {
            CompoundTag nodeTag = serializeNode(node);
            nodeList.add(nodeTag);
        });
        ListTag edgeList = tag.getList("edges", Tag.TAG_COMPOUND);
        getEdges().forEach(edge -> {
            CompoundTag edgeTag = new CompoundTag();
            edge.serialize(edgeTag);
            edgeList.add(edgeTag);
            Constants.LOG.info("serialized edge {}", edgeTag);
        });
        tag.put("nodes", nodeList);
        tag.put("edges", edgeList);
    }

    @SuppressWarnings("unchecked")
    default <T extends Node<T>> CompoundTag serializeNode(Node<T> node) {
        CompoundTag tag = node.getNodeType().serialize((T) node);
        tag.putString("type", CommonRegistries.NODE_TYPES.getKey(node.getNodeType()).toString());
        return tag;
    }

    static Network deserialize(CompoundTag tag) {
        Network network = new NetworkImpl();
        ListTag nodeList = tag.getList("nodes", Tag.TAG_COMPOUND);
        nodeList.forEach(nodeTag -> {
            network.addNode(deserializeNode((CompoundTag) nodeTag));
        });
        ListTag edgeList = tag.getList("edges", Tag.TAG_COMPOUND);
        edgeList.forEach(edgeTag -> {
            network.addEdge(Edge.deserialize((CompoundTag) edgeTag));
        });
        return network;
    }

    @SuppressWarnings("unchecked")
    static <T extends Node<T>> T deserializeNode(CompoundTag tag) {
        NodeType<T> type = (NodeType<T>) CommonRegistries.NODE_TYPES.get(new ResourceLocation(tag.getString("type")));
        return type.load(tag);
    }

    /**
     * Gets a list of all nodes in the network.
     */
    Stream<Node<?>> getNodes();

    interface IHasNetwork {
        Network getNetwork();

        void setNetwork(Network network);
    }
}
