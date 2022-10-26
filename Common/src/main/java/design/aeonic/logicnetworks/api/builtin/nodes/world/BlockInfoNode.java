package design.aeonic.logicnetworks.api.builtin.nodes.world;

import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.builtin.BuiltinSignalTypes;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.LinkCard;
import design.aeonic.logicnetworks.api.logic.LinkStatus;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.base.AnchorSourceNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.UUID;

public class BlockInfoNode extends AnchorSourceNode<BlockInfoNode> {
    private static final SignalType<?>[] slots = SignalType.arrayOf(BuiltinSignalTypes.BOOLEAN, BuiltinSignalTypes.STRING, BuiltinSignalTypes.NBT);

    public BlockInfoNode(NodeType<BlockInfoNode> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public List<Component> getSocketTooltip(boolean isOutput, int index) {
        return isOutput ? switch (index) {
            case 0 -> List.of(Translations.Generic.IS_AIR, BuiltinSignalTypes.BOOLEAN.getSocketTooltip(true));
            case 1 -> List.of(Translations.Generic.BLOCK_ID, BuiltinSignalTypes.STRING.getSocketTooltip(true));
            case 2 -> List.of(Translations.Generic.BLOCK_STATE, BuiltinSignalTypes.NBT.getSocketTooltip(true));
            default -> super.getSocketTooltip(true, index);
        } :  super.getSocketTooltip(false, index);
    }

    @Override
    public Object[] get(NetworkController controller) {
        if (linkStack != null && !linkStack.isEmpty() && linkStack.getItem() instanceof LinkCard item) {
            if (item.getLinkStatus(linkStack) == LinkStatus.VALID && controller.getControllerLevel().getBlockEntity(item.getLink(linkStack)) instanceof NetworkAnchor anchor) {
                BlockPos relative = anchor.getAnchorPos().relative(item.getDirection(linkStack));
                BlockState state = anchor.getAnchorLevel().getBlockState(relative);
                return new Object[]{
                        state.isAir(),
                        Registry.BLOCK.getKey(state.getBlock()).toString(),
                        BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state).getOrThrow(false, Constants.LOG::error)
                };
            }
        }
        return new Object[getOutputSlots().length];
    }

    @Override
    public Component getName() {
        return Translations.Nodes.BLOCK_INFO;
    }

    @Override
    public SignalType<?>[] getOutputSlots() {
        return slots;
    }
}
