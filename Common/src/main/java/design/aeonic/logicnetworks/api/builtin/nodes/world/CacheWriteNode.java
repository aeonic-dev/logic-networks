package design.aeonic.logicnetworks.api.builtin.nodes.world;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.block.NetworkCache;
import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.CacheWritableSignalType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSinkNode;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class CacheWriteNode extends AnchorSinkNode<CacheWriteNode> {
    private static CacheWritableSignalType<?>[] inputSlots = null;
    private NetworkCache cache = null;

    public CacheWriteNode(NodeType<CacheWriteNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
        if (inputSlots == null) inputSlots = CommonRegistries.SIGNAL_TYPES.getValues().stream().filter(
                CacheWritableSignalType.class::isInstance).toArray(CacheWritableSignalType[]::new);
    }

    @Override
    public int getHeight() {
        return 45;
    }

    @Override
    public boolean validate(NetworkController controller, Object... inputs) {
        // Only accept input if at least one value is nonnull
        for (Object object : inputs) {
            if (object != null) return true;
        }
        return false;
    }

    @Override
    public int findInputSlot(SignalType<?> type) {
        for (int i = 0; i < inputSlots.length; i++) {
            if (inputSlots[i] == type) return i;
        }
        return -1;
    }

    @Override
    public Component getName() {
        return Translations.Nodes.CACHE_WRITE;
    }

    @Override
    public void accept(NetworkController controller, Object... inputs) {
        super.accept(controller, inputs);
        cache = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <S> void writeSingle(NetworkAnchor anchor, Direction side, SignalType<S> type, Object value) {
        if (cache == null && anchor.getAnchorLevel().getBlockEntity(anchor.getAnchorPos().relative(side)) instanceof NetworkCache networkCache) cache = networkCache;
        if (cache != null) cache.writeValue(type, (S) value);
    }

    @Override
    public SignalType<?>[] getInputSlots() {
        return inputSlots;
    }
}
