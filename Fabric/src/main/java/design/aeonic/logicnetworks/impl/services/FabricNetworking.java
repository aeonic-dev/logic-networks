package design.aeonic.logicnetworks.impl.services;

import design.aeonic.logicnetworks.api.networking.ClientboundPacketHandler;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import design.aeonic.logicnetworks.api.services.Networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class FabricNetworking implements Networking {
    private final Map<Class<?>, ClientboundPacketHandler<?>> clientboundPacketHandlers = new HashMap<>();
    private final Map<Class<?>, ServerboundPacketHandler<?>> serverboundPacketHandlers = new HashMap<>();

    @Override
    public <T> void sendClientboundPacket(ServerPlayer player, ResourceLocation channel, T packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ClientboundPacketHandler<T> handler = (ClientboundPacketHandler<T>) clientboundPacketHandlers.get(packet.getClass());
        handler.write(packet, buf);

        ServerPlayNetworking.send(player, channel, buf);
    }

    @Override
    public <T> void sendServerboundPacket(ResourceLocation channel, T packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ServerboundPacketHandler<T> handler = (ServerboundPacketHandler<T>) serverboundPacketHandlers.get(packet.getClass());
        handler.write(packet, buf);

        ClientPlayNetworking.send(channel, buf);
    }

    @Override
    public <T> void registerClientboundPacketHandler(ClientboundPacketHandler<T> handler) {
        clientboundPacketHandlers.put(handler.getPacketClass(), handler);
        ClientPlayNetworking.registerReceiver(handler.getChannelId(), (client, handler1, buf, responseSender) -> handler.handle(client::submit, client, handler.read(buf)));
    }

    @Override
    public <T> void registerServerboundPacketHandler(ServerboundPacketHandler<T> handler) {
        serverboundPacketHandlers.put(handler.getPacketClass(), handler);
        ServerPlayNetworking.registerGlobalReceiver(handler.getChannelId(), (server, player, handler1, buf, responseSender) -> handler.handle(server::submit, server, player, handler.read(buf)));
    }
}
