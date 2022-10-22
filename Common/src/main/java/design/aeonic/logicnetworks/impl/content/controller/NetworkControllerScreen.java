package design.aeonic.logicnetworks.impl.content.controller;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.NetworkController;
import design.aeonic.logicnetworks.api.logic.RedstoneControl;
import design.aeonic.logicnetworks.api.logic.network.Network;
import design.aeonic.logicnetworks.api.screen.WidgetContainerScreen;
import design.aeonic.logicnetworks.api.screen.input.InputWidget;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.widgets.ButtonInputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.IntInputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.RedstoneInputWidget;
import design.aeonic.logicnetworks.api.util.Texture;
import design.aeonic.logicnetworks.impl.client.NetworkGraphScreen;
import design.aeonic.logicnetworks.impl.networking.packets.ServerboundNetworkChangePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class NetworkControllerScreen extends WidgetContainerScreen<NetworkControllerMenu> {
    public static final Texture BACKGROUND = new Texture("logicnetworks:textures/gui/containers/network_controller.png", 128, 128, 128, 56);

    private RedstoneInputWidget redstoneControlInputWidget = new RedstoneInputWidget(8, 19, RedstoneControl.ALWAYS);

    private IntInputWidget ticksPerOperationInput = new IntInputWidget(8 + redstoneControlInputWidget.getWidth() + 3, 19, 1, 1200, 4, true, 20, 20) {
        @Override
        public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
            return List.of(Translations.NetworkController.TICKS_PER_OP);
        }
    };

    private ButtonInputWidget editNetworkButton = new ButtonInputWidget(8, 36, Translations.NetworkController.EDIT_NETWORK, this::openNetworkGraph) {
        @Override
        public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
            return List.of(Translations.NetworkController.EDIT_NETWORK_TOOLTIP);
        }
    };

    private final ContainerListener listener = new net.minecraft.world.inventory.ContainerListener() {
        @Override
        public void slotChanged(AbstractContainerMenu var1, int var2, ItemStack var3) {}

        @Override
        public void dataChanged(AbstractContainerMenu var1, int var2, int var3) {
            redstoneControlInputWidget.setValue(menu.getRedstoneControl());
            ticksPerOperationInput.setValue(menu.getTicksPerOperation());
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

        menu.addSlotListener(listener);

        editNetworkButton.setX(8 + (imageWidth - 16 - editNetworkButton.getWidth()) / 2);
        addWidgets(redstoneControlInputWidget, ticksPerOperationInput, editNetworkButton);
    }

    public void openNetworkGraph() {
        onClose();

        assert menu.data.isClientSide();
        Level level = Minecraft.getInstance().level;
        assert Objects.requireNonNull(level).hasChunkAt(menu.getControllerPos());
        NetworkGraphScreen.open(((NetworkController) Objects.requireNonNull(level.getBlockEntity(menu.getControllerPos()))).getNetwork(), this::onNetworkGraphClosed);
    }

    public void onNetworkGraphClosed(Network network) {
        ServerboundNetworkChangePacket.HANDLER.sendToServer(new ServerboundNetworkChangePacket(menu.getControllerPos(), network));
    }

    @Override
    public void removed() {
        super.removed();
        menu.removeSlotListener(listener);
    }

    @Override
    public void onClose() {
        NetworkControllerUpdatePacket.HANDLER.sendToServer(new NetworkControllerUpdatePacket(
                menu.getControllerPos(), redstoneControlInputWidget.getValue(), ticksPerOperationInput.getValue()));

        super.onClose();
    }

    @Override
    protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
        renderBackground(var1, 0);
        BACKGROUND.draw(var1, leftPos, topPos, 0);
    }

    @Override
    protected void renderLabels(PoseStack $$0, int $$1, int $$2) {
        font.draw($$0, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 0x404040);
    }

    protected void drawNextTo(PoseStack stack, InputWidget widget, Component label, int offsetX, int offsetY, int color) {
        font.draw(stack, label, widget.getRight() + offsetX, widget.getY() + Mth.ceil((widget.getHeight() - font.lineHeight) / 2f) + offsetY, color);
    }
}
