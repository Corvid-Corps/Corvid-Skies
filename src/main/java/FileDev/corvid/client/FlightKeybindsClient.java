package FileDev.corvid.client;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding;

public class FlightKeybindsClient {
    public static KeyBinding TOGGLE_FLIGHT;
    public static KeyBinding TOGGLE_FLIGHT_DOWN;
    public static KeyBinding TOGGLE_FLIGHT_DASH;

    public static void register() {
        TOGGLE_FLIGHT = registerKeyBinding(new KeyBinding(
                "key.corvid.toggle_flight",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                "category.corvid.controls"
        ));
        TOGGLE_FLIGHT_DOWN = registerKeyBinding(new KeyBinding(
                "key.corvid.toggle_flight_down",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.corvid.controls"
        ));
        TOGGLE_FLIGHT_DASH = registerKeyBinding(new KeyBinding(
                "key.corvid.toggle_flight_dash",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.corvid.controls"
        ));
    }
}
