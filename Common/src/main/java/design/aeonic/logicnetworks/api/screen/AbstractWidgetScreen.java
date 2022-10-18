package design.aeonic.logicnetworks.api.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.RenderUtils;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWidgetScreen extends Screen implements WidgetScreen {
    public static final Texture TOOLTIP = new Texture("logicnetworks:textures/gui/tooltip.png", 16, 16, 16, 16);

    protected final List<InputWidget> inputWidgets = new ArrayList<>();
    protected InputWidget focusedWidget = null;

    public AbstractWidgetScreen(Component component) {
        super(component);
    }

    public abstract int getLeftPos();

    public abstract int getTopPos();

    @Override
    public void onClose() {
        inputWidgets.forEach(widget -> widget.onClose(this));
        super.onClose();
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        super.render(stack, mouseX, mouseY, partialTick);

        stack.pushPose();
        stack.translate(getLeftPos(), getTopPos(), 0);
        for (InputWidget widget : inputWidgets) {
            widget.draw(stack, this, mouseX - getLeftPos(), mouseY - getTopPos(), partialTick);
        }
        stack.popPose();

        InputWidget hovered = getHoveredWidget(mouseX, mouseY);
        if (hovered != null) {
            var tooltip = hovered.getTooltip(this, mouseX - getLeftPos(), mouseY - getTopPos());
            if (tooltip != null) renderTooltip(stack, mouseX, mouseY, tooltip);
        }
    }

    public void renderTooltip(PoseStack stack, int mouseX, int mouseY, List<Component> tooltip) {
        int x = mouseX + 6;
        int y = mouseY + 12;
        int width = font.width(tooltip.stream().max((a, b) -> font.width(a) - font.width(b)).orElse(Component.empty())) + 4;
        int height = tooltip.size() * font.lineHeight + 3;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderUtils.drawRect(stack, TOOLTIP, x - 2, y - 2, getBlitOffset() + 400, width, height, 0xF0FFFFFF);
        stack.pushPose();
        stack.translate(0, 0, 500);
        for (int i = 0; i < tooltip.size(); i++) {
            font.draw(stack, tooltip.get(i), x, y + i * font.lineHeight, 0xFFFFFF);
        }
        stack.popPose();
        RenderSystem.disableBlend();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        InputWidget widget = getHoveredWidget((int) mouseX, (int) mouseY);
        if (widget != null && widget.isEnabled()) {
            if (widget.mouseDown(this, (int) mouseX - getLeftPos(), (int) mouseY - getTopPos(), button)) return true;
        } else if (button == 0) clearFocus(getFocusedWidget());
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (inputWidgets.stream().anyMatch(widget -> widget.mouseUp(this, (int) mouseX - getLeftPos(), (int) mouseY - getTopPos(), button))) return true;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int mods) {
        if (focusedWidget != null && focusedWidget.isEnabled()) {
            if (focusedWidget.keyDown(this, key, scanCode, mods)) return true;
        }
        return super.keyPressed(key, scanCode, mods);
    }

    @Override
    public boolean keyReleased(int key, int scanCode, int mods) {
        if (focusedWidget != null && focusedWidget.isEnabled()) {
            if (focusedWidget.keyUp(this, key, scanCode, mods)) return true;
        }
        return super.keyReleased(key, scanCode, mods);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta) {
        InputWidget widget = getHoveredWidget((int) mouseX, (int) mouseY);
        if (widget != null && widget.isEnabled() && widget.mouseScrolled(this, (int) mouseX - getLeftPos(), (int) mouseY - getTopPos(), scrollDelta)) return true;
        else if (super.mouseScrolled(mouseX, mouseY, scrollDelta)) return true;
        else return focusedWidget != null && focusedWidget.mouseScrolled(this, (int) mouseX - getLeftPos(), (int) mouseY - getTopPos(), scrollDelta);
    }

    public void addWidgets(InputWidget... widgets) {
        for (InputWidget widget : widgets) {
            addWidget(widget);
        }
    }

    @Override
    public  <W extends InputWidget> W addWidget(W widget) {
        // Don't add if the widget is already in the list - sometimes gets duplicated otherwise when screen is resized
        if (!inputWidgets.contains(widget)) inputWidgets.add(widget);
        return widget;
    }

    @Override
    public void removeWidget(InputWidget widget) {
        inputWidgets.remove(widget);
    }

    public InputWidget getHoveredWidget(int mouseX, int mouseY) {
        return getWidgetAt(mouseX - getLeftPos(), mouseY - getTopPos());
    }

    @Nullable
    @Override
    public InputWidget getWidgetAt(int x, int y) {
        return inputWidgets.stream().filter(widget -> widget.isWithinBounds(x, y)).findFirst().orElse(null);
    }

    @Nullable
    @Override
    public InputWidget getFocusedWidget() {
        return focusedWidget;
    }

    @Override
    public void setFocus(InputWidget widget) {
        focusedWidget = widget;
    }

    @Override
    public void clearFocus(InputWidget widget) {
        if (focusedWidget == widget) {
            focusedWidget = null;
        }
    }
}
