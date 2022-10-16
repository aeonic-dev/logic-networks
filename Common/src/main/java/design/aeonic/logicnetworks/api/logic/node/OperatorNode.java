package design.aeonic.logicnetworks.api.logic.node;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.SignalType;

public interface OperatorNode<T extends OperatorNode<T>> extends Node<T> {

    /**
     * Gets the operation this fromNode performs.
     */
    Operator getOperator();

    @Override
    default SignalType<?>[] getOutputSlots() {
        return getOperator().getOutputs();
    }

    @Override
    default SignalType<?>[] getInputSlots() {
        return getOperator().getInputs();
    }
}
