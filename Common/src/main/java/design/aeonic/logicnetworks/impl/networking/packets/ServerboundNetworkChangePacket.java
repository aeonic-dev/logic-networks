package design.aeonic.logicnetworks.impl.networking.packets;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.logic.Network;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class ServerboundNetworkChangePacket {
    public static final ServerboundPacketHandler<ServerboundNetworkChangePacket> HANDLER = new Handler();

    protected final BlockPos blockPos;
    protected final Network network;

    public ServerboundNetworkChangePacket(BlockPos pos, Network network) {
        this.blockPos = pos;
        this.network = network;
    }

    public ServerboundNetworkChangePacket(FriendlyByteBuf buf) {
        blockPos = buf.readBlockPos();
        network = Network.deserialize(buf.readNbt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        CompoundTag tag = new CompoundTag();
        network.serialize(tag);
        buf.writeNbt(tag);
    }

    public static class Handler implements ServerboundPacketHandler<ServerboundNetworkChangePacket> {
        public static final ResourceLocation CHANNEL = new ResourceLocation(Constants.MOD_ID, "network_change");

        @Override
        public ResourceLocation getChannelId() {
            return CHANNEL;
        }

        @Override
        public Class<ServerboundNetworkChangePacket> getPacketClass() {
            return ServerboundNetworkChangePacket.class;
        }

        @Override
        public void handle(Consumer<Runnable> taskQueue, MinecraftServer server, ServerPlayer player, ServerboundNetworkChangePacket packet) {
            taskQueue.accept(() -> {
                ServerLevel level = (ServerLevel) player.level;
                if (level.hasChunkAt(packet.blockPos) && level.getChunk(packet.blockPos).getBlockEntity(packet.blockPos) instanceof Network.IHasNetwork be) {
                    be.setNetwork(packet.network);
                }
            });
        }

        @Override
        public void write(ServerboundNetworkChangePacket object, FriendlyByteBuf buffer) {
            object.write(buffer);
        }

        @Override
        public ServerboundNetworkChangePacket read(FriendlyByteBuf buffer) {
            return new ServerboundNetworkChangePacket(buffer);
        }
    }
}
