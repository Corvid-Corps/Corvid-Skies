package FileDev.corvid.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FlightKeybindsClient {
    public static KeyBinding TOGGLE_FLIGHT;

    public static void register() {
        TOGGLE_FLIGHT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.corvid.toggle_flight",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "category.corvid.controls"
        ));
    }
}
