package design.aeonic.logicnetworks.impl.logic;

import design.aeonic.logicnetworks.api.logic.network.Edge;
import design.aeonic.logicnetworks.api.logic.network.Network;
import design.aeonic.logicnetworks.api.logic.network.node.Node;
import design.aeonic.logicnetworks.api.logic.network.node.OperatorNode;
import design.aeonic.logicnetworks.api.logic.network.node.SinkNode;
import design.aeonic.logicnetworks.api.logic.network.node.SourceNode;
import design.aeonic.logicnetworks.api.logic.network.CompiledNetwork;

import java.util.*;

public class TopologicalCompiledNetwork implements CompiledNetwork {
    private final List<List<Node<?>>> layers = new ArrayList<>();
    private final Map<UUID, Object[]> inputBuffer = new HashMap<>();
    private final Map<UUID, Edge[]> connections = new HashMap<>();

    public TopologicalCompiledNetwork(Network network) {
        network.getSourceNodes().forEach(node -> sort(network, node, 0));
    }

    @Override
    public void tick() {
        for (List<Node<?>> layer : layers) {
            for (Node<?> node : layer) {
                if (node instanceof SinkNode<?> sink) {
                    Object[] input = inputBuffer.get(sink.getUUID());
                    // Don't write to the sink if any input is null
                    for (Object obj : input) {
                        if (obj == null) return;
                    }
                    sink.accept(input);
                } else if (node instanceof SourceNode<?> source){
                    Object[] output = source.get();
                    for (Edge edge: connections.get(node.getUUID())) {
                        inputBuffer.get(edge.getToNode())[edge.getToIndex()] = output[edge.getFromIndex()];
                    }
                } else if (node instanceof OperatorNode<?> operator) {
                    Object[] input = inputBuffer.get(operator.getUUID());
                    if (input == null) return;

                    // Validate operator inputs before continuing; should propagate the rest of the changes
                    if (!operator.validate(input)) return;

                    Object[] output = operator.evaluate(input);
                    for (Edge edge: connections.get(node.getUUID())) {
                        inputBuffer.get(edge.getToNode())[edge.getToIndex()] =  output[edge.getFromIndex()];
                    }
                }
            }
        }
    }

    void sort(Network network, Node<?> node, int depth) {
        if (layers.size() <= depth) layers.add(new ArrayList<>());

        // If this node is in an earlier layer, move it to this one to ensure its dependencies are computed first
        int oldLayer = getNodeLayer(node);
        if (oldLayer != -1 && oldLayer < depth) layers.get(oldLayer).remove(node);

        layers.get(depth).add(node);
        Edge[] edges = network.getEdgesFrom(node).toArray(Edge[]::new);
        connections.put(node.getUUID(), edges);

        for (Edge edge: edges) {
            Node<?> toNode = network.getNode(edge.getToNode());
            sort(network, toNode, depth + 1);
        }
    }

    /**
     * If the node is contained in the layer list, returns the outer index of the list it's in. Otherwise, returns -1
     */
    public int getNodeLayer(Node<?> node) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).contains(node)) return i;
        }
        return -1;
    }
}
