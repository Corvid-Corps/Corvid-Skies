package FileDev.corvid.client;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CorvidClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }
}
