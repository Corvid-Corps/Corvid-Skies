package FileDev.corvid.called;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModEvents {
    public static void init() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (player.getCommandTags().contains("dashing")) {
                    return false;
                }
            }
            return true;
        });
    }
}
