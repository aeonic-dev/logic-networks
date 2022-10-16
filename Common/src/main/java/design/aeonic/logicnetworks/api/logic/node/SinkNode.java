package design.aeonic.logicnetworks.api.logic.node;

import design.aeonic.logicnetworks.api.logic.SignalType;

public interface SinkNode<T extends SinkNode<T>> extends Node<T> {

    /**
     * Accepts and deals with signals matching the descriptor returned from {@link #getInputSlots()}.
     */
    void accept(Object... inputs);

    @Override
    default SignalType<?>[] getOutputSlots() {
        return null;
    }
}
