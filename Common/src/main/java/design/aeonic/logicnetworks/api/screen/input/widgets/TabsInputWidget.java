package design.aeonic.logicnetworks.api.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.screen.input.AbstractInputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class TabsInputWidget extends AbstractInputWidget {

    public static final Texture TAB_LEFT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 12, 15, 12);
    public static final Texture TAB_FILL = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 64, 12, 0, 28);
    public static final Texture TAB_RIGHT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 12, 24, 12);
    public static final Texture TAB_HOVERED_LEFT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 12, 30, 12);
    public static final Texture TAB_HOVERED_FILL = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 64, 1, 0, 27);
    public static final Texture TAB_HOVERED_RIGHT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 12, 39, 12);
    public static final Texture TAB_SELECTED_LEFT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 15, 0, 12);
    public static final Texture TAB_SELECTED_FILL = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 64, 15, 0, 40);
    public static final Texture TAB_SELECTED_RIGHT = new Texture("logicnetworks:textures/gui/input_widgets_extended.png", 64, 64, 4, 15, 9, 12);

    protected final int spacing = 2;

    protected final Consumer<Integer> onSelect;
    protected final Component[] tabs;
    protected int selected = 0;

    public TabsInputWidget(int x, int y, Consumer<Integer> onSelect, Component... tabs) {
        super(x, y);
        this.onSelect = onSelect;
        this.tabs = tabs;
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        if (button == 0) {
            int hovered = getHoveredTab(mouseX, mouseY);
            if (hovered != -1) {
                selected = hovered;
                onSelect.accept(selected);
                playClickSound();
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        int hovered = getHoveredTab(mouseX, mouseY);

        int x = getX();
        for (int i = 0; i < tabs.length; i++) {
            int width = getTabWidth(i);
            if (selected == i) {
                TAB_SELECTED_LEFT.draw(stack, x, getY(), 0);
                TAB_SELECTED_FILL.draw(stack, x + 4, getY(), width - 8);
                TAB_SELECTED_RIGHT.draw(stack, x + width - 4, getY(), 0);
            } else {
                TAB_LEFT.draw(stack, x, getY(), 0);
                TAB_FILL.draw(stack, x + 4, getY(), width - 8);
                TAB_RIGHT.draw(stack, x + width - 4, getY(), 0);
                if (i == hovered) {
                    TAB_HOVERED_LEFT.draw(stack, x, getY(), 0);
                    TAB_HOVERED_FILL.draw(stack, x + 4, getY(), width - 8);
                    TAB_HOVERED_RIGHT.draw(stack, x + width - 4, getY(), 0);
                }
                x += width + spacing;
            }
            Minecraft.getInstance().font.draw(stack, tabs[i], x + 4, getY() + 4, 0x404040);
        }
    }

    @Override
    public int getWidth() {
        int width = spacing * (tabs.length - 1);
        for (int i = 0; i < tabs.length; i++) {
            width += getTabWidth(i);
        }
        return width;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    public int getHoveredTab(int mouseX, int mouseY) {
        if (!isWithinBounds(mouseX, mouseY)) return -1;
        int x = 0;
        for (int i = 0; i < tabs.length; i++) {
            if (isWithinBounds(x, 0, getTabWidth(i), 12, mouseX, mouseY)) return i;
            x += getTabWidth(i) + spacing;
        }
        return -1;
    }

    public int getTabWidth(int index) {
        return 8 + Minecraft.getInstance().font.width(tabs[index]);
    }
}
