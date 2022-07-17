package com.pinkulu.heightlimitmod.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UDesktop;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import com.pinkulu.heightlimitmod.hud.BlocksTillMax;
import com.pinkulu.heightlimitmod.hud.CurrentMap;
import com.pinkulu.heightlimitmod.hud.MaxHeight;

import java.net.URI;

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
            "Horse Dies :(", "Ghast Yells", "Guardian Abuse", "Cat Meows","Dog Barks", "BOOOM!"},  category = "Sound")
    public static int soundToPlay = 0;

    @Checkbox(name = "Should Spam Sound", category = "Sound")
    public static boolean shouldSpamSound = false;


    @Button(
            name = "Pinkulus Webiste",
            text = "Click Here",
            category = "Information"
    )
    Runnable pinkulu = () -> {
        UDesktop.browse(URI.create("https://pinkulu.com"));
    };

    @Button(
            name = "Support Discord Server",
            text = "Click Here",
            category = "Information"
    )
    Runnable discord = () -> {
        UDesktop.browse(URI.create("https://inv.wtf/pink"));
    };

    @Button(
            name = "Block Height Overlay (hytils reborn by wyvest)",
            text = "Click Here",
            category = "Information"
    )
    Runnable hytils_reborn = () -> {
        UDesktop.browse(URI.create("https://github.com/Polyfrost/Hytils-Reborn/releases/tag/v1.5.0"));
    };
    public HeightLimitModConfig() {
        super(new Mod(HeightLimitMod.NAME, ModType.HYPIXEL), HeightLimitMod.MODID + ".json");
        initialize();
    }
}

