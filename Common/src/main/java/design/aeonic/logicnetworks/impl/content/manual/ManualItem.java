package design.aeonic.logicnetworks.impl.content.manual;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nonnull;

public class ManualItem extends Item {
    private static final ResourceLocation bookId = new ResourceLocation("logicnetworks", "manual");

    public ManualItem(Properties props) {
        super(props);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player instanceof ServerPlayer serverPlayer) {
            PatchouliAPI.get().openBookGUI(serverPlayer, bookId);
            player.playSound(SoundEvents.BOOK_PAGE_TURN, 1, 1);
        }
        return InteractionResultHolder.success(stack);
    }

    public static float getIsOpenPropety() {
        return bookId.equals(PatchouliAPI.get().getOpenBookGui()) ? 1 : 0;
    }
}
