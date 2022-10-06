package design.aeonic.logicnetworks.api.logic.operators;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.graph.Option;
import design.aeonic.logicnetworks.api.logic.SignalType;

/**
 * An operator for single output of a given type, either as a constant or from an in-world anchor.
 * This class's type parameter is, confusingly, the type of the output signal.
 */
public abstract class InputOperator<O> extends Operator<O> {
    public final SignalType<O> type;

    public InputOperator(SignalType<O> type) {
        outputType = this.type = type;
    }

    public abstract O read(Option<?>[] options);

    @Override
    public final O process(Object[] inputs, Option<?>[] options) {
        return read(options);
    }
}
