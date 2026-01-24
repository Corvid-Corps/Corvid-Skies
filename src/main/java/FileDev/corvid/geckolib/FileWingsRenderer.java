package FileDev.corvid.geckolib;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FileWingsRenderer extends GeoItemRenderer<FileWings> {
    public FileWingsRenderer() {
        super(new FileWingsModel());
    }
}