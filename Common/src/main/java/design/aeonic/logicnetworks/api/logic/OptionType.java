package design.aeonic.logicnetworks.api.logic;

import design.aeonic.logicnetworks.api.graph.Option;

import java.util.function.Function;

/**
 * Describes a type of setting used as additional data for an operator.
 * Instances (actual options within a node) are created using {@link Option}.
 */
public abstract class OptionType<T, O extends Option<T>> {
    public final String label;
    public final T defaultValue;

    public OptionType(String label, T defaultValue) {
        this.label = label;
        this.defaultValue = defaultValue;
    }

    public abstract O create();

    public static <T, O extends Option<T>> OptionType<T, O> of(String label, T defaultValue, Function<OptionType<T, O>, O> factory) {
        return new OptionType<>(label, defaultValue) {
            @Override
            public O create() {
                return factory.apply(this);
            }
        };
    }
}
