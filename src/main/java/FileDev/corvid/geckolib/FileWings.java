package FileDev.corvid.geckolib;

import net.minecraft.item.Item;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class FileWings extends Item implements GeoItem {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public FileWings(Properties properties) {
        super(properties);
    }
    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final Supplier<GeoItemRenderer<ExampleItem>> renderer = Suppliers.memoize(() -> new GeoItemRenderer<>(ExampleItem.this));

            @Override
            public @Nullable GeoItemRenderer<ExampleItem> getGeoItemRenderer() {
                return this.renderer.get();
            }
        });
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
