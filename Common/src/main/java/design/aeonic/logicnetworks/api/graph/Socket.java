package design.aeonic.logicnetworks.api.graph;

import design.aeonic.logicnetworks.api.logic.SignalType;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Socket<T> {
    public final Node node;
    public final int index;
    public final SignalType<T> signalType;
    public final Side side;

    protected Socket<?> connectedSocket;
    // UUID and index are used to identify the connected socket before all nodes are loaded
    // The connectedSocket field is then fetched from the network when ready() is called
    private UUID connectedSocketUuid;
    private int connectedSocketIndex;

    public Socket(Node node, int index, SignalType<T> signalType, Side side) {
        this.node = node;
        this.index = index;
        this.signalType = signalType;
        this.side = side;
    }

    public boolean connect(Socket<?> connection) {
        // TODO: Not sure if this all propagates correctly
        // Ignore checks when resetting a connection
        if (connection == null) {
            if (this.connectedSocket != null) connectedSocket.connectedSocket = null;
            this.connectedSocket = null;
            return true;
        }
        // Can't connect a node to itself, or an input to input/output to output
        if (connection.node == this.node || connection.side == this.side) return false;
        if (!connection.signalType.canConnect(this.signalType)) return false;

        // Clear the connection's reference to this socket
        if (this.connectedSocket != null) {
            this.connectedSocket.connect(null);
        }
        this.connectedSocket = connection;
        connection.connectedSocket = this;
        return true;
    }

    public Socket<?> getConnectedSocket() {
        return connectedSocket;
    }

    public void serialize(CompoundTag tag) {
        if (connectedSocket != null) {
            tag.putBoolean("hasConnection", true);
            tag.putUUID("connectionUuid", connectedSocket.node.uuid);
            tag.putInt("connectionIndex", connectedSocket.index);
        } else {
            tag.putBoolean("hasConnection", false);
        }
    }

    public static <T> Socket<T> deserialize(Node node, int index, SignalType<T> signalType, Side side, CompoundTag tag) {
        Socket<T> socket = new Socket<>(node, index, signalType, side);
        if (tag.getBoolean("hasConnection")) {
            socket.connectedSocketUuid = tag.getUUID("connectionUuid");
            socket.connectedSocketIndex = tag.getInt("connectionIndex");
        }
        return socket;
    }

    @SuppressWarnings("unchecked")
    public void ready(Network network) {
        if (connectedSocketUuid != null) {
            connectedSocket = network.getNode(connectedSocketUuid).getSocket(side.flip(), connectedSocketIndex);
            connectedSocketUuid = null;
            connectedSocketIndex = -1;
        }
    }

    public enum Side {
        INPUT, OUTPUT;

        public Side flip() {
            return this == INPUT ? OUTPUT : INPUT;
        }
    }
}
