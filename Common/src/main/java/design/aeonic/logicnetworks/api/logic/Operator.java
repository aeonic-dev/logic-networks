package design.aeonic.logicnetworks.api.logic;

public interface Operator {

    /**
     * A descriptor of this operator's inputs.
     */
    SignalType<?>[] getInputs();

    /**
     * A descriptor of this operator's outputs.
     */
    SignalType<?>[] getOutputs();

    /**
     * Validates input parameters before evaluation. If this method returns false, this branch of traversal will stop.
     */
    default boolean validate(Object... inputs) {
        // Parameters are assumed to be of the correct type, so we just need to null-check them.
        for (Object input : inputs) {
            if (input == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluates this operator, returning an array matching {@link #getOutputs()}. Inputs will have passed any tests in
     * {@link #validate(Object...)} prior to this method being called; by default, this ensures they are all nonnull.
     */
    Object[] evaluate(Object... inputs);

    static Builder builder() {
        return new Builder();
    }

    final class Builder {
        private SignalType<?>[] inputs;
        private SignalType<?>[] outputs;
        private Evaluator evaluator;

        private Builder() {}

        public Builder inputs(SignalType<?>... inputs) {
            this.inputs = inputs;
            return this;
        }

        public Builder outputs(SignalType<?>... outputs) {
            this.outputs = outputs;
            return this;
        }

        public Builder process(Evaluator evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        public Operator build() {
            if (inputs == null) inputs = new SignalType[0];
            if (outputs == null) outputs = new SignalType[0];
            if (evaluator == null) evaluator = (inputs) -> new Object[0];

            return new Operator() {
                @Override
                public SignalType<?>[] getInputs() {
                    return inputs;
                }

                @Override
                public SignalType<?>[] getOutputs() {
                    return outputs;
                }

                @Override
                public Object[] evaluate(Object... inputs) {
                    return evaluator.evaluate(inputs);
                }
            };
        }
    }

    @FunctionalInterface
    interface Evaluator {
        Object[] evaluate(Object... inputs);
    }
}
