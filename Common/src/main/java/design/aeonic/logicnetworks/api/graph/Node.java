package design.aeonic.logicnetworks.api.graph;

import design.aeonic.logicnetworks.api.client.NodeRenderer;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.operators.InputOperator;
import design.aeonic.logicnetworks.api.registries.NodeRendererRegistry;
import design.aeonic.logicnetworks.api.registries.OperatorRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

/**
 * A node in a logic network; serves as a graph's instance of an {@link Operator} with I/O sockets and connections.
 */
public class Node<T, O extends Operator<T>> {
    public final Network network;
    public final UUID uuid;
    public final Operator<T> operator;
    private Socket<T> outputSocket;
    private Socket<?>[] inputSockets;
    private Option<?>[] options;
    private int x;
    private int y;

    public Node(Network network, O operator, int x, int y) {
        this(network, UUID.randomUUID(), operator, x, y, false);
    }

    public Node(Network network, UUID uuid, O operator, int x, int y, boolean skipInit) {
        this.network = network;
        this.uuid = uuid;
        this.operator = operator;

        if (!skipInit) {
            outputSocket = new Socket<>(this, 0, operator.getOutputType(), Socket.Side.OUTPUT);

            inputSockets = new Socket[operator.getInputTypes().length];
            for (int i = 0; i < inputSockets.length; i++) {
                inputSockets[i] = new Socket<>(this, i, operator.getInputTypes()[i], Socket.Side.INPUT);
            }

            options = new Option[operator.getOptionTypes().length];
            for (int i = 0; i < options.length; i++) {
                options[i] = operator.getOptionTypes()[i].create();
            }
        }

        this.x = x;
        this.y = y;
    }

    @SuppressWarnings("unchecked")
    public NodeRenderer<T, O> getRenderer() {
        return (NodeRenderer<T, O>) (Object) NodeRendererRegistry.INSTANCE.get(operator);
    }

    /**
     * Called to recursively compute its value from branching inputs.
     * Null value can be returned at any point to cancel traversal if inputs are invalid down the line.<br><br>
     * This method is called by the network, on the node inputting to an output node. Wordy, eh?
     */
    public T traverse() {
        if (operator instanceof InputOperator<T>) return ((InputOperator<T>) operator).read(options);

        Object[] inputs = new Object[inputSockets.length];
        for (int i = 0; i < inputs.length; i++) {
            if (inputSockets[i].connectedSocket == null) return null;
            inputs[i] = inputSockets[i].connectedSocket.node.traverse();
        }

        return operator.process(inputs, options);
    }

    public Socket<?> getSocket(Socket.Side side, int index) {
        return side == Socket.Side.INPUT ? inputSockets[index] : outputSocket;
    }

    public void serialize(CompoundTag tag) {
        tag.putUUID("uuid", uuid);
        tag.putString("operator", OperatorRegistry.INSTANCE.getKey(operator).toString());
        tag.putInt("x", x);
        tag.putInt("y", y);

        CompoundTag output = new CompoundTag();
        outputSocket.serialize(output);

        ListTag inputs = new ListTag();
        for (Socket<?> socket : inputSockets) {
            CompoundTag socketTag = new CompoundTag();
            socket.serialize(socketTag);
            inputs.add(socketTag);
        }

        ListTag options = new ListTag();
        for (Option<?> option: this.options) {
            CompoundTag optionTag = new CompoundTag();
            option.serialize(optionTag);
            options.add(optionTag);
        }

        tag.put("output", output);
        tag.put("inputs", inputs);
        tag.put("options", options);
    }

    @SuppressWarnings("unchecked")
    public static <T, O extends Operator<T>> Node<T, O> deserialize(Network network, CompoundTag tag) {
        O operator = (O) OperatorRegistry.INSTANCE.get(new ResourceLocation(tag.getString("operator")));
        Node<T, O> node = new Node<>(network, UUID.fromString(tag.getString("uuid")), operator, tag.getInt("x"), tag.getInt("y"), true);

        CompoundTag output = tag.getCompound("output");
        node.outputSocket = new Socket<>(node, 0, operator.getOutputType(), Socket.Side.OUTPUT);

        ListTag inputs = tag.getList("inputs", Tag.TAG_COMPOUND);
        Socket<?>[] inputSockets = new Socket[inputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            inputSockets[i] = Socket.deserialize(node, i, operator.getInputTypes()[i], Socket.Side.INPUT, inputs.getCompound(i));
        }

        ListTag options = tag.getList("options", Tag.TAG_COMPOUND);
        Option<?>[] optionsArray = new Option[options.size()];
        for (int i = 0; i < inputs.size(); i++) {
            optionsArray[i] = operator.getOptionTypes()[i].create();
            optionsArray[i].deserialize(options.getCompound(i));
        }

        node.inputSockets = inputSockets;
        node.options = optionsArray;

        return node;
    }

    public void ready() {
        outputSocket.ready(network);
        for (Socket<?> socket : inputSockets) {
            socket.ready(network);
        }
    }

    public Socket<T> getOutputSocket() {
        return outputSocket;
    }

    public Socket<?>[] getInputSockets() {
        return inputSockets;
    }

    public Option<?>[] getOptions() {
        return options;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }
}
