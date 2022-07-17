package com.pinkulu.heightlimitmod.events;

import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;

public class HeightLimitListener {
    private boolean shouldPlaySound;

    @Subscribe
    private void onTick(TickEvent event) {
        if (event.stage == Stage.START) {
            // do the stuff
        }
    }

}
