package design.aeonic.logicnetworks.impl.content.link;

import design.aeonic.logicnetworks.api.core.Translations;
import design.aeonic.logicnetworks.api.logic.LinkCard;
import design.aeonic.logicnetworks.api.logic.LinkStatus;
import design.aeonic.logicnetworks.impl.content.NetworkItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LinkingCardItem extends Item implements LinkCard {
    private int checkTicker;

    public LinkingCardItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(BlockPos pos, Direction side) {
        ItemStack stack = NetworkItems.LINKING_CARD.getDefaultInstance();
        CompoundTag linkTag = new CompoundTag();
        linkTag.putLong("Pos", pos.asLong());
        linkTag.putString("Side", side.getName());
        stack.getOrCreateTag().put("Link", linkTag);
        stack.getOrCreateTag().putInt("Status", LinkStatus.VALID.ordinal());
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack $$0, Level $$1, Entity $$2, int $$3, boolean $$4) {
        if (++checkTicker % 100 <= 5 && $$2 instanceof Player) {
            checkStatus($$1, $$0);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        BlockPos link = getLink(stack);
        Direction side = getDirection(stack);
        tooltip.add(Translations.Link.LINKED_TO.get(posToString(link)));
        tooltip.add(Translations.Link.LINKED_SIDE.get(side == null ? "" : side.getName()));
        tooltip.add(Translations.Link.status(getLinkStatus(stack)));
    }

    public String posToString(@Nullable BlockPos pos) {
        return pos == null ? "[]" : "[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "]";
    }

    @Override
    public void checkStatus(Level level, ItemStack stack) {
        if (level.isClientSide) return;

        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Link", Tag.TAG_COMPOUND)) {
            CompoundTag link = tag.getCompound("Link");
            if (link.contains("Pos", Tag.TAG_LONG)) {
                BlockPos pos = BlockPos.of(link.getLong("Pos"));
                LinkStatus status = LinkStatus.get(level, pos);
                tag.putInt("Status", status.ordinal());
            } else {
                tag.putInt("Status", LinkStatus.INVALID.ordinal());
            }
        } else {
            tag.putInt("Status", LinkStatus.INVALID.ordinal());
        }
    }

    @Nullable
    @Override
    public BlockPos getLink(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Link", Tag.TAG_COMPOUND)) {
            CompoundTag link = tag.getCompound("Link");
            if (link.contains("Pos", Tag.TAG_LONG)) {
                return BlockPos.of(link.getLong("Pos"));
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Direction getDirection(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Link", Tag.TAG_COMPOUND)) {
            CompoundTag link = tag.getCompound("Link");
            if (link.contains("Side", Tag.TAG_STRING)) {
                return Direction.byName(link.getString("Side"));
            }
        }
        return null;
    }

    @Override
    public LinkStatus getLinkStatus(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Status", Tag.TAG_INT)) {
            return LinkStatus.values()[tag.getInt("Status")];
        }
        return LinkStatus.INVALID;
    }
}
