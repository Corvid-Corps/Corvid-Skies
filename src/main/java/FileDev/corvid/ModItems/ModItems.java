package FileDev.corvid.ModItems;

import FileDev.corvid.common.geckolib.FileWings;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class ModItems {
    private ModItems() {
    }

    public static Item THROWINGPOUCH;
    public static Item FILEWINGS;

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of("corvid", path));
        return Items.register(registryKey, factory, settings);
    }

    public static void registerItem() {
        THROWINGPOUCH = register("throwingpouch", Item::new, new Item.Settings().maxDamage(30).maxCount(1));
        FILEWINGS = register("filewings", FileWings::new, new Item.Settings().maxCount(1));
    }
}