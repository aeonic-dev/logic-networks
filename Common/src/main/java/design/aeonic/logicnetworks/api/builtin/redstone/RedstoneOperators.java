package design.aeonic.logicnetworks.api.builtin.redstone;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.logic.operators.InputOperator;
import design.aeonic.logicnetworks.api.logic.operators.OutputOperator;
import design.aeonic.logicnetworks.api.registry.OperatorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.lang.model.type.NullType;

public final class RedstoneOperators {
    public static final Operator<NullType> ANALOG_OUTPUT = new OutputOperator<>(BuiltinSignalTypes.ANALOG);

    public static final Operator<Integer> ANALOG_INPUT = new InputOperator<>(BuiltinSignalTypes.ANALOG);

    public static final Operator<Integer> ANALOG_INVERT = Operator.builder(BuiltinSignalTypes.ANALOG)
            .inputs(BuiltinSignalTypes.ANALOG)
            .process((inputs, options) -> Mth.clamp(15 - BuiltinSignalTypes.ANALOG.cast(inputs[0]), 0, 15))
            .build();

    public static final Operator<Integer> ANALOG_ADD = Operator.builder(BuiltinSignalTypes.ANALOG)
            .inputs(BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.ANALOG)
            .process((inputs, options) -> Mth.clamp(BuiltinSignalTypes.ANALOG.cast(inputs[0]) + BuiltinSignalTypes.ANALOG.cast(inputs[1]), 0, 15))
            .build();

    public static final Operator<Integer> ANALOG_SUBTRACT = Operator.builder(BuiltinSignalTypes.ANALOG)
            .inputs(BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.ANALOG)
            .process((inputs, options) -> Mth.clamp(BuiltinSignalTypes.ANALOG.cast(inputs[0]) - BuiltinSignalTypes.ANALOG.cast(inputs[1]), 0, 15))
            .build();

    public static final Operator<Boolean> BOOLEAN_INPUT = new InputOperator<>(BuiltinSignalTypes.BOOLEAN);
    public static final Operator<NullType> BOOLEAN_OUTPUT = new OutputOperator<>(BuiltinSignalTypes.BOOLEAN);

    public static final Operator<Boolean> BOOLEAN_INVERT = Operator.builder(BuiltinSignalTypes.BOOLEAN)
            .inputs(BuiltinSignalTypes.BOOLEAN)
            .process((inputs, options) -> !BuiltinSignalTypes.BOOLEAN.cast(inputs[0]))
            .build();

    public static void register() {
        // Analog
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/analog_input"), ANALOG_INPUT);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/analog_output"), ANALOG_OUTPUT);

        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/analog_invert"), ANALOG_INVERT);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/analog_add"), ANALOG_ADD);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/analog_subtract"), ANALOG_SUBTRACT);

        // Boolean
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/boolean_input"), BOOLEAN_INPUT);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/boolean_output"), BOOLEAN_OUTPUT);

        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/boolean_invert"), BOOLEAN_INVERT);
    }
}
