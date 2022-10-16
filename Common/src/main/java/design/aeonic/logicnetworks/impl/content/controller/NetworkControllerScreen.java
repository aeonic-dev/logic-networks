package design.aeonic.logicnetworks.impl.content.controller;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.control.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.screen.WidgetContainerScreen;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.widgets.IntInputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.RedstoneInputWidget;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class NetworkControllerScreen extends WidgetContainerScreen<NetworkControllerMenu> {
    public static final Texture BACKGROUND = new Texture("logicnetworks:textures/gui/containers/network_controller.png", 128, 128, 128, 86);

    private RedstoneInputWidget redstoneControlInputWidget = new RedstoneInputWidget(8, 19, RedstoneControl.ALWAYS);

    private IntInputWidget ticksPerOperationInput = new IntInputWidget(8, 35, 1, 1200, 4, true, 20, 20) {
        @Override
        public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
            return List.of(Translations.NetworkController.TICKS_PER_OP);
        }
    };

    public NetworkControllerScreen(NetworkControllerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        imageWidth = BACKGROUND.width();
        imageHeight = BACKGROUND.height();
    }

    @Override
    protected void init() {
        super.init();

        addWidgets(redstoneControlInputWidget, ticksPerOperationInput);
    }

    @Override
    protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
        BACKGROUND.draw(var1, leftPos, topPos, 0);
    }

    @Override
    protected void renderLabels(PoseStack $$0, int $$1, int $$2) {
        font.draw($$0, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 0x404040);

        drawNextTo($$0, redstoneControlInputWidget, Translations.InputLabels.REDSTONE_CONTROL, 3, 0, 0x555555);
        drawNextTo($$0, ticksPerOperationInput, Translations.InputLabels.TICKS, 3, 0, 0x555555);
    }

    protected void drawNextTo(PoseStack stack, InputWidget widget, Component label, int offsetX, int offsetY, int color) {
        font.draw(stack, label, widget.getRight() + offsetX, widget.getY() + Mth.ceil((widget.getHeight() - font.lineHeight) / 2f) + offsetY, color);
    }
}
