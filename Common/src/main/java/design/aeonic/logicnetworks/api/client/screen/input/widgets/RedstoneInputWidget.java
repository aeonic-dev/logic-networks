package design.aeonic.logicnetworks.api.client.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.client.screen.input.AbstractInputWidget;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.logic.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class RedstoneInputWidget extends AbstractInputWidget {
    public static final Texture HIGHLIGHT = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 14, 14, 0, 24);
    public static final Texture ALWAYS = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 0, 50);
    public static final Texture NEVER = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 12, 50);
    public static final Texture HIGH = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 24, 50);
    public static final Texture LOW = new Texture("logicnetworks:textures/gui/input_widgets.png", 64, 64, 12, 12, 36, 50);

    private static final Map<RedstoneControl, List<Component>> TOOLTIPS = Map.of(
            RedstoneControl.ALWAYS, List.of(Translations.RedstoneControl.ALWAYS),
            RedstoneControl.HIGH, List.of(Translations.RedstoneControl.HIGH),
            RedstoneControl.LOW, List.of(Translations.RedstoneControl.LOW),
            RedstoneControl.NEVER, List.of(Translations.RedstoneControl.NEVER)
    );

    private RedstoneControl value;

    public RedstoneInputWidget(int x, int y, RedstoneControl value) {
        super(x, y);

        this.value = value;
    }

    public void setValue(RedstoneControl value) {
        this.value = value;
    }

    public RedstoneControl getValue() {
        return value;
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        cycleValue(Screen.hasShiftDown());
        playClickSound();
        return true;
    }

    private void cycleValue(boolean isShiftDown) {
        value = RedstoneControl.values()[(value.ordinal() + (isShiftDown ? -1 : 1) + RedstoneControl.values().length) % RedstoneControl.values().length];
        playClickSound();
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        if (isEnabled() && (isWithinBounds(mouseX, mouseY) || screen.getFocusedWidget() == this)) {
            HIGHLIGHT.draw(stack, getX() - 1, getY() - 1, 200);
        }

        float[] rgba = isEnabled() ? new float[]{1, 1, 1, 1} : new float[]{0.5f, 0.5f, 0.5f, 1};
        (switch (value) {
            case ALWAYS -> ALWAYS;
            case NEVER -> NEVER;
            case HIGH -> HIGH;
            case LOW -> LOW;
        }).draw(stack, getX(), getY(), 200, rgba[0], rgba[1], rgba[2], rgba[3]);


    }

    @Nullable
    @Override
    public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
        return TOOLTIPS.get(value);
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
