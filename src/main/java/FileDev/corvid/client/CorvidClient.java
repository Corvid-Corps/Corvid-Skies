package FileDev.corvid.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import FileDev.corvid.called.FlightNetwork;

public class CorvidClient implements ClientModInitializer {

    private boolean lastUp = false;
    private boolean lastDown = false;

    @Override
    public void onInitializeClient() {
        FlightKeybindsClient.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (FlightKeybindsClient.TOGGLE_FLIGHT.wasPressed()) {
                ClientPlayNetworking.send(new FlightNetwork.ToggleFlightPayload());
            }

            while (FlightKeybindsClient.TOGGLE_FLIGHT_DASH.wasPressed()) {
                ClientPlayNetworking.send(new FlightNetwork.DashPayload());
            }

            boolean up = client.options.jumpKey.isPressed();
            boolean down = FlightKeybindsClient.TOGGLE_FLIGHT_DOWN.isPressed();

            if (up != lastUp || down != lastDown) {
                ClientPlayNetworking.send(new FlightNetwork.FlightMovePayload(up, down));
                lastUp = up;
                lastDown = down;
            }
        });
    }
}
