package design.aeonic.logicnetworks.api.client.screen.input.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import design.aeonic.logicnetworks.api.core.CommonRegistries;
import design.aeonic.logicnetworks.api.logic.network.NodeType;
import design.aeonic.logicnetworks.api.logic.network.SignalType;
import design.aeonic.logicnetworks.api.logic.network.node.Node;
import design.aeonic.logicnetworks.api.client.screen.input.WidgetScreen;
import design.aeonic.logicnetworks.api.util.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class NodeSearchWidget extends StringInputWidget {
    // TODO: Generally pretty bad implementation but whatever it works for now
    public static final Texture BOX = new Texture("logicnetworks:textures/gui/node_search_box.png", 128, 128, 96, 97);
    public static final Texture OPTION = new Texture("logicnetworks:textures/gui/node_search_box.png", 128, 128, 86, 12, 0, 104);
    public static final Texture OPTION_HOVERED = new Texture("logicnetworks:textures/gui/node_search_box.png", 128, 128, 86, 12, 0, 116);

    private static NodeSearchWidget instance = null;
    private static Map<String, NodeType<?>> nodeTypes;
    private static Map<NodeType<?>, Node<?>> nodeInstances;

    private final SelectCallback callback;
    private final Predicate<NodeType<?>> filter;
    private List<Map.Entry<String, NodeType<?>>> results;

    private int selected = 0;

    private NodeSearchWidget(int x, int y, int maxLength, SelectCallback callback, Predicate<NodeType<?>> filter) {
        super(x, y, maxLength, "");
        this.callback = callback;
        this.filter = filter;

        results = search(getValue());
    }

    public static NodeSearchWidget createForInput(WidgetScreen screen, int x, int y, SelectCallback callback, SignalType<?> inputType) {
        setup();
        return create(screen, x, y, callback, type -> Arrays.stream(nodeInstances.get(type).getInputSlots()).anyMatch(
                inputType::canConnect));
    }

    public static NodeSearchWidget createForOutput(WidgetScreen screen, int x, int y, SelectCallback callback, SignalType<?> outputType) {
        setup();
        return create(screen, x, y, callback, type -> Arrays.stream(nodeInstances.get(type).getOutputSlots()).anyMatch(
                outputType::canConnect));
    }

    public static NodeSearchWidget create(WidgetScreen screen, int x, int y, SelectCallback callback, Predicate<NodeType<?>> filter) {
        setup();
        if (instance != null) {
            instance.callback.onSelect(null);
            screen.removeWidget(instance);
        }
        instance = new NodeSearchWidget(x, y, 18, callback, filter);
        screen.setFocus(instance);
        return instance;
    }

    private static void setup() {
        if (nodeTypes == null) {
            nodeTypes = new HashMap<>();
            nodeInstances = new HashMap<>();
            UUID dummy = UUID.randomUUID();
            for (NodeType<?> type : CommonRegistries.NODE_TYPES.getValues()) {
                var node = type.createNode(dummy, 0, 0);
                nodeTypes.put(node.getName().getString(), type);
                nodeInstances.put(type, node);
            }
        }
    }

    public static NodeSearchWidget getInstance() {
        return instance;
    }

    @Override
    public void onLostFocus(WidgetScreen screen) {
        close(screen, true);
    }

    @Override
    public boolean mouseDown(WidgetScreen screen, int mouseX, int mouseY, int button) {
        if (button == 0) {
            if (mouseX >= getX() + 5 && mouseX <= getX() + getWidth() - 5) {
                if (mouseY >= getY() + 5 && mouseY <= getY() + 17) return super.mouseDown(screen, mouseX, mouseY, button);
                else if (mouseY >= getY() + 20 && mouseY <= getY() + 20 + Math.min(results.size(), 6) * 12) {
                    int index = (mouseY - (getY() + 20)) / 12;
                    callback.onSelect(results.get(index).getValue());
                    screen.clearFocus(this);
                    close(screen, false);
                    return true;
                }
            }
        }
        return false;
    }

    public void close(WidgetScreen screen) {
        close(screen, true);
    }

    private void close(WidgetScreen screen, boolean canceled) {
        if (canceled) callback.onSelect(null);
        screen.removeWidget(this);
        if (instance == this) instance = null;
    }

    @Override
    public boolean keyDown(WidgetScreen screen, int keyCode, int scanCode, int modifiers) {
        selected = Mth.clamp(selected, 0, results.size() - 1);
        if (keyCode == GLFW.GLFW_KEY_UP) {
            selected = Mth.clamp(selected - 1, 0, results.size() - 1);
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_DOWN) {
            selected = Mth.clamp(selected + 1, 0, results.size() - 1);
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            if (results.size() > 0) {
                callback.onSelect(results.get(selected).getValue());
                screen.clearFocus(this);
                screen.removeWidget(this);
                if (instance == this) instance = null;
                return true;
            }
        }
        if (super.keyDown(screen, keyCode, scanCode, modifiers)) {
            results = search(getValue());
            return true;
        }
        return false;
    }

    @Override
    public void draw(PoseStack stack, WidgetScreen screen, int mouseX, int mouseY, float partialTicks) {
        if (selectionStart != -1) {
            selectionStart = Mth.clamp(selectionStart, 0, value.length());
            selectionEnd = Mth.clamp(selectionEnd, 0, value.length());
        }

        BOX.draw(stack, getX(), getY(), 0);
        Minecraft.getInstance().font.draw(stack, value, getX() + 8, getY() + 7, 0xFFFFFF);
        if (selectionStart != -1) {
            HIGHLIGHT.draw(stack, getX() + 8 + Minecraft.getInstance().font.width(value.substring(0, selectionStart)), getY() + 7, 0, Minecraft.getInstance().font.width(value.substring(selectionStart, selectionEnd)), Minecraft.getInstance().font.lineHeight, 1, 1, 1, 1, false);
            Minecraft.getInstance().font.draw(stack, value.substring(selectionStart, selectionEnd), getX() + 8 + Minecraft.getInstance().font.width(value.substring(0, selectionStart)), getY() + 7, 0x3F3F3F);
        } else {
            CURSOR.draw(stack, getX() + 5 + Minecraft.getInstance().font.width(value.substring(0, cursor)), getY() + 5 + Minecraft.getInstance().font.lineHeight, 0, 1, 1, 1, 1);
        }

        if (results == null) results = search(getValue());
        selected = Math.min(selected, results.size() - 1);
        int hovered = getHovered(mouseX, mouseY);
        for (int i = 0; i < Math.min(results.size(), 6); i++) {
            var entry = results.get(i);
            (hovered == i ? OPTION_HOVERED : OPTION).draw(stack, getX() + 5, getY() + 20 + i * 12, 0);
            Minecraft.getInstance().font.draw(stack, entry.getKey(), getX() + 8, getY() + 20 + i * 12 + 2, 0x3F3F3F);
        }
    }

    public int getHovered(int mouseX, int mouseY) {
        if (mouseX >= getX() + 5 && mouseX <= getX() + getWidth() - 5) {
            if (mouseY >= getY() + 20 && mouseY <= getY() + 19 + Math.min(results.size(), 6) * 12) {
                return (mouseY - (getY() + 20)) / 12;
            }
        }
        return selected;
    }

    public List<Map.Entry<String, NodeType<?>>> search(String query) {
        String lower = query.toLowerCase();
        return nodeTypes.entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().contains(lower) && filter.test(entry.getValue()))
                .sorted((m, n) -> compare(m, n, lower))
                .toList();
    }

    private int compare(Map.Entry<String, ?> m, Map.Entry<String, ?> n, String query) {
        if (m.getKey().replace(" ", "").equals(query.replace(" ", ""))) return 1;
        return m.getKey().compareTo(n.getKey());
    }

    @FunctionalInterface
    public interface SelectCallback {
        void onSelect(@Nullable NodeType<?> nodeType);
    }

    @Override
    public int getWidth() {
        return 96;
    }

    @Override
    public int getHeight() {
        return 97;
    }
}
