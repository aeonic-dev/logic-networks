package design.aeonic.logicnetworks.api.logic.operators;

import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.Signal;

/**
 * An operator that serves as an in-world anchor for a network; instead of being processed by the network, it either
 * reads or writes in-world data.
 * @param <T>
 */
public abstract class NetworkAnchorOperator<T> extends Operator {
    public abstract Signal<T> getSignal();
}
