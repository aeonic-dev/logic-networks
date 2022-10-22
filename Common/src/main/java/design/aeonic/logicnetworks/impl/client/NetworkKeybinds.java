package design.aeonic.logicnetworks.impl.client;

import com.mojang.blaze3d.platform.InputConstants;
import design.aeonic.logicnetworks.api.util.Registrar;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class NetworkKeybinds {
    public static final KeyMapping ADD_NODE = new KeyMapping("key.logicnetworks.add_node", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_SPACE, "category.logicnetworks.graph");
    public static final KeyMapping DELETE_NODE = new KeyMapping("key.logicnetworks.delete_node", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DELETE, "category.logicnetworks.graph");

    public static void register(Registrar<KeyMapping> registrar) {
        registrar.accept(null, ADD_NODE);
        registrar.accept(null, DELETE_NODE);
    }
}
