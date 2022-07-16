package com.pinkulu.heightlimitmod.config;

import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class HeightLimitModConfig extends Config {

    @Checkbox(name = "test2")
    public static boolean uwu = false;

    @Switch(
            name = "Test",
            subcategory = "Test",
            size = 2
    )
    public static boolean test = true;

    public HeightLimitModConfig() {
        super(new Mod(HeightLimitMod.NAME, ModType.HYPIXEL, ""), HeightLimitMod.MODID + ".json");
    }
}

