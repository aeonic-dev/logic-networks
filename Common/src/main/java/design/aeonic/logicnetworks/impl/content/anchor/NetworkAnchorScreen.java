package design.aeonic.logicnetworks.impl.content.anchor;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.screen.WidgetContainerScreen;
import design.aeonic.logicnetworks.api.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.screen.input.widgets.StringInputWidget;
import design.aeonic.logicnetworks.api.screen.input.widgets.TabsInputWidget;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerListener;

import javax.annotation.Nullable;
import java.util.List;

public class NetworkAnchorScreen extends WidgetContainerScreen<NetworkAnchorMenu> {
    public static final Texture BACKGROUND = new Texture("logicnetworks:textures/gui/containers/network_anchor.png", 256, 256, 176, 142);

    private final TabsInputWidget tabs = new TabsInputWidget(2, -14, 2, 1, this::onSelectTab,
            Translations.Side.UP, Translations.Side.DOWN, Translations.Side.NORTH, Translations.Side.SOUTH, Translations.Side.EAST, Translations.Side.WEST);

    private final StringInputWidget nameWidget = new StringInputWidget(6, 6, 18, "Network Anchor") {
        @Override
        public void onLostFocus(WidgetScreen screen) {
            sendNameUpdate();
        }

        @Nullable
        @Override
        public List<Component> getTooltip(WidgetScreen screen, int mouseX, int mouseY) {
            return List.of(Translations.NetworkAnchor.NAME_TOOLTIP);
        }
    };

    private final ContainerListener listener = new ContainerListener() {
        @Override
        public void slotChanged(net.minecraft.world.inventory.AbstractContainerMenu var1, int var2, net.minecraft.world.item.ItemStack var3) {}

        @Override
        public void dataChanged(net.minecraft.world.inventory.AbstractContainerMenu var1, int var2, int var3) {
            nameWidget.setValue(menu.getName());
        }
    };

    public NetworkAnchorScreen(NetworkAnchorMenu menu, Inventory playerInventory, Component component) {
        super(menu, playerInventory, component);

        imageWidth = BACKGROUND.width();
        imageHeight = BACKGROUND.height();
        inventoryLabelY = imageHeight - 94;
    }

    void sendNameUpdate() {
        NetworkAnchorUpdatePacket.HANDLER.sendToServer(new NetworkAnchorUpdatePacket(menu.getPos(), nameWidget.getValue()));
    }

    @Override
    protected void init() {
        super.init();

        menu.addSlotListener(listener);

        addWidgets(tabs, nameWidget);
    }

    @Override
    public void onClose() {
        super.onClose();
        sendNameUpdate();
    }

    @Override
    public void removed() {
        super.removed();
        menu.removeSlotListener(listener);
    }

    public void onSelectTab(int tab) {
        menu.onSelect(tab);
    }

    @Override
    protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
        BACKGROUND.draw(var1, leftPos, topPos, getBlitOffset());
    }

    @Override
    protected void renderLabels(PoseStack $$0, int $$1, int $$2) {
        this.font.draw($$0, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }
}
