package design.aeonic.logicnetworks.api.networking;

import design.aeonic.logicnetworks.impl.services.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public interface ServerboundPacketHandler<T> {

    default void sendToServer(T packet) {
        Services.NETWORKING.sendServerboundPacket(getChannelId(), packet);
    }

    ResourceLocation getChannelId();

    Class<T> getPacketClass();

    void handle(Consumer<Runnable> taskQueue, MinecraftServer server, ServerPlayer player, T packet);

    void write(T object, FriendlyByteBuf buffer);

    T read(FriendlyByteBuf buffer);
}
