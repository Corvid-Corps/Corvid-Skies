package FileDev.corvid.called;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
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

    public record FlightMovePayload(boolean up, boolean down) implements CustomPayload {
        public static final Id<FlightMovePayload> ID = new Id<>(Identifier.of("corvid", "flight_move"));
        public static final PacketCodec<RegistryByteBuf, FlightMovePayload> CODEC = PacketCodec.tuple(
                PacketCodecs.BOOLEAN, FlightMovePayload::up,
                PacketCodecs.BOOLEAN, FlightMovePayload::down,
                FlightMovePayload::new
        );

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public record DashPayload() implements CustomPayload {
        public static final Id<DashPayload> ID = new Id<>(Identifier.of("corvid", "dash"));
        public static final PacketCodec<RegistryByteBuf, DashPayload> CODEC = PacketCodec.unit(new DashPayload());

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    /** Server-only: register the handler for when the client sends the toggle packet */
    public static void registerServerHandler() {
        PayloadTypeRegistry.playC2S().register(ToggleFlightPayload.ID, ToggleFlightPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(FlightMovePayload.ID, FlightMovePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(DashPayload.ID, DashPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ToggleFlightPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    // Run flight toggle safely on the main server thread
                    player.getServer().submit(() -> FlightController.toggleFlight(player));
                });

        ServerPlayNetworking.registerGlobalReceiver(FlightMovePayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    player.getServer().submit(() -> FlightController.updateMovement(player, payload.up(), payload.down()));
                });

        ServerPlayNetworking.registerGlobalReceiver(DashPayload.ID,
                (payload, context) -> {
                    ServerPlayerEntity player = context.player();
                    player.getServer().submit(() -> FlightController.dash(player));
                });
    }
}
