package design.aeonic.logicnetworks.api.builtin.nodes.world;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.block.NetworkCache;
import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.network.CacheWritableSignalType;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSourceNode;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class CacheReadNode extends AnchorSourceNode<CacheReadNode> {
    private static CacheWritableSignalType<?>[] outputSlots = null;
    private NetworkCache cache = null;

    public CacheReadNode(NodeType<CacheReadNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
        if (outputSlots == null) outputSlots = CommonRegistries.SIGNAL_TYPES.getValues().stream().filter(
                CacheWritableSignalType.class::isInstance).toArray(CacheWritableSignalType[]::new);
    }

    @Override
    public int getHeight() {
        return 43;
    }

    @Override
    public int findOutputSlot(SignalType<?> type) {
        for (int i = 0; i < outputSlots.length; i++) {
            if (outputSlots[i] == type) return i;
        }
        return -1;
    }

    @Override
    public Component getName() {
        return Translations.Nodes.CACHE_READ;
    }

    @Override
    public Object[] get(NetworkController controller) {
        Object[] ret = super.get(controller);
        cache = null;
        return ret;
    }

    @Override
    protected <S> S getSingle(NetworkAnchor anchor, Direction direction, SignalType<S> type) {
        if (cache == null && anchor.getAnchorLevel().getBlockEntity(anchor.getAnchorPos().relative(direction)) instanceof NetworkCache networkCache) cache = networkCache;
        return cache == null ? null : cache.readValue(type);
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return outputSlots;
    }
}
