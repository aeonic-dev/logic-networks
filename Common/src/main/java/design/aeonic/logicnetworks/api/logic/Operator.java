package design.aeonic.logicnetworks.api.logic;

/**
 * An Operator defines an operation on input signals to compute a single output.
 * It also defines extra data that can be used to configure the operation within a graphical node.<br><br>
 * In general, node definitions are generated automatically from Operators.
 */
public abstract class Operator<T> {
    protected SignalType<T> outputType;
    protected SignalType<?>[] inputTypes;
    protected OptionType<?>[] optionTypes = new OptionType<?>[0];

    /**
     * Runs this operator's process on given signals. The passed signal arrays will match the defined signal type arrays
     * of this operator, and the options array will match the option type array. Input signals will be nonnull.
     * @param inputs the input signals
     * @param outputs the output signals
     * @param options the option values
     */
    public abstract T process(Object[] inputs, Option<?>[] options);

    public SignalType<T> getOutputType() {
        return outputType;
    }

    public SignalType<?>[] getInputTypes() {
        return inputTypes;
    }

    public OptionType<?>[] getOptionTypes() {
        return optionTypes;
    }

    public static <T> Builder<T> builder(SignalType<T> outputType) {
        return new Builder<>(outputType);
    }

    public static class Builder<T> {
        private Built<T> operator = new Built<>();

        private Builder(SignalType<T> outputType) {
            operator.outputType = outputType;
        }

        public Builder<T> inputs(SignalType<?>... types) {
            operator.inputTypes = types;
            return this;
        }

        public Builder<T> options(OptionType<?>... types) {
            operator.optionTypes = types;
            return this;
        }

        public Builder<T> process(OperatorProcess<T> process) {
            operator.process = process;
            return this;
        }

        public Operator<T> build() {
            if (operator.inputTypes == null) operator.inputTypes = new SignalType<?>[0];
            if (operator.optionTypes == null) operator.optionTypes = new OptionType<?>[0];
            if (operator.process == null) throw new IllegalStateException("Operator process must be defined!");
            return operator;
        }
    }

    private static class Built<T> extends Operator<T> {
        private OperatorProcess<T> process;

        @Override
        public T process(Object[] inputs, Option<?>[] options) {
            return process.execute(inputs, options);
        }
    }

    @FunctionalInterface
    public interface OperatorProcess<T> {
        T execute(Object[] inputs, Option<?>[] options);
    }
}