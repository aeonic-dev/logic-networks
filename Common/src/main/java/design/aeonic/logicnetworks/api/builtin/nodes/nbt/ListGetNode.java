package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class ListGetNode<T> extends SimpleOperatorNode<ListGetNode<T>> {
    public ListGetNode(NodeType<ListGetNode<T>> nodeType, Component name, SignalType<?> signalType, UUID uuid, int x, int y) {
        super(nodeType, name, SignalType.arrayOf(BuiltinSignalTypes.LIST, BuiltinSignalTypes.INTEGER), signalType.arrayOf(), uuid, x, y);
    }

    protected abstract boolean isOfType(ListTag tag);

    protected abstract T get(ListTag tag, int index);

    @Override
    public Object[] evaluate(Object[] inputs) {
        ListTag tag = ((ListTag) inputs[0]).copy();
        if (isOfType(tag) && tag.size() > (Integer) inputs[1]) {
            return new Object[]{get(tag, (Integer) inputs[1])};
        }
        return new Object[]{null};
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

    public static class Compound extends ListGetNode<CompoundTag> {
        public Compound(NodeType<ListGetNode<CompoundTag>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_COMPOUND, BuiltinSignalTypes.NBT, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_COMPOUND;
        }

        @Override
        protected CompoundTag get(ListTag tag, int index) {
            return tag.getCompound(index);
        }
    }

    public static class Str extends ListGetNode<String> {
        public Str(NodeType<ListGetNode<String>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_STRING, BuiltinSignalTypes.STRING, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_STRING;
        }

        @Override
        protected String get(ListTag tag, int index) {
            return tag.getString(index);
        }
    }

    public static class Int extends ListGetNode<Integer> {
        public Int(NodeType<ListGetNode<Integer>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_INTEGER, BuiltinSignalTypes.INTEGER, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_INT || tag.getElementType() == Tag.TAG_SHORT;
        }

        @Override
        protected Integer get(ListTag tag, int index) {
            return tag.getElementType() == Tag.TAG_INT ? tag.getInt(index) : (int) tag.getShort(index);
        }
    }

    public static class Lng extends ListGetNode<Long> {
        public Lng(NodeType<ListGetNode<Long>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_LONG, BuiltinSignalTypes.LONG, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_LONG || tag.getElementType() == Tag.TAG_INT || tag.getElementType() == Tag.TAG_SHORT;
        }

        @Override
        protected Long get(ListTag tag, int index) {
            return switch (tag.getElementType()) {
                case Tag.TAG_LONG -> tag.get(index) instanceof LongTag longTag ? longTag.getAsLong() : null;
                case Tag.TAG_INT -> (long) tag.getInt(index);
                case Tag.TAG_SHORT -> (long) tag.getShort(index);
                default -> null;
            };
        }
    }

    public static class Bool extends ListGetNode<Boolean> {
        public Bool(NodeType<ListGetNode<Boolean>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, Translations.Nodes.LIST_ADD_BOOLEAN, BuiltinSignalTypes.BOOLEAN, uuid, x, y);
        }

        @Override
        protected boolean isOfType(ListTag tag) {
            return tag.getElementType() == Tag.TAG_BYTE;
        }

        @Override
        protected Boolean get(ListTag tag, int index) {
            return tag.get(index) instanceof ByteTag byteTag ? byteTag.getAsByte() != 0 : null;
        }
    }
}
