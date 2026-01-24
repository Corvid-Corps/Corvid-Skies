package FileDev.corvid.called;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.ActionResult;

public class KnifeLogic {
    public void init() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!world.isClient) {
                ArrowEntity arrow = new ArrowEntity(EntityType.ARROW, world);
                arrow.setOwner(player);
                arrow.setVelocity(player.getRotationVector().multiply(2));
                world.spawnEntity(arrow);
            }

            return ActionResult.PASS;
        });
    }
}