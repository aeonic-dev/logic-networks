package design.aeonic.logicnetworks.api.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.logic.LinkCard;
import design.aeonic.logicnetworks.api.screen.input.AbstractInputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class LinkSlotsWidget extends AbstractInputWidget {
    public static final Texture LEFT = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 7, 32, 0, 5);
    public static final Texture RIGHT = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 7, 32, 25, 5);
    public static final Texture SLOT = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 18, 32, 7, 5);
    public static final Texture SLOT_ARROW = new Texture("logicnetworks:textures/gui/link_slot.png", 64, 64, 18, 37, 32, 0);

    private final List<ItemStack> itemStacks;
    private Consumer<ItemStack> onSelect;

    public LinkSlotsWidget(List<ItemStack> itemStacks, Consumer<ItemStack> onSelect, int x, int y) {
        super(x, y);
        this.itemStacks = itemStacks;
        this.onSelect = onSelect;
    }

    public static LinkSlotsWidget create(Consumer<ItemStack> onSelect, int x, int y) {
        List<ItemStack> stacks = Minecraft.getInstance().player.getInventory().items.stream()
                .filter(stack -> stack.getItem() instanceof LinkCard).limit(9).toList();
        if (stacks.isEmpty()) stacks = List.of(ItemStack.EMPTY);
        return new LinkSlotsWidget(stacks, onSelect, x, y);
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        if (button == 0) {
            int highlighted = getHighlighted(mouseX, mouseY);
            if (highlighted != -1) {
                onSelect.accept(itemStacks.get(highlighted));
                playClickSound();
            }
            return true;
        }
        return false;
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        LEFT.draw(stack, getX(), getY() + 5, 0);
        RIGHT.draw(stack, getX() + 7 + 18 * itemStacks.size(), getY() + 5, 0);
        for (int i = 0; i < itemStacks.size(); i++) {
            if (i == 0) SLOT_ARROW.draw(stack, getX() + 7 + i * 18, getY(), 0);
            else SLOT.draw(stack, getX() + 7 + i * 18, getY() + 5, 0);

            Minecraft.getInstance().getItemRenderer().renderGuiItem(itemStacks.get(i), screen.getRenderLeftPos() + getX() + 7 + i * 18 + 1, screen.getRenderTopPos() + getY() + 13);
        }

        int highlighted = getHighlighted(mouseX, mouseY);
        if (highlighted != -1) {
            AbstractContainerScreen.renderSlotHighlight(stack, getX() + 8 + highlighted * 18, getY() + 13, 0);
            RenderUtils.renderTooltip(stack, itemStacks.get(highlighted), mouseX, mouseY);
        }
    }

    public int getHighlighted(int mouseX, int mouseY) {
        if (mouseY < getY() + 8 + 5 || mouseY > getY() + 7 + 5 + 16) return -1;
        if (mouseX < getX() + 8 || mouseX > getX() + 7 + 18 * itemStacks.size()) return -1;
        int ret = (mouseX - getX() - 6) / 18;
        return ret >= itemStacks.size() ? -1 : ret;
    }

    @Override
    public int getWidth() {
        return 14 + 18 * itemStacks.size();
    }

    @Override
    public int getHeight() {
        return 37;
    }
}
