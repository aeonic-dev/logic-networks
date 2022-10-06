package design.aeonic.logicnetworks.api.logic.operators;

import design.aeonic.logicnetworks.api.logic.Option;
import design.aeonic.logicnetworks.api.logic.Signal;
import design.aeonic.logicnetworks.api.logic.SignalType;

/**
 * An operator for single input of a given type, either as a constant or from an in-world anchor.
 */
public class InputOperator<T> extends NetworkAnchorOperator<T> {
    public final SignalType<T> type;

    public InputOperator(SignalType<T> type) {
        this.type = type;
        outputTypes = new SignalType[]{type};
    }

    public Signal<T> getSignal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void process(Signal<?>[] inputs, Signal<?>[] outputs, Option<?>[] options) {

    }
}
