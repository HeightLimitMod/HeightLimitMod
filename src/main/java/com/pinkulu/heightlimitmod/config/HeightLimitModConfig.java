package com.pinkulu.heightlimitmod.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
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


    @Switch(name = "Should Play Sound", category = "Sound")
    public static boolean shouldPlaySound = false;

    @Slider(name = "Blocks Left To Play", min = 0, max = 5, category = "Sound")
    public static int blocksWhenPlay = 0;

    @Dropdown(name = "Sound To Play", options = {"Ding!", "Iron Golem Abuse","Blaze Abuse", "Anvil Land",
            "Horse Dies :(", "Ghast Yells", "Guardian Abuse", "Cat Meows","Dog Barks"},  category = "Sound")
    public static int soundToPlay = 0;

    @Checkbox(name = "Should Spam Sound", category = "Sound")
    public static boolean shouldSpamSound = false;


    public HeightLimitModConfig() {
        super(new Mod(HeightLimitMod.NAME, ModType.HYPIXEL), HeightLimitMod.MODID + ".json");
        initialize();
    }
}

