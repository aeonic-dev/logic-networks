package design.aeonic.logicnetworks.api.networking;

import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public interface ClientboundPacketHandler<T> {

    default void sendToClient(ServerPlayer player, T packet) {
        Services.NETWORKING.sendClientboundPacket(player, getChannelId(), packet);
    }

    ResourceLocation getChannelId();

    Class<T> getPacketClass();

    void handle(Consumer<Runnable> taskQueue, Minecraft minecraft, T packet);

    void write(T object, FriendlyByteBuf buffer);

    T read(FriendlyByteBuf buffer);
}
