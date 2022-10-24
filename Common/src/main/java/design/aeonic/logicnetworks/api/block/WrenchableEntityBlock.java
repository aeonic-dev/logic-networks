package design.aeonic.logicnetworks.api.block;

import design.aeonic.logicnetworks.api.data.NetworkTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class WrenchableEntityBlock extends BaseEntityBlock implements Wrenchable{
    protected WrenchableEntityBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
        if ($$3.getItemInHand($$4).is(NetworkTags.Items.WRENCH)) {
            InteractionResult result = wrench($$1, $$2, $$0, $$3, $$4);
            if (result.consumesAction()) return result;
        }
        return super.use($$0, $$1, $$2, $$3, $$4, $$5);
    }

    @Override
    public InteractionResult wrench(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (level.isClientSide) return InteractionResult.SUCCESS;
            level.destroyBlock(pos, true, player);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }
}
