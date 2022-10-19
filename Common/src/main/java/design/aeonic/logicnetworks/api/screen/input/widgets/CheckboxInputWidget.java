package design.aeonic.logicnetworks.api.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.screen.input.AbstractInputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.Texture;

public class CheckboxInputWidget extends AbstractInputWidget {
    public static final Texture HIGHLIGHT = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 14, 14, 0, 24);
    public static final Texture EMPTY = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 19, 0);
    public static final Texture CHECKED = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 31, 0);

    private boolean value;


    public CheckboxInputWidget(int x, int y, boolean value) {
        super(x, y);

        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        if (button != 0) return false;
        value = !value;
        playClickSound();
        return true;
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        if (isEnabled() && (isWithinBounds(mouseX, mouseY) || screen.getFocusedWidget() == this)) {
            HIGHLIGHT.draw(stack, getX() - 1, getY() - 1, 0);
        }

        float[] rgba = isEnabled() ? new float[]{1, 1, 1, 1} : new float[]{0.5f, 0.5f, 0.5f, 1};
        (value ? CHECKED : EMPTY).draw(stack, getX(), getY(), 0, rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    @Override
    public int getWidth() {
        return 12;
    }

    @Override
    public int getHeight() {
        return 12;
    }
}
