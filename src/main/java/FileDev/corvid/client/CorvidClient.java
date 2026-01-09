package FileDev.corvid.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import FileDev.corvid.called.FlightNetwork;

public class CorvidClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FlightKeybindsClient.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (FlightKeybindsClient.TOGGLE_FLIGHT.wasPressed()) {
                ClientPlayNetworking.send(new FlightNetwork.ToggleFlightPayload());
            }
        });
    }
}
