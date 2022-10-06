package design.aeonic.logicnetworks.api.graph;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.Option;
import design.aeonic.logicnetworks.api.logic.Signal;
import design.aeonic.logicnetworks.api.logic.operators.InputOperator;
import design.aeonic.logicnetworks.api.registry.OperatorRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

/**
 * A node in a logic network; serves as a visual representation of an {@link Operator}.
 */
public class Node<T extends Operator> {
    public final Network network;
    public final UUID uuid;
    public final Operator operator;
    private Socket<?>[] inputSockets;
    private Socket<?>[] outputSockets;
    private Option<?>[] options;
    private int x;
    private int y;

    public Node(Network network, T operator, int x, int y) {
        this(network, UUID.randomUUID(), operator, x, y, false);
    }

    public Node(Network network, UUID uuid, T operator, int x, int y, boolean skipInit) {
        this.network = network;
        this.uuid = uuid;
        this.operator = operator;

        if (!skipInit) {
            inputSockets = new Socket[operator.getInputTypes().length];
            for (int i = 0; i < inputSockets.length; i++) {
                inputSockets[i] = new Socket<>(this, i, operator.getInputTypes()[i], Socket.Side.INPUT);
            }

            outputSockets = new Socket[operator.getOutputTypes().length];
            for (int i = 0; i < outputSockets.length; i++) {
                outputSockets[i] = new Socket<>(this, i, operator.getOutputTypes()[i], Socket.Side.OUTPUT);
            }

            options = new Option[operator.getOptionTypes().length];
            for (int i = 0; i < options.length; i++) {
                options[i] = new Option<>(operator.getOptionTypes()[i]);
            }
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Called initially on an output node to start the traversal backwards of the network.
     * Returns null at any node if its inputs are unset.
     */
    public Signal<?>[] traverse() {
        // FIXME: This makes a whooooole lotta objects
        // End of traversal. We did it reddit
        if (operator instanceof InputOperator) return new Signal[]{((InputOperator<?>) operator).getSignal()};

        // Traverse backwards
        var inputSignals = new Signal[outputSockets.length];
        for (Socket<?> socket: inputSockets) {
            if (socket.getConnectedSocket() == null) return null;
            Signal<?>[] signals = socket.getConnectedSocket().node.traverse();
            if (signals == null) return null;
            inputSignals[socket.getConnectedSocket().index] = signals[socket.index];
        }

        var outputSignals = new Signal[outputSockets.length];
        operator.process(inputSignals, outputSignals, options);
        return outputSignals;
    }

    public Socket<?> getSocket(Socket.Side side, int index) {
        return (side == Socket.Side.INPUT ? inputSockets : outputSockets)[index];
    }

    public void serialize(CompoundTag tag) {
        tag.putUUID("uuid", uuid);
        tag.putString("operator", OperatorRegistry.INSTANCE.getKey(operator).toString());
        tag.putInt("x", x);
        tag.putInt("y", y);

        ListTag inputs = new ListTag();
        for (Socket<?> socket : inputSockets) {
            CompoundTag socketTag = new CompoundTag();
            socket.serialize(socketTag);
            inputs.add(socketTag);
        }

        ListTag outputs = new ListTag();
        for (Socket<?> socket : outputSockets) {
            CompoundTag socketTag = new CompoundTag();
            socket.serialize(socketTag);
            outputs.add(socketTag);
        }

        ListTag options = new ListTag();
        for (Option<?> option: this.options) {
            CompoundTag optionTag = new CompoundTag();
            option.serialize(optionTag);
            options.add(optionTag);
        }

        tag.put("inputs", inputs);
        tag.put("outputs", outputs);
        tag.put("options", options);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Operator> Node<T> deserialize(Network network, CompoundTag tag) {
        T operator = (T) OperatorRegistry.INSTANCE.get(new ResourceLocation(tag.getString("operator")));
        Node<T> node = new Node<>(network, UUID.fromString(tag.getString("uuid")), operator, tag.getInt("x"), tag.getInt("y"), true);

        ListTag inputs = tag.getList("inputs", Tag.TAG_COMPOUND);
        Socket<?>[] inputSockets = new Socket[inputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            inputSockets[i] = Socket.deserialize(node, i, operator.getInputTypes()[i], Socket.Side.INPUT, inputs.getCompound(i));
        }

        ListTag outputs = tag.getList("outputs", Tag.TAG_COMPOUND);
        Socket<?>[] outputSockets = new Socket[outputs.size()];
        for (int i = 0; i < outputs.size(); i++) {
            outputSockets[i] = Socket.deserialize(node, i, operator.getInputTypes()[i], Socket.Side.OUTPUT, outputs.getCompound(i));
        }

        ListTag options = tag.getList("options", Tag.TAG_COMPOUND);
        Option<?>[] optionsArray = new Option[options.size()];
        for (int i = 0; i < inputs.size(); i++) {
            optionsArray[i] = Option.deserialize(operator.getOptionTypes()[i], options.getCompound(i));
        }

        node.inputSockets = inputSockets;
        node.outputSockets = outputSockets;

        return node;
    }

    public void ready() {
        for (Socket<?> socket : inputSockets) {
            socket.ready(network);
        }
        for (Socket<?> socket : outputSockets) {
            socket.ready(network);
        }
    }

    /**
     * Draws the node, excluding sockets.
     */
    public void draw(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        // TODO: Draw the node, excluding sockets
    }

    public Socket<?>[] getInputSockets() {
        return inputSockets;
    }

    public Socket<?>[] getOutputSockets() {
        return outputSockets;
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
