package FileDev.corvid;

import FileDev.corvid.called.FlightController;
import FileDev.corvid.called.ModEvents;
import net.fabricmc.api.ModInitializer;

public class Corvid implements ModInitializer {
    @Override
    public void onInitialize() {
    ModEvents.init();
    FlightController.controller();

    System.out.println("[CORVID] Corvid Loaded");
    }

}


