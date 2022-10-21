package design.aeonic.logicnetworks.impl.content.controller;

import design.aeonic.logicnetworks.api.logic.RedstoneControl;
import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class NetworkControllerUpdatePacket {
    public static final Handler HANDLER = new Handler();

    private final BlockPos blockPos;
    private final RedstoneControl redstoneControl;
    private final int ticksPerOperation;

    public NetworkControllerUpdatePacket(BlockPos blockPos, RedstoneControl redstoneControl, int ticksPerOperation) {
        this.blockPos = blockPos;
        this.redstoneControl = redstoneControl;
        this.ticksPerOperation = ticksPerOperation;
    }

    public static final class Handler implements ServerboundPacketHandler<NetworkControllerUpdatePacket> {
        public static final ResourceLocation CHANNEL = new ResourceLocation(Constants.MOD_ID, "network_controller_update");

        @Override
        public ResourceLocation getChannelId() {
            return CHANNEL;
        }

        @Override
        public Class<NetworkControllerUpdatePacket> getPacketClass() {
            return NetworkControllerUpdatePacket.class;
        }

        @Override
        public void handle(Consumer<Runnable> taskQueue, MinecraftServer server, ServerPlayer player, NetworkControllerUpdatePacket packet) {
            taskQueue.accept(() -> {
                ServerLevel level = (ServerLevel) player.level;
                if (level.hasChunkAt(packet.blockPos) && level.getChunk(packet.blockPos).getBlockEntity(packet.blockPos) instanceof NetworkControllerBlockEntity be) {
                    be.setRedstoneControl(packet.redstoneControl);
                    be.setTicksPerOperation(packet.ticksPerOperation);
                }
            });
        }

        @Override
        public void write(NetworkControllerUpdatePacket object, FriendlyByteBuf buffer) {
            buffer.writeBlockPos(object.blockPos);
            buffer.writeEnum(object.redstoneControl);
            buffer.writeVarInt(object.ticksPerOperation);
        }

        @Override
        public NetworkControllerUpdatePacket read(FriendlyByteBuf buffer) {
            return new NetworkControllerUpdatePacket(buffer.readBlockPos(), buffer.readEnum(RedstoneControl.class), buffer.readVarInt());
        }
    }
}
