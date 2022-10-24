package design.aeonic.logicnetworks.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface Wrenchable {
    InteractionResult wrench(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand);
}
