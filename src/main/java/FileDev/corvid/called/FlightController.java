package FileDev.corvid.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class FlightController {

    private static final Set<UUID> flyingPlayers = new HashSet<>();
    private static final Set<UUID> movingUp = new HashSet<>();
    private static final Set<UUID> movingDown = new HashSet<>();
    private static final Map<UUID, Integer> dashCooldowns = new HashMap<>();
    private static final Map<UUID, Integer> dashTimers = new HashMap<>();


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
            // Dampen velocity to prevent "rapture" or sudden jumps
            Vec3d currentVel = player.getVelocity();
            player.setVelocity(currentVel.x * 0.5, 0, currentVel.z * 0.5);
            player.velocityModified = true;
        }

        System.out.println("[CORVID] Flight toggled for " + player.getName().getString());
    }

    public static void updateMovement(ServerPlayerEntity player, boolean up, boolean down) {
        if (up) movingUp.add(player.getUuid());
        else movingUp.remove(player.getUuid());

        if (down) movingDown.add(player.getUuid());
        else movingDown.remove(player.getUuid());
    }

    public static void dash(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        if (!flyingPlayers.contains(uuid)) return;
        int cooldown = dashCooldowns.getOrDefault(uuid, 0);
        if (cooldown <= 0) {
            if (!movingDown.contains(uuid)) {
                Vec3d look = player.getRotationVec(1.0f);
                player.setVelocity(look.x * 3, look.y * 3, look.z * 3);
                player.velocityModified = true;
                dashCooldowns.put(uuid, 140);
                dashTimers.put(uuid, 20);
                player.addCommandTag("dashing");
            }
        }
    }


    private static void registerTickManager() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                UUID uuid = player.getUuid();

                if (flyingPlayers.contains(uuid)) {
                    applyFlight(player);


                    if (player.getCommandTags().contains("dashing")) {
                        List<Entity> collidingEntities = player.getWorld().getOtherEntities(player, player.getBoundingBox());
                        if (!collidingEntities.isEmpty()) {
                            for (Entity entity : collidingEntities) {
                                entity.damage((ServerWorld) player.getWorld(), player.getWorld().getDamageSources().flyIntoWall(), 5.0f);
                            }

                            Vec3d look = player.getRotationVec(1.0f);
                            player.setVelocity(-look.x * 1.5, 1.5, -look.z * 1.5);
                            player.velocityModified = true;
                            dashCooldowns.put(uuid, 20);
                            dashTimers.put(uuid, 0);
                            player.getCommandTags().remove("dashing");
                        }
                    }
                }
                
                int cooldown = dashCooldowns.getOrDefault(uuid, 0);
                if (cooldown > 0) {
                    dashCooldowns.put(uuid, cooldown - 1);
                }

                int dashTimer = dashTimers.getOrDefault(uuid, 0);
                if (dashTimer > 0) {
                    dashTimers.put(uuid, dashTimer - 1);
                    if (dashTimer == 1) {
                        player.getCommandTags().remove("dashing");
                    }
                }
            }
        });
    }

    private static void registerConnectionEvents() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            UUID uuid = handler.player.getUuid();
            flyingPlayers.remove(uuid);
            movingUp.remove(uuid);
            movingDown.remove(uuid);
            dashCooldowns.remove(uuid);
            dashTimers.remove(uuid);
        });
    }

    private static void applyFlight(ServerPlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) return;

        UUID uuid = player.getUuid();
        Vec3d look = player.getRotationVec(1.0f);
        double xVel, yVel, zVel;

        if (movingUp.contains(uuid)) {
            // Ascending
            yVel = 0.4;
            xVel = look.x * 0.5;
            zVel = look.z * 0.5;
            player.setVelocity(xVel, yVel, zVel);
            player.velocityModified = true;
        } else if (movingDown.contains(uuid)) {
            if (!player.isOnGround()) {
// test
                yVel = -0.2;
                double pitch = player.getPitch(1.0f);
                if (pitch >= 25) yVel = player.getVelocity().y *-0.4;
                if (pitch >= 40) yVel = player.getVelocity().y *-0.6;
                if (pitch >= 55) yVel = player.getVelocity().y *-0.8;
                if (pitch >= 65) yVel = player.getVelocity().y *-1.3;
                if (pitch >= 75) yVel = player.getVelocity().y *-1.7;
                if (pitch >= 90) yVel = player.getVelocity().y * -2.2;


                xVel = look.x * 0.5;
                if(pitch > 0)
                xVel = look.x * pitch * 0.08;
                zVel = look.z * pitch * 0.08 ;

                player.setVelocity(xVel, yVel, zVel);
                player.velocityModified = true;
            }
        }


        player.fallDistance = 0;
    }
}
