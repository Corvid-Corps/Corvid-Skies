package FileDev.corvid.called;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FlightNetwork {

    public record ToggleFlightPayload() implements CustomPayload {
        public static final Id<ToggleFlightPayload> ID = new Id<>(Identifier.of("corvid", "toggle_flight"));
        public static final PacketCodec<RegistryByteBuf, ToggleFlightPayload> CODEC = PacketCodec.unit(new ToggleFlightPayload());

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    /** Server-only: register the handler for when the client sends the toggle packet */
    public static void registerServerHandler() {
        PayloadTypeRegistry.playC2S().register(ToggleFlightPayload.ID, ToggleFlightPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ToggleFlightPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    // Run flight toggle safely on the main server thread
                    player.getServer().submit(() -> FlightController.toggleFlight(player));
                });
    }
}
