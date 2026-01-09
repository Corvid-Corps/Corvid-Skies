package FileDev.corvid.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlightController {

    private static final Set<UUID> flyingPlayers = new HashSet<>();

    public static void controller() {
        FlightNetwork.registerServerHandler();
        registerTickManager();
        registerConnectionEvents();
    }

    public static void toggleFlight(ServerPlayerEntity player) {
        if (flyingPlayers.contains(player.getUuid())) flyingPlayers.remove(player.getUuid());
        else flyingPlayers.add(player.getUuid());

        System.out.println("[CORVID] Flight toggled for " + player.getName().getString());
    }

    private static void registerTickManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (flyingPlayers.contains(player.getUuid())) applyFlight(player);
            }
        });
    }

    private static void registerConnectionEvents() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            flyingPlayers.remove(handler.player.getUuid());
        });
    }

    private static void applyFlight(ServerPlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return;
        Vec3d vel = player.getVelocity();

        double yVel = player.getVelocity().y;
        if (player.isHolding(Items.NETHER_STAR)) {
            yVel = -0.2;
        }

        if(player.isHolding(Items.BEACON)) {
            yVel = 0.5;
        }


        player.setVelocity(vel.x, yVel, vel.z);
        player.velocityModified = true;
        player.fallDistance = 0;
    }
}
