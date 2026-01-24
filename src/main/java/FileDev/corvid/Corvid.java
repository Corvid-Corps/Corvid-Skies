package FileDev.corvid;

import FileDev.corvid.ModItems.ModItems;
import FileDev.corvid.called.FlightController;
import FileDev.corvid.called.KnifeLogic;
import FileDev.corvid.called.ModEvents;
import net.fabricmc.api.ModInitializer;

public class Corvid implements ModInitializer {
    @Override
    public void onInitialize() {
        ModItems.registerItem();
    ModEvents.init();
    new KnifeLogic().init();
    FlightController.controller();
    System.out.println("[CORVID] Corvid Loaded");
    }
}


