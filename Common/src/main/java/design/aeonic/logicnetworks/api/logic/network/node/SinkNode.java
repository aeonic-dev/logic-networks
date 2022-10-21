package design.aeonic.logicnetworks.api.logic.network.node;

import design.aeonic.logicnetworks.api.logic.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import it.unimi.dsi.fastutil.ints.IntArrays;

public interface SinkNode<T extends SinkNode<T>> extends Node<T> {

    /**
     * Accepts and deals with signals matching the descriptor returned from {@link #getInputSlots()}.
     */
    void accept(NetworkController controller, Object... inputs);

    @Override
    default int[] getOutputPositions() {
        return IntArrays.EMPTY_ARRAY;
    }

    @Override
    default SignalType<?>[] getOutputSlots() {
        return new SignalType[0];
    }
}
