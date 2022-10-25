package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class NBTPutNode<T> extends SimpleOperatorNode<NBTPutNode<T>> {
    public NBTPutNode(NodeType<NBTPutNode<T>> nodeType, SignalType<T> signalType, Component name, UUID uuid, int x, int y) {
        super(nodeType, name, SignalType.arrayOf(BuiltinSignalTypes.NBT, BuiltinSignalTypes.STRING, signalType), BuiltinSignalTypes.NBT.arrayOf(), uuid, x, y);
    }

    protected abstract void writeNBTValue(CompoundTag tag, String key, T value);

    @Override
    public int getHeight() {
        return 25;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return switch(index) {
            case 1 -> Translations.Generic.KEY;
            case 2 -> Translations.Generic.VALUE;
            default -> super.getInputSocketName(index);
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] evaluate(Object[] inputs) {
        CompoundTag tag = ((CompoundTag) inputs[0]).copy();
        writeNBTValue(tag, (String) inputs[1], (T) inputs[2]);
        return new Object[]{tag};
    }

    public static class Compound extends NBTPutNode<CompoundTag> {
        public Compound(NodeType<NBTPutNode<CompoundTag>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.NBT, Translations.Nodes.NBT_PUT_COMPOUND, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, CompoundTag value) {
            tag.put(key, value);
        }
    }

    public static class Str extends NBTPutNode<String> {
        public Str(NodeType<NBTPutNode<String>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.STRING, Translations.Nodes.NBT_PUT_STRING, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, String value) {
            tag.putString(key, value);
        }
    }

    public static class Int extends NBTPutNode<Integer> {
        public Int(NodeType<NBTPutNode<Integer>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.INTEGER, Translations.Nodes.NBT_PUT_INTEGER, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Integer value) {
            tag.putInt(key, value);
        }
    }

    public static class Lng extends NBTPutNode<Long> {
        public Lng(NodeType<NBTPutNode<Long>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.LONG, Translations.Nodes.NBT_PUT_LONG, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Long value) {
            tag.putLong(key, value);
        }
    }

    public static class Bool extends NBTPutNode<Boolean> {
        public Bool(NodeType<NBTPutNode<Boolean>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.BOOLEAN, Translations.Nodes.NBT_PUT_BOOLEAN, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Boolean value) {
            tag.putBoolean(key, value);
        }
    }

    public static class List extends NBTPutNode<ListTag> {
        public List(NodeType<NBTPutNode<ListTag>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.LIST, Translations.Nodes.NBT_PUT_LIST, uuid, x, y);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, ListTag value) {
            tag.put(key, value);
        }
    }
}
