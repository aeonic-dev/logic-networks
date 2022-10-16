package design.aeonic.logicnetworks.api.services;

import design.aeonic.logicnetworks.api.networking.ClientboundPacketHandler;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public interface Networking {
    <T> void sendClientboundPacket(ServerPlayer player, ResourceLocation channel, T packet);

    <T> void sendServerboundPacket(ResourceLocation channel, T packet);

    <T> void registerClientboundPacketHandler(ClientboundPacketHandler<T> handler);

    <T> void registerServerboundPacketHandler(ServerboundPacketHandler<T> handler);
}
