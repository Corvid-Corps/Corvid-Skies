package FileDev.corvid.client.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModel<PlayerEntityRenderState> {

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(
            method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V",
            at = @At("TAIL")
    )
    private void corvid$twoHandedSword(PlayerEntityRenderState state, CallbackInfo ci) {
        // Right hand (main hand)
        this.rightArm.pitch = -1.0F;  // Forward lift
        this.rightArm.yaw   = -0.5F;  // Slight inward
        this.rightArm.roll  = -0.3F;  // Tilt forward

        // Left hand (support hand)
        this.leftArm.pitch  = -1.0F;  // Forward lift
        this.leftArm.yaw    = 0.5F;   // Slight outward
        this.leftArm.roll   = 0.3F;   // Tilt backward
    }


}
