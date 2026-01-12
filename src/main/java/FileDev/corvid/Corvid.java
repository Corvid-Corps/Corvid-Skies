package FileDev.corvid;

import FileDev.corvid.ModItems.ModItems;
import FileDev.corvid.called.FlightController;
import FileDev.corvid.called.ModEvents;
import FileDev.corvid.enchants.ModEnchants;
import net.fabricmc.api.ModInitializer;

public class Corvid implements ModInitializer {
    @Override
    public void onInitialize() {

    ModEnchants.register();
    ModItems.registerItem();
    ModEvents.init();
    FlightController.controller();
    System.out.println("[CORVID] Corvid Loaded");
    }

}


