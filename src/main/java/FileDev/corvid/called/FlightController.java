package FileDev.corvid.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlightController {

    private static final Set<UUID> flyingPlayers = new HashSet<>();
    private static final Set<UUID> movingUp = new HashSet<>();
    private static final Set<UUID> movingDown = new HashSet<>();

    public static void controller() {
        FlightNetwork.registerServerHandler();
        registerTickManager();
        registerConnectionEvents();
    }

    public static void toggleFlight(ServerPlayerEntity player) {
        if (flyingPlayers.contains(player.getUuid())) {
            flyingPlayers.remove(player.getUuid());
            movingUp.remove(player.getUuid());
            movingDown.remove(player.getUuid());
        } else {
            flyingPlayers.add(player.getUuid());
        }

        System.out.println("[CORVID] Flight toggled for " + player.getName().getString());
    }

    public static void updateMovement(ServerPlayerEntity player, boolean up, boolean down) {
        if (up) movingUp.add(player.getUuid());
        else movingUp.remove(player.getUuid());

        if (down) movingDown.add(player.getUuid());
        else movingDown.remove(player.getUuid());
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
            movingUp.remove(handler.player.getUuid());
            movingDown.remove(handler.player.getUuid());
        });
    }

    private static void applyFlight(ServerPlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return;
        Vec3d vel = player.getVelocity();

        double xVel = vel.x;
        double yVel = vel.y;
        double zVel = vel.z;
if (!player.isOnGround()) {
    if (movingDown.contains(player.getUuid())) {
        yVel = -0.2;

        Vec3d look = player.getRotationVec(1.0f);
        double pitch = player.getPitch(1.0f);
        if (pitch >= 25) {
            yVel = -.4;
        }
        if (pitch >= 40) {
            yVel = -.6;
        }
        if (pitch >= 55) {
            yVel = -.8;
        }
        if (pitch >= 65) {
            yVel = -1.3;
        }
        if (pitch >= 75) {
            yVel = -1.7;
        }
        if (pitch >= 90) {
            yVel = -2.2;
        }
        xVel = look.x * 0.75;
        zVel = look.z * 0.75;
    }
}

        if (movingUp.contains(player.getUuid())) {
            if (!movingDown.contains(player.getUuid()))
                yVel = 1;
        }
            else yVel += 0.3;



        if (movingUp.contains(player.getUuid())){
            player.setVelocity(xVel, yVel, zVel);
            player.velocityModified = true;
        }
        else if (movingDown.contains(player.getUuid())){
            if(!player.isOnGround()) {
                player.setVelocity(xVel, yVel, zVel);
                player.velocityModified = true;
            }
        }
        player.fallDistance = 0;
    }
}
