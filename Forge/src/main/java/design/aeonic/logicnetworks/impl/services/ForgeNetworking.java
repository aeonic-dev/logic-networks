package design.aeonic.logicnetworks.impl.services;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.networking.ClientboundPacketHandler;
import design.aeonic.logicnetworks.api.networking.ServerboundPacketHandler;
import design.aeonic.logicnetworks.api.services.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;

public class ForgeNetworking implements Networking {
    private final String protocolVersion = "1";
    public final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Constants.MOD_ID, "main"),
            () -> protocolVersion,
            protocolVersion::equals,
            protocolVersion::equals);

    @Override
    public <T> void sendClientboundPacket(ServerPlayer player, ResourceLocation channel, T packet) {
        this.channel.sendTo(packet, Objects.requireNonNull(player.connection.getConnection()), NetworkDirection.PLAY_TO_CLIENT);
    }

    @Override
    public <T> void sendServerboundPacket(ResourceLocation channel, T packet) {
        this.channel.sendToServer(packet);
    }

    @Override
    public <T> void registerClientboundPacketHandler(ClientboundPacketHandler<T> handler) {
        channel.registerMessage(handler.getChannelId().hashCode(), handler.getPacketClass(), handler::write, handler::read, (packet, ctx) -> handler.handle(ctx.get()::enqueueWork, Minecraft.getInstance(), packet));
    }

    @Override
    public <T> void registerServerboundPacketHandler(ServerboundPacketHandler<T> handler) {
        channel.registerMessage(handler.getChannelId().hashCode(), handler.getPacketClass(), handler::write, handler::read, (packet, ctx) -> handler.handle(ctx.get()::enqueueWork, Objects.requireNonNull(ctx.get().getSender()).getServer(), ctx.get().getSender(), packet));
    }
}
