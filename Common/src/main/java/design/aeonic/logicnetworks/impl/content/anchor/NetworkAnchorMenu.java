package design.aeonic.logicnetworks.impl.content.anchor;

import design.aeonic.logicnetworks.api.networking.container.BaseContainerMenu;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.MovableSlot;
import design.aeonic.logicnetworks.api.networking.container.field.BlockPosField;
import design.aeonic.logicnetworks.api.networking.container.field.StringField;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import design.aeonic.logicnetworks.impl.content.NetworkMenus;
import design.aeonic.logicnetworks.impl.content.link.LinkingCardItem;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class NetworkAnchorMenu extends BaseContainerMenu<NetworkAnchorMenu> {
    public final Container container;
    public final ContainerFields data;
    public final MovableSlot[] slots = new MovableSlot[6];

    private final ContainerListener cardRefreshListener = new ContainerListener() {
        @Override
        public void slotChanged(AbstractContainerMenu var1, int var2, ItemStack var3) {}

        @Override
        public void dataChanged(AbstractContainerMenu var1, int var2, int var3) {
            refreshCards();
        }
    };

    private int selected = 0;

    public NetworkAnchorMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new ContainerFields(new BlockPosField(), new StringField(18)));
    }

    public NetworkAnchorMenu(int syncId, Inventory inventory, ContainerFields containerData) {
        super(NetworkMenus.NETWORK_ANCHOR, 6, syncId, inventory);
        this.data = containerData;

        container = containerData.isClientSide() ? new SimpleContainer(6) : new LinkCardContainer();
        for (int i = 0; i < 6; i++) {
            addSlot(slots[i] = Services.ACCESS.movableSlot(container, i, 134, i == selected ? 23 : 999));
        }
        addPlayerSlots();

        addDataSlots(containerData);
        addSlotListener(cardRefreshListener);
    }

    @Override
    public void removed(Player $$0) {
        super.removed($$0);

        removeSlotListener(cardRefreshListener);
    }

    @Override
    public void setData(int $$0, int $$1) {
        super.setData($$0, $$1);
        broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack ret = super.quickMoveStack(player, index);
        return ItemStack.EMPTY;
    }

    public void refreshCards() {
        container.setChanged();
    }

    public void onSelect(int selected) {
        this.selected = selected;
        for (int i = 0; i < 6; i++) {
            slots[i].setY(i == selected ? 23 : 999);
        }
    }

    protected void addPlayerSlots() {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 60 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 118));
        }
    }

    public BlockPos getPos() {
        return data.getField(0);
    }

    public String getName() {
        return data.getField(1);
    }

    public class LinkCardContainer implements Container {
        private static final Direction[] directions = new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        private ItemStack[] cache = new ItemStack[6];

        public LinkCardContainer() {
            setChanged();
        }

        @Override
        public ItemStack getItem(int $$0) {
            return getLinkCard($$0);
        }

        @Override
        public ItemStack removeItem(int $$0, int $$1) {
            return getLinkCard($$0);
        }

        @Override
        public boolean canPlaceItem(int $$0, ItemStack $$1) {
            return false;
        }

        @Override
        public int countItem(Item $$0) {
            return $$0 == NetworkItems.LINKING_CARD ? 1 : 0;
        }

        @Override
        public void setItem(int $$0, ItemStack $$1) {}

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int getContainerSize() {
            return 6;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public void setChanged() {
            String literalName = getName();
            String name = literalName.equals("") || literalName.equals("Network Anchor") ? Component.Serializer.toJson(Component.literal(literalName).withStyle(ChatFormatting.RESET)) : null;
            for (int i = 0; i < cache.length; i++) {
                cache[i] = LinkingCardItem.create(getPos(), directions[i]);
                if (name != null) cache[i].getOrCreateTagElement("display").putString("Name", name);
            }
        }

        @Override
        public boolean stillValid(Player var1) {
            return false;
        }

        @Override
        public ItemStack removeItemNoUpdate(int $$0) {
            return getLinkCard($$0);
        }

        @Override
        public boolean hasAnyOf(Set<Item> $$0) {
            return $$0.contains(NetworkItems.LINKING_CARD);
        }

        @Override
        public void clearContent() {}

        private ItemStack getLinkCard(int index) {
            if (BlockPos.ZERO.equals(NetworkItems.LINKING_CARD.getLink(cache[index]))) setChanged();
            return cache[index];
        }
    }
}
