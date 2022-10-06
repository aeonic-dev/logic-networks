package design.aeonic.logicnetworks.api.builtin.redstone;

import design.aeonic.logicnetworks.api.Constants;
import design.aeonic.logicnetworks.api.logic.operators.InputOperator;
import design.aeonic.logicnetworks.api.logic.Operator;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.logic.operators.OutputOperator;
import design.aeonic.logicnetworks.api.registry.OperatorRegistry;
import net.minecraft.resources.ResourceLocation;

public final class RedstoneOperators {
    public static final Operator INPUT_ANALOG = new InputOperator<>(BuiltinSignalTypes.ANALOG);
    public static final Operator INPUT_BOOLEAN = new InputOperator<>(BuiltinSignalTypes.BOOLEAN);
    public static final Operator OUTPUT_ANALOG = new OutputOperator<>(BuiltinSignalTypes.ANALOG);
    public static final Operator OUTPUT_BOOLEAN = new OutputOperator<>(BuiltinSignalTypes.BOOLEAN);

    public static final Operator INVERT = Operator.builder()
            .inputs(BuiltinSignalTypes.ANALOG).outputs(BuiltinSignalTypes.ANALOG)
            .process((inputs, outputs, options) -> outputs[0].setAs(15 - inputs[0].getAs(BuiltinSignalTypes.ANALOG)))
            .build();

    public static final Operator ADD = Operator.builder()
            .inputs(BuiltinSignalTypes.ANALOG, BuiltinSignalTypes.ANALOG).outputs(BuiltinSignalTypes.ANALOG)
            .process((inputs, outputs, options) -> outputs[0].setAs(inputs[0].getAs(BuiltinSignalTypes.ANALOG) + inputs[1].getAs(BuiltinSignalTypes.ANALOG)))
            .build();

    public static final Operator SUBTRACT = ADD.composeFirst((inputs, outputs, options) -> inputs[1].setAs(15 - inputs[1].getAs(BuiltinSignalTypes.ANALOG)));

    public static void register() {
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/input_analog"), INPUT_ANALOG);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/input_boolean"), INPUT_BOOLEAN);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/output_analog"), OUTPUT_ANALOG);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/output_boolean"), OUTPUT_BOOLEAN);

        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/invert"), INVERT);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/add"), ADD);
        OperatorRegistry.INSTANCE.register(new ResourceLocation(Constants.MOD_ID, "redstone/subtract"), SUBTRACT);
    }
}
