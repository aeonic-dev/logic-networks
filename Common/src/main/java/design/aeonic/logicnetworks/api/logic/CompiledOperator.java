package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.graph.Node;

/**
 * A "compiled" operator that bundles option values needed for execution without a {@link Node} object.
 */
public class CompiledOperator {
    public final Operator operator;
    private final Option<?>[] options;

    public CompiledOperator(Operator operator, Option<?>... options) {
        this.operator = operator;
        this.options = options;
    }

    public void process(Signal<?>[] inputs, Signal<?>[] outputs) {
        operator.process(inputs, outputs, options);
    }
}
