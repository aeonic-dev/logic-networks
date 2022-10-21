package design.aeonic.logicnetworks.impl.networking.container;

import design.aeonic.logicnetworks.api.networking.container.MovableSlot;
import net.minecraft.world.Container;

public class ForgeMovableSlot extends MovableSlot {
    public ForgeMovableSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
