package design.aeonic.logicnetworks.impl.content.anchor;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class NetworkAnchorUpdatePacket {
    public static final Handler HANDLER = new Handler();

    private final BlockPos blockPos;
    private final String name;

    public NetworkAnchorUpdatePacket(BlockPos blockPos, String name) {
        this.blockPos = blockPos;
        this.name = name;
    }

    public static final class Handler implements ServerboundPacketHandler<NetworkAnchorUpdatePacket> {
        public static final ResourceLocation CHANNEL = new ResourceLocation(Constants.MOD_ID, "network_anchor_update");

        @Override
        public ResourceLocation getChannelId() {
            return CHANNEL;
        }

        @Override
        public Class<NetworkAnchorUpdatePacket> getPacketClass() {
            return NetworkAnchorUpdatePacket.class;
        }

        @Override
        public void handle(Consumer<Runnable> taskQueue, MinecraftServer server, ServerPlayer player, NetworkAnchorUpdatePacket packet) {
            taskQueue.accept(() -> {
                ServerLevel level = (ServerLevel) player.level;
                if (level.hasChunkAt(packet.blockPos) && level.getChunk(packet.blockPos).getBlockEntity(packet.blockPos) instanceof NetworkAnchorBlockEntity be) {
                    be.setName(packet.name);
                }
            });
        }

        @Override
        public void write(NetworkAnchorUpdatePacket object, FriendlyByteBuf buffer) {
            buffer.writeBlockPos(object.blockPos);
            buffer.writeUtf(object.name);
        }

        @Override
        public NetworkAnchorUpdatePacket read(FriendlyByteBuf buffer) {
            return new NetworkAnchorUpdatePacket(buffer.readBlockPos(), buffer.readUtf());
        }
    }
}
