package FileDev.corvid.geckolib;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class FileWingsModel extends GeoModel<FileWings> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.of("corvid", "geckolib/models/filewings.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.of("corvid", "textures/item/bord.png");
    }

    @Override
    public Identifier getAnimationResource(FileWings animatable) {
        return Identifier.of("corvid", "geckolib/animations/filewings.animation.json");
    }
}
