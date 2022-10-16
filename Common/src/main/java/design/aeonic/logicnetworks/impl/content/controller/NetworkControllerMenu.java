package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.control.RedstoneControl;
import design.aeonic.logicnetworks.api.networking.container.BaseContainerMenu;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.field.EnumField;
import design.aeonic.logicnetworks.api.networking.container.field.IntField;
import design.aeonic.logicnetworks.impl.content.NetworkMenus;
import net.minecraft.world.entity.player.Inventory;
public class NetworkControllerMenu extends BaseContainerMenu<NetworkControllerMenu> {
    private final ContainerFields data;

    public NetworkControllerMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new ContainerFields(new EnumField<>(RedstoneControl.class), new IntField()));
    }

    @Override
    protected void addPlayerSlots() {
        // Prevents the parent method from adding player slots
    }

    public NetworkControllerMenu(int syncId, Inventory inventory, ContainerFields containerData) {
        super(NetworkMenus.NETWORK_CONTROLLER, 0, syncId, inventory);
        this.data = containerData;

        addDataSlots(containerData);
    }

    public RedstoneControl getRedstoneControl() {
        return data.getField(0);
    }

    public int getTicksPerOperation() {
        return data.getField(1);
    }
}
