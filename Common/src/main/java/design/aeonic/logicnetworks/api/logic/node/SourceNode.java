package design.aeonic.logicnetworks.api.logic.node;

import design.aeonic.logicnetworks.api.logic.SignalType;

public interface SourceNode<T extends SourceNode<T>> extends Node<T> {

    /**
     * Reads the signals of this source node, matching the descriptor returned from {@link #getOutputSlots()}.
     */
    Object[] get();

    @Override
    default SignalType<?>[] getInputSlots() {
        return null;
    }
}
