package design.aeonic.logicnetworks.api.block;

import design.aeonic.logicnetworks.api.data.NetworkTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

/**
 * Interface for block entities that can be hidden with a facade.
 */
public interface FacadeHideable {
    @Nullable
    BlockState getFacade();

    void setFacade(BlockState facade);

    static InteractionResult tryPutFacade(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (level.getBlockEntity(pos) instanceof FacadeHideable be) {
            return be.tryPutFacade(level, pos, player, hand, res);
        }
        return InteractionResult.FAIL;
    }

    static InteractionResult tryRemoveFacade(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (level.getBlockEntity(pos) instanceof FacadeHideable be) {
            return be.tryRemoveFacade(level, pos, player, hand, res);
        }
        return InteractionResult.FAIL;
    }


    /**
     * Tries to set the block entity's facade to the block state of the item stack. Returns
     * the correct interaction result to return from {@link net.minecraft.world.level.block.Block#use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult)}.
     */
    default InteractionResult tryPutFacade(Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
            BlockPlaceContext ctx = blockItem.updatePlacementContext(new BlockPlaceContext(player, hand, stack, res));
            if (ctx != null) {
                BlockState facadeState = blockItem.getBlock().getStateForPlacement(ctx);
                if (facadeState != null && !facadeState.is(NetworkTags.Blocks.FACADE_BLACKLIST)) {
                    level.playSound(player, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, .5f, 1);
                    level.playSound(player, pos, facadeState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, .5f, 1);
                    setFacade(facadeState);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
        return InteractionResult.FAIL;
    }

    default InteractionResult tryRemoveFacade(Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (getFacade() != null) {
            if (player.isCrouching() && player.getItemInHand(hand).isEmpty()) {
                level.playSound(player, pos, SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.BLOCKS, .5f, 1);
                level.playSound(player, pos, getFacade().getSoundType().getBreakSound(), SoundSource.BLOCKS, .5f, 1);
                setFacade(null);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.FAIL;
    }
}
