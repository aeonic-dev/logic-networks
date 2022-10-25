package design.aeonic.logicnetworks.api.builtin.nodes.nbt;

import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.SimpleOperatorNode;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class NBTGetNode<T> extends SimpleOperatorNode<NBTGetNode<T>> {
    public NBTGetNode(NodeType<NBTGetNode<T>> nodeType, SignalType<T> signalType, Component name, UUID uuid, int x, int y) {
        super(nodeType, name, SignalType.arrayOf(BuiltinSignalTypes.NBT, BuiltinSignalTypes.STRING), signalType.arrayOf(), uuid, x, y);
    }

    protected abstract boolean contains(CompoundTag tag, String key);

    protected abstract T getNBTValue(CompoundTag tag, String key);

    protected abstract void writeNBTValue(CompoundTag tag, String key, T value);

    @Override
    public int getHeight() {
        return 19;
    }

    @Nullable
    @Override
    protected Component getInputSocketName(int index) {
        return index == 1 ? Translations.Generic.KEY : super.getInputSocketName(index);
    }

    @Override
    public Object[] evaluate(Object[] inputs) {
        return new Object[]{(contains((CompoundTag) inputs[0], (String) inputs[1])) ? getNBTValue((CompoundTag) inputs[0], (String) inputs[1]) : null};
    }

    public static class Compound extends NBTGetNode<CompoundTag> {
        public Compound(NodeType<NBTGetNode<CompoundTag>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.NBT, Translations.Nodes.NBT_GET_COMPOUND, uuid, x, y);
        }

        @Override
        protected boolean contains(CompoundTag tag, String key) {
            return tag.contains(key, Tag.TAG_COMPOUND);
        }

        @Override
        protected CompoundTag getNBTValue(CompoundTag tag, String key) {
            return tag.getCompound(key);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, CompoundTag value) {
            tag.put(key, value);
        }
    }

    public static class Str extends NBTGetNode<String> {
        public Str(NodeType<NBTGetNode<String>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.STRING, Translations.Nodes.NBT_GET_STRING, uuid, x, y);
        }

        @Override
        protected boolean contains(CompoundTag tag, String key) {
            return tag.contains(key, Tag.TAG_STRING);
        }

        @Override
        protected String getNBTValue(CompoundTag tag, String key) {
            return tag.getString(key);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, String value) {
            tag.putString(key, value);
        }
    }

    public static class Int extends NBTGetNode<Integer> {
        public Int(NodeType<NBTGetNode<Integer>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.INTEGER, Translations.Nodes.NBT_GET_INTEGER, uuid, x, y);
        }

        @Override
        protected boolean contains(CompoundTag tag, String key) {
            return tag.contains(key, Tag.TAG_INT) || tag.contains(key, Tag.TAG_SHORT);
        }

        @Override
        protected Integer getNBTValue(CompoundTag tag, String key) {
            return tag.getInt(key);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Integer value) {
            tag.putInt(key, value);
        }
    }

    public static class Lng extends NBTGetNode<Long> {
        public Lng(NodeType<NBTGetNode<Long>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.LONG, Translations.Nodes.NBT_GET_LONG, uuid, x, y);
        }

        @Override
        protected boolean contains(CompoundTag tag, String key) {
            return tag.contains(key, Tag.TAG_LONG);
        }

        @Override
        protected Long getNBTValue(CompoundTag tag, String key) {
            return tag.getLong(key);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Long value) {
            tag.putLong(key, value);
        }
    }

    public static class Bool extends NBTGetNode<Boolean> {
        public Bool(NodeType<NBTGetNode<Boolean>> nodeType, UUID uuid, int x, int y) {
            super(nodeType, BuiltinSignalTypes.BOOLEAN, Translations.Nodes.NBT_GET_BOOLEAN, uuid, x, y);
        }

        @Override
        protected boolean contains(CompoundTag tag, String key) {
            return tag.contains(key, Tag.TAG_BYTE);
        }

        @Override
        protected Boolean getNBTValue(CompoundTag tag, String key) {
            return tag.getBoolean(key);
        }

        @Override
        protected void writeNBTValue(CompoundTag tag, String key, Boolean value) {
            tag.putBoolean(key, value);
        }
    }
}
