package design.aeonic.logicnetworks.api.logic.network.node;

import design.aeonic.logicnetworks.api.logic.network.SignalType;
import it.unimi.dsi.fastutil.ints.IntArrays;

public interface SourceNode<T extends SourceNode<T>> extends Node<T> {

    /**
     * Reads the signals of this source node, matching the descriptor returned from {@link #getOutputSlots()}.
     */
    Object[] get();

    @Override
    default int[] getInputPositions() {
        return IntArrays.EMPTY_ARRAY;
    }

    @Override
    default SignalType<?>[] getInputSlots() {
        return null;
    }
}
