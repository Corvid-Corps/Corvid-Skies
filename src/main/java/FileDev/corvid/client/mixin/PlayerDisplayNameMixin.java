package FileDev.corvid.client.mixin;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin(PlayerEntity.class)
public abstract class PlayerDisplayNameMixin {

    @Inject(
            method = "getDisplayName",
            at = @At("HEAD"),
            cancellable = true
    )
    private void filewings$overrideDisplayName(CallbackInfoReturnable<Text> cir) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        // ONLY client-side
        if (!player.getWorld().isClient()) return;
        cir.setReturnValue(Text.literal("§dWing Lord"));

    }
}