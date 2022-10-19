package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.control.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.networking.container.BaseContainerMenu;
import design.aeonic.logicnetworks.api.networking.container.ContainerFields;
import design.aeonic.logicnetworks.api.networking.container.field.BlockPosField;
import design.aeonic.logicnetworks.api.networking.container.field.EnumField;
import design.aeonic.logicnetworks.api.networking.container.field.IntField;
import design.aeonic.logicnetworks.impl.content.NetworkMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class NetworkControllerMenu extends BaseContainerMenu<NetworkControllerMenu> {
    public final ContainerFields data;

    public NetworkControllerMenu(int syncId, Inventory inventory) {
        this(syncId, inventory, new ContainerFields(new EnumField<>(RedstoneControl.class), new IntField(), new BlockPosField()));
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

    @Override
    public void setData(int $$0, int $$1) {
        super.setData($$0, $$1);
        broadcastChanges();
    }

    public RedstoneControl getRedstoneControl() {
        Constants.LOG.info("redstone control {}", (Object) data.getField(0));
        return data.getField(0);
    }

    public int getTicksPerOperation() {
        Constants.LOG.info("ticks per op {}", (Object) data.getField(1));
        return data.getField(1);
    }

    public BlockPos getControllerPos() {
        Constants.LOG.info("controller pos {}", (Object) data.getField(2));
        return data.getField(2);
    }
}
