package design.aeonic.logicnetworks.impl.content;

import design.aeonic.logicnetworks.api.util.Registrar;
import design.aeonic.logicnetworks.impl.content.controller.NetworkControllerBlockEntity;
import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class NetworkBlockEntities {
    public static final BlockEntityType<NetworkControllerBlockEntity> NETWORK_CONTROLLER = Services.ACCESS.blockEntityType(NetworkControllerBlockEntity::new, NetworkBlocks.NETWORK_CONTROLLER);

    public static void register(Registrar<BlockEntityType<?>> registrar) {
        registrar.accept("network_controller", NETWORK_CONTROLLER);
    }
}
