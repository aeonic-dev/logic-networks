package design.aeonic.logicnetworks.api.logic.network.node;

import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import it.unimi.dsi.fastutil.ints.IntArrays;
import org.apache.commons.lang3.ArrayUtils;

public interface SinkNode<T extends SinkNode<T>> extends Node<T> {

    /**
     * Validates input signals before writing.
     */
    default boolean validate(NetworkController controller, Object... inputs) {
        return !ArrayUtils.contains(inputs, null);
    }

    /**
     * Accepts and deals with signals matching the descriptor returned from {@link #getInputSlots()}. Input is
     * guaranteed to have returned true from {@link #validate(NetworkController, Object...)}, and will have been
     * converted to the input signal types if necessary.
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
