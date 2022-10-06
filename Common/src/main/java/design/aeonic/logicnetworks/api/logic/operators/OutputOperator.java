package design.aeonic.logicnetworks.api.logic.operators;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.graph.Option;
import design.aeonic.logicnetworks.api.logic.SignalType;

import javax.lang.model.type.NullType;

/**
 * An operator for single input of a given type, either as a constant or from an in-world anchor.
 * This class's type parameter is, confusingly, the type of the input signal.
 */
public class OutputOperator<I> extends Operator<NullType> {
    public final SignalType<I> type;

    public OutputOperator(SignalType<I> type) {
        this.type = type;
        inputTypes = new SignalType[]{type};
    }

    public void write(I value, Option<?>[] options) {
        throw new UnsupportedOperationException("Cannot write to an output operator");
    }

    @Override
    public NullType process(Object[] inputs, Option<?>[] options) {
        return null;
    }
}
