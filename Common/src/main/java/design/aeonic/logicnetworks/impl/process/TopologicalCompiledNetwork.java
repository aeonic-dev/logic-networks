package design.aeonic.logicnetworks.impl.process;

import design.aeonic.logicnetworks.api.logic.Edge;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.logic.node.Node;
import design.aeonic.logicnetworks.api.logic.node.OperatorNode;
import design.aeonic.logicnetworks.api.logic.node.SinkNode;
import design.aeonic.logicnetworks.api.logic.node.SourceNode;
import design.aeonic.logicnetworks.api.logic.CompiledNetwork;
import design.aeonic.logicnetworks.api.logic.Operator;

import java.util.*;

public class TopologicalCompiledNetwork implements CompiledNetwork {
    private final List<List<Node>> layers = new ArrayList<>();
    private final Map<UUID, Object[]> inputBuffer = new HashMap<>();
    private final Map<UUID, Edge[]> connections = new HashMap<>();

    public TopologicalCompiledNetwork(Network network) {
        network.getSourceNodes().forEach(node -> sort(network, node, 0));
    }

    @Override
    public void tick() {
        for (List<Node> layer : layers) {
            for (Node node : layer) {
                if (node instanceof SinkNode sink) {
                    Object[] input = inputBuffer.get(sink.getUuid());
                    // Don't write to the sink if any input is null
                    for (Object obj : input) {
                        if (obj == null) return;
                    }
                    sink.accept(input);
                } else if (node instanceof SourceNode source){
                    Object[] output = source.get();
                    for (Edge edge: connections.get(node.getUuid())) {
                        inputBuffer.get(edge.getToNode())[edge.getToIndex()] =  output[edge.getFromIndex()];
                    }
                } else if (node instanceof OperatorNode operator) {
                    Object[] input = inputBuffer.get(operator.getUuid());
                    if (input == null) return;

                    // Validate operator inputs before continuing; should propogate the rest of the changes
                    Operator op = operator.getOperator();
                    if (!op.validate(input)) return;

                    Object[] output = op.evaluate(input);
                    for (Edge edge: connections.get(node.getUuid())) {
                        inputBuffer.get(edge.getToNode())[edge.getToIndex()] =  output[edge.getFromIndex()];
                    }
                }
            }
        }
    }

    void sort(Network network, Node node, int depth) {
        if (layers.size() <= depth) layers.add(new ArrayList<>());

        // If this node is in an earlier layer, move it to this one to ensure its dependencies are computed first
        int oldLayer = getNodeLayer(node);
        if (oldLayer != -1 && oldLayer < depth) layers.get(oldLayer).remove(node);

        layers.get(depth).add(node);
        Edge[] edges = network.getEdgesFrom(node).toArray(Edge[]::new);
        connections.put(node.getUuid(), edges);

        for (Edge edge: edges) {
            Node toNode = network.getNode(edge.getToNode());
            sort(network, toNode, depth + 1);
        }
    }

    /**
     * If the node is contained in the layer list, returns the outer index of the list it's in. Otherwise, returns -1
     */
    public int getNodeLayer(Node node) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).contains(node)) return i;
        }
        return -1;
    }
}
