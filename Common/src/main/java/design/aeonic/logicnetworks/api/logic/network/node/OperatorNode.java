package design.aeonic.logicnetworks.api.logic.network.node;

public interface OperatorNode<T extends OperatorNode<T>> extends Node<T> {

    /**
     * Validates input parameters before evaluation. If this method returns false, this branch of traversal will stop.
     */
    default boolean validate(Object[] inputs) {
        // Parameters are assumed to be of the correct type, so we just need to null-check them.
        for (Object input : inputs) {
            if (input == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluates this operator, returning an array matching {@link #getOutputSlots()}. Inputs will have passed any tests in
     * {@link #validate(Object[])} prior to this method being called; by default, this ensures they are all nonnull.
     */
    Object[] evaluate(Object[] inputs);
}
