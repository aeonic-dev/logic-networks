package design.aeonic.logicnetworks.api.logic;

/**
 * An Operator defines an operation on input signals to compute any number of output signals.
 * It also defines extra data that can be used to configure the operation within a graphical node.<br><br>
 * In general, node definitions are generated automatically from Operators.
 */
public abstract class Operator {
    protected SignalType<?>[] inputTypes;
    protected SignalType<?>[] outputTypes;
    protected OptionType<?>[] optionTypes = new OptionType<?>[0];

    public CompiledOperator compile(Option<?>... options) {
        return new CompiledOperator(this, options);
    }

    /**
     * Runs this operator's process on given signals. The passed signal arrays will match the defined signal type arrays
     * of this operator, and the options array will match the option type array. Input signals will be nonnull.
     * @param inputs the input signals
     * @param outputs the output signals
     * @param options the option values
     */
    public abstract void process(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options);

    public SignalType<?>[] getInputTypes() {
        return inputTypes;
    }

    public SignalType<?>[] getOutputTypes() {
        return outputTypes;
    }

    public OptionType<?>[] getOptionTypes() {
        return optionTypes;
    }

    /**
     * Creates a composed operator that first runs firstProcess, then the outside operator.
     */
    public Operator composeFirst(OperatorProcess firstProcess) {
        return new Operator() {
            @Override
            public void process(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options) {
                firstProcess.execute(inputs, outputs, options);
                Operator.this.process(inputs, outputs, options);
            }
        };
    }

    /**
     * Creates a composed operator that first runs the outside operator, then the passed secondProcess.
     */
    public Operator compose(OperatorProcess secondProcess) {
        return new Operator() {
            @Override
            public void process(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options) {
                Operator.this.process(inputs, outputs, options);
                secondProcess.execute(inputs, outputs, options);
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Built operator = new Built();

        public Builder inputs(SignalType<?>... types) {
            operator.inputTypes = types;
            return this;
        }

        public Builder outputs(SignalType<?>... types) {
            operator.outputTypes = types;
            return this;
        }

        public Builder options(OptionType<?>... types) {
            operator.optionTypes = types;
            return this;
        }

        public Builder process(OperatorProcess process) {
            operator.process = process;
            return this;
        }

        public Operator build() {
            return operator;
        }
    }

    private static class Built extends Operator {
        private OperatorProcess process;

        @Override
        public void process(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options) {
            process.execute(inputs, outputs, options);
        }
    }

    @FunctionalInterface
    public interface OperatorProcess {
        void execute(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options);
    }
}