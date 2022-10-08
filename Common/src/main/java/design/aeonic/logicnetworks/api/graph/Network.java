package design.aeonic.logicnetworks.api.graph;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.operators.InputOperator;
import design.aeonic.logicnetworks.api.logic.operators.OutputOperator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Defines a network of nodes that only exists in the graphical GUI; node paths are simplified to collections of
 * operators when the graph is closed for performance.
 */
public class Network {
    protected final Map<UUID, Node<?, ?>> nodes = new HashMap<>();
    public <T, O extends Operator<T>> Node<T, O> addNode(Node<T, O> node) {
        nodes.put(node.uuid, node);
        return node;
    }

    public void compute() {
        // I considered "compiling" the network somehow and discarding extra node information for performance, but
        // for now decided against it - shouldn't be a huge deal to have a few extra nodes in memory and this way does
        // avoid an expensive GC cycle whenever the network would be destroyed.
        getOutputNodes().forEach(this::computeSingle);
    }

    @SuppressWarnings("unchecked")
    private <I> void computeSingle(Node<?, OutputOperator<?>> node) {
        ((OutputOperator<I>) node.operator).write((I) node.getInputSockets()[0].connectedSocket.node.traverse(), node.getOptions());
    }

    public Map<UUID, Node<?, ?>> getNodes() {
        return nodes;
    }

    @SuppressWarnings("unchecked")
    public Stream<Node<?, InputOperator<?>>> getInputNodes() {
        return nodes.values().stream().filter(node -> node.operator instanceof InputOperator<?>).map(node -> (Node<?, InputOperator<?>>) node);
    }

    @SuppressWarnings("unchecked")
    public Stream<Node<?, OutputOperator<?>>> getOutputNodes() {
        return nodes.values().stream().filter(node -> node.operator instanceof OutputOperator<?>).map(node -> (Node<?, OutputOperator<?>>) node);
    }

    public Node<?, ?> getNode(UUID uuid) {
        return nodes.get(uuid);
    }

    public void serialize(CompoundTag tag) {
        ListTag nodesTag = new ListTag();
        for (Node<?, ?> node : nodes.values()) {
            CompoundTag nodeTag = new CompoundTag();
            node.serialize(nodeTag);
            nodesTag.add(nodeTag);
        }
        tag.put("nodes", nodesTag);
    }

    public static Network deserialize(CompoundTag tag) {
        Network network = new Network();
        ListTag nodesTag = tag.getList("nodes", Tag.TAG_COMPOUND);
        for (int i = 0; i < nodesTag.size(); i++) {
            network.addNode(Node.deserialize(network, nodesTag.getCompound(i)));
        }
        for (Node<?, ?> node : network.nodes.values()) {
            node.ready();
        }
        return network;
    }
}
