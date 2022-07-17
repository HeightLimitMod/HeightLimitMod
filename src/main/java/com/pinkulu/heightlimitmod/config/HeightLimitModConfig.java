package com.pinkulu.heightlimitmod.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.HUD;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import com.pinkulu.heightlimitmod.hud.BlocksTillMax;
import com.pinkulu.heightlimitmod.hud.CurrentMap;
import com.pinkulu.heightlimitmod.hud.MaxHeight;

public class HeightLimitModConfig extends Config {

    @HUD(
            name = "Blocks Till Max Limit",
            category = "General"
    )
    public static BlocksTillMax btm = new BlocksTillMax();

    @HUD(
            name = "Map Name",
            category = "General"
    )
    public static CurrentMap cm = new CurrentMap();

    @HUD(
            name = "Max Map Height",
            category = "General"
    )
    public static MaxHeight mh = new MaxHeight();



    public HeightLimitModConfig() {
        super(new Mod(HeightLimitMod.NAME, ModType.HYPIXEL), HeightLimitMod.MODID + ".json");
        initialize();
    }
}

