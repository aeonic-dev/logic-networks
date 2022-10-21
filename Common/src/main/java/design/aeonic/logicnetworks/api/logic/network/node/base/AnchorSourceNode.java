package design.aeonic.logicnetworks.api.logic.network.node.base;

import design.aeonic.logicnetworks.api.logic.LinkCard;
import design.aeonic.logicnetworks.api.logic.LinkStatus;
import design.aeonic.logicnetworks.api.logic.NetworkAnchor;
import design.aeonic.logicnetworks.api.logic.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.SelectLinkWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public abstract class AnchorSourceNode<T extends AnchorSourceNode<T>> extends AbstractSourceNode<T> {
    protected SelectLinkWidget selectLinkWidget;
    protected ItemStack linkStack = ItemStack.EMPTY;

    public AnchorSourceNode(NodeType<T> nodeType, UUID uuid, int x, int y) {
        super(nodeType, uuid, x, y);
    }

    @Override
    public List<InputWidget> getInputWidgets() {
        return List.of(selectLinkWidget = new SelectLinkWidget(6, 15, linkStack));
    }

    @Override
    public int getWidth() {
        return Minecraft.getInstance().font.width(getName()) + 12;
    }

    @Override
    public int getHeight() {
        return 39;
    }

    @Override
    public Object[] get(NetworkController controller) {
        if (linkStack != null && !linkStack.isEmpty() && linkStack.getItem() instanceof LinkCard item) {
            if (item.getLinkStatus(linkStack) == LinkStatus.VALID && controller.getLevel().getBlockEntity(item.getLink(linkStack)) instanceof NetworkAnchor anchor) {
                var ret = new Object[getOutputSlots().length];
                for (int i = 0; i < getOutputSlots().length; i++) {
                    ret[i] = getOutputSlots()[i].read(anchor, item.getDirection(linkStack));
                }
                return ret;
            }
        }
        return new Object[getOutputSlots().length];
    }

    @Override
    public void readAdditional(CompoundTag tag) {
        linkStack = ItemStack.of(tag.getCompound("LinkStack"));
        if (selectLinkWidget != null) selectLinkWidget.setLinkStack(linkStack);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (selectLinkWidget != null) linkStack = selectLinkWidget.getLinkStack();
        CompoundTag stack = new CompoundTag();
        linkStack.save(stack);
        tag.put("LinkStack", stack);
    }

    @Override
    public void loadOnServer(NetworkController controller) {
        if (linkStack != null && linkStack.getItem() instanceof LinkCard card) card.checkStatus(controller.getLevel(), linkStack);
    }
}
