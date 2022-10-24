package design.aeonic.logicnetworks.impl.content.wrench;

import design.aeonic.logicnetworks.api.block.Wrenchable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class WrenchItem extends Item {
    public WrenchItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext $$0) {
        if ($$0.getLevel().getBlockState($$0.getClickedPos()).getBlock() instanceof Wrenchable block) {
            InteractionResult result = block.wrench($$0.getLevel(), $$0.getClickedPos(), $$0.getLevel().getBlockState($$0.getClickedPos()), $$0.getPlayer(), $$0.getHand());
            if (result.consumesAction()) return result;
        }
        return super.useOn($$0);
    }
}
