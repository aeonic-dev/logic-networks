package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.client.screen.input.widgets.CheckboxInputWidget;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class ListAddNode<T> extends SimpleOperatorNode<ListAddNode<T>> {
    private final SignalType<?>[] appendInputs;

    protected CheckboxInputWidget appendCheckbox;
    protected boolean append = false;

    public ListAddNode(NodeType<ListAddNode<T>> nodeType, Component name, SignalType<?> signalType, UUID uuid, int x, int y) {
        super(nodeType, name, SignalType.arrayOf(BuiltinSignalTypes.LIST, signalType, BuiltinSignalTypes.INTEGER), BuiltinSignalTypes.LIST.arrayOf(), uuid, x, y);
        appendInputs = SignalType.arrayOf(BuiltinSignalTypes.LIST, signalType);
    }

    protected abstract boolean isOfType(ListTag tag);

    protected abstract void insert(ListTag tag, T value, int index);

    @Override
    public SignalType<?>[] getInputSlots() {
        return (appendCheckbox == null ? append : appendCheckbox.getValue()) ? appendInputs : super.getInputSlots();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] evaluate(Object[] inputs) {
        ListTag tag = ((ListTag) inputs[0]).copy();
        if (isOfType(tag)) {
            if (append) {
                insert(tag, (T) inputs[1], tag.size());
            } else if (inputs[2] != null) {
                insert(tag, (T) inputs[1], (Integer) inputs[2]);
            }
        }
        return new Object[]{null};
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(appendCheckbox = new CheckboxInputWidget(6, 15, append) {
            @Override
            public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
                return List.of(Translations.Generic.APPEND);
            }
        });
    }

    @Override
    public int getHeight() {
        return 25;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return index == 2 ? Translations.Generic.INDEX : super.getInputSocketName(index);
    }

    @Override
    public boolean validate(Object[] inputs) {
        // Third input (index) can be null to append to the end of the list
        return (inputs[0] != null && inputs[1] != null);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (appendCheckbox != null) append = appendCheckbox.getValue();
        tag.putBoolean("Append", append);
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        append = tag.getBoolean("Append");
        if (appendCheckbox != null) appendCheckbox.setValue(append);
    }

    public static class Compound extends ListAddNode<CompoundTag> {
        public Compound(NodeType<ListAddNode<CompoundTag>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_COMPOUND, BuiltinSignalTypes.NBT, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_COMPOUND;
        }

        @Override
        protected void insert(ListTag tag, CompoundTag value, int index) {
            tag.add(index, value);
        }
    }

    public static class Str extends ListAddNode<String> {
        public Str(NodeType<ListAddNode<String>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_STRING, BuiltinSignalTypes.STRING, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_STRING;
        }

        @Override
        protected void insert(ListTag tag, String value, int index) {
            tag.add(index, StringTag.valueOf(value));
        }
    }

    public static class Int extends ListAddNode<Integer> {
        public Int(NodeType<ListAddNode<Integer>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_INTEGER, BuiltinSignalTypes.INTEGER, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_INT || tag.getElementType() == Tag.TAG_LONG;
        }

        @Override
        protected void insert(ListTag tag, Integer value, int index) {
            if (tag.getElementType() == Tag.TAG_INT) tag.add(index, IntTag.valueOf(value));
            else tag.add(index, LongTag.valueOf(value));
        }
    }

    public static class Lng extends ListAddNode<Long> {
        public Lng(NodeType<ListAddNode<Long>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_LONG, BuiltinSignalTypes.LONG, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_LONG;
        }

        @Override
        protected void insert(ListTag tag, Long value, int index) {
            tag.add(index, LongTag.valueOf(value));
        }
    }

    public static class Bool extends ListAddNode<Boolean> {
        public Bool(NodeType<ListAddNode<Boolean>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_BOOLEAN, BuiltinSignalTypes.BOOLEAN, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_BYTE;
        }

        @Override
        protected void insert(ListTag tag, Boolean value, int index) {
            tag.add(index, ByteTag.valueOf(value));
        }
    }
}
