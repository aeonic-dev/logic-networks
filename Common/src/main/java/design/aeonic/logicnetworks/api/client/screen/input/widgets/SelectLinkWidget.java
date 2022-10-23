package design.aeonic.logicnetworks.api.client.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.client.screen.input.AbstractInputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class SelectLinkWidget extends AbstractInputWidget {
    public static final Texture SLOT = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 18, 18, 7, 12);
    public static final Texture SLOT_HIGHLIGHT = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 18, 18, 0, 37);

    /**
     * One instance on the client; when another needs to be opened the last one is cleared.
     */
    public static LinkSlotsWidget CURRENT_SELECTOR = null;

    private ItemStack linkStack;
    private LinkSlotsWidget selector = null;

    public SelectLinkWidget(int x, int y, ItemStack linkStack) {
        super(x, y);
        this.linkStack = linkStack;
    }

    public ItemStack getLinkStack() {
        return linkStack;
    }

    public void setLinkStack(ItemStack linkStack) {
        this.linkStack = linkStack;
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        if (super.mouseDown(screen, mouseX, mouseY, button) || CURRENT_SELECTOR == null) {
            if (CURRENT_SELECTOR != null) screen.removeWidget(CURRENT_SELECTOR);
            CURRENT_SELECTOR = screen.addWidget(selector = LinkSlotsWidget.create(stack -> {
                setLinkStack(stack);
                screen.removeWidget(selector);
                CURRENT_SELECTOR = null;
            }, getX() - 7, getY() + 19));
        }
        return true;
    }

    @Override
    public void onLostFocus(WidgetScreen screen) {
        super.onLostFocus(screen);
        if (selector != null && CURRENT_SELECTOR == selector) {
            screen.removeWidget(selector);
            CURRENT_SELECTOR = null;
        }
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        SLOT.draw(stack, getX(), getY(), 0);
        if (!linkStack.isEmpty()) Minecraft.getInstance().getItemRenderer().renderGuiItem(linkStack, screen.getRenderLeftPos() + getX() + 1, screen.getRenderTopPos() + getY() + 1);
        if (isHighlighted(mouseX, mouseY)) {
            SLOT_HIGHLIGHT.draw(stack, getX(), getY(), 0);
            if (!linkStack.isEmpty()) RenderUtils.renderTooltip(stack, linkStack, mouseX, mouseY);
        }
    }

    public boolean isHighlighted(int mouseX, int mouseY) {
        return mouseX >= getX() + 1 && mouseX <= getX() + 17 && mouseY >= getY() + 1 && mouseY <= getY() + 17;
    }

    @Override
    public int getWidth() {
        return 18;
    }

    @Override
    public int getHeight() {
        return 18;
    }
}
