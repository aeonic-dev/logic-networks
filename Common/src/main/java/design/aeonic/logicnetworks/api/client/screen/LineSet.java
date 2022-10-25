package design.aeonic.logicnetworks.api.client.screen;

import design.aeonic.logicnetworks.api.core.Constants;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.resources.ResourceLocation;

public class LineSet {
    public static LineSet DEFAULT = new LineSet();

    private static final Texture DEFAULT_LINE_EE_BG = new Texture("logicnetworks:textures/gui/graph/line1.png", 32, 32, 32, 3, 0, 9);
    private static final Texture DEFAULT_LINE_NN_BG = new Texture("logicnetworks:textures/gui/graph/line2.png", 32, 32, 3, 32, 0, 0);
    private static final Texture DEFAULT_LINE_ES_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 6, 0);
    private static final Texture DEFAULT_LINE_EN_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 6 , 6);
    private static final Texture DEFAULT_LINE_SE_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 0, 6);
    private static final Texture DEFAULT_LINE_NE_BG = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 0, 0);

    private static final Texture DEFAULT_LINE_EE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 32, 3, 0, 12);
    private static final Texture DEFAULT_LINE_NN = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 32, 32, 3, 32, 3, 0);
    private static final Texture DEFAULT_LINE_ES = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 15, 0);
    private static final Texture DEFAULT_LINE_EN = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 15 , 6);
    private static final Texture DEFAULT_LINE_SE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 9, 6);
    private static final Texture DEFAULT_LINE_NE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 3, 3, 9, 0);

    private LineSet() {}

    public Texture eastEast() {
        return DEFAULT_LINE_EE;
    }

    public Texture northNorth() {
        return DEFAULT_LINE_NN;
    }

    public Texture eastSouth() {
        return DEFAULT_LINE_ES;
    }

    public Texture eastNorth() {
        return DEFAULT_LINE_EN;
    }

    public Texture southEast() {
        return DEFAULT_LINE_SE;
    }

    public Texture northEast() {
        return DEFAULT_LINE_NE;
    }

    public Texture eastEastBackground() {
        return DEFAULT_LINE_EE_BG;
    }

    public Texture northNorthBackground() {
        return DEFAULT_LINE_NN_BG;
    }

    public Texture eastSouthBackground() {
        return DEFAULT_LINE_ES_BG;
    }

    public Texture eastNorthBackground() {
        return DEFAULT_LINE_EN_BG;
    }

    public Texture southEastBackground() {
        return DEFAULT_LINE_SE_BG;
    }

    public Texture northEastBackground() {
        return DEFAULT_LINE_NE_BG;
    }

    public static class ListLineSet extends LineSet {
        private static final Texture LIST_LINE_EE = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line1.png"), 32, 32, 32, 3, 0, 15);
        private static final Texture LIST_LINE_NN = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/gui/graph/line2.png"), 32, 32, 3, 32, 6, 0);

        @Override
        public Texture eastEast() {
            return LIST_LINE_EE;
        }

        @Override
        public Texture northNorth() {
            return LIST_LINE_NN;
        }
    }
}
