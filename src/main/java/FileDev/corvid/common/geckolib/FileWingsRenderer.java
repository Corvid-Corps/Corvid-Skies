package FileDev.corvid.common.geckolib;

import software.bernie.geckolib.renderer.GeoItemRenderer;


public class FileWingsRenderer extends GeoItemRenderer<FileWings> {
    public FileWingsRenderer() {
        super(new FileWingsModel());
    }
}