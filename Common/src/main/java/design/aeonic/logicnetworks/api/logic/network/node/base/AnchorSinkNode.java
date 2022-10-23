package design.aeonic.logicnetworks.api.logic.network.node.base;

import design.aeonic.logicnetworks.api.logic.LinkCard;
import design.aeonic.logicnetworks.api.logic.LinkStatus;
import design.aeonic.logicnetworks.api.block.NetworkAnchor;
import design.aeonic.logicnetworks.api.block.NetworkController;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.client.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.widgets.SelectLinkWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.UUID;

public abstract class AnchorSinkNode<T extends AnchorSinkNode<T>> extends AbstractSinkNode<T> {
    protected SelectLinkWidget selectLinkWidget;
    protected ItemStack linkStack = ItemStack.EMPTY;

    public AnchorSinkNode(NodeType<T> nodeType, UUID uuid, int x, int y) {
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
    public void accept(NetworkController controller, Object... inputs) {
        if (linkStack != null && !linkStack.isEmpty() && linkStack.getItem() instanceof LinkCard item) {
            if (item.getLinkStatus(linkStack) == LinkStatus.VALID && controller.getControllerLevel().getBlockEntity(item.getLink(linkStack)) instanceof NetworkAnchor anchor) {
                for (int i = 0; i < getInputSlots().length; i++) {
                    writeSingle(anchor, item.getDirection(linkStack), getInputSlots()[i], inputs[i]);
                }
            }
        }
    }

    protected <S> void writeSingle(NetworkAnchor anchor, Direction side, SignalType<S> type, Object value) {
        type.write(anchor, side, type.cast(value));
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
        if (linkStack != null && linkStack.getItem() instanceof LinkCard card) card.checkStatus(controller.getControllerLevel(), linkStack);
    }
}
