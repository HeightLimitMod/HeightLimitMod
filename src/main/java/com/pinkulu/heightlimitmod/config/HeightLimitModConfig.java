package com.pinkulu.heightlimitmod.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneColor;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UDesktop;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.hud.*;

import java.net.URI;

public class HeightLimitModConfig extends Config {

    @Switch(
            name = "Enable Height Overlay",
            category = "General",
            description = "show an overlay if a block is placed at the height limit"
    )
    public static boolean enableHeightOverlay = true;

    @Color(
            name = "Height Overlay Color",
            category = "General"
    )
    public static OneColor heightOverlayColor = new OneColor(255, 0, 128, 144);

    @Switch(
            name = "Dynamic Limit Calculation",
            category = "General",
            description = "Calculate the actual height limit of the map, as its a sphere, and not always static"
    )
    public static boolean dynamicLimit = true;

    @HUD(
            name = "Max Map Height",
            category = "Max Map Height"
    )
    public static MaxHeight mh = new MaxHeight();

    @HUD(
            name = "Blocks Left",
            category = "Blocks Left"
    )
    public static BlocksTillMax btm = new BlocksTillMax();

    @HUD(
            name = "Map Name",
            category = "Map Name"
    )
    public static CurrentMap cm = new CurrentMap();


    @HUD(
            name = "Map Type",
            category = "Map Type"
    )
    public static MapType mt = new MapType();

    @HUD(
            name = "Game Mode",
            category = "Game Mode"
    )
    public static GameMode gt = new GameMode();

    @HUD(
            name = "Game Type",
            category = "Game Type"
    )
    public static GameType gm = new GameType();

    @Switch(name = "Should Play Sound", category = "Sound")
    public static boolean shouldPlaySound = false;

    @Slider(name = "Blocks Left To Play", min = 0, max = 5, category = "Sound")
    public static int blocksWhenPlay = 0;

    @Dropdown(name = "Sound To Play", options = {"Ding!", "Iron Golem Abuse", "Blaze Abuse", "Anvil Land",
            "Horse Dies :(", "Ghast Yells", "Guardian Abuse", "Cat Meows", "Dog Barks", "BOOOM!", "Custom Sound(Advanced)"}, category = "Sound")
    public static int soundToPlay = 0;
    @Checkbox(name = "Should Spam Sound", category = "Sound")
    public static boolean shouldSpamSound = false;
    @Info(text = "For custom sounds, go to \"Sound List\" in Information tab", type = InfoType.INFO, category = "Sound")
    public static String info;
    @Info(text = "Always check the \"Test Sound\" button, after setting a custom sound", type = InfoType.WARNING, category = "Sound")
    public static String info2;
    @Text(
            name = "Custom Sound",
            category = "Sound",
            placeholder = "example: dig.glass"
    )
    public static String soundName;
    @Button(
            name = "Test Sound",
            text = "Click Here",
            category = "Sound"
    )
    Runnable testSound = HeightLimitListener::PlaySound;
    @Button(
            name = "Pinkulu's Website",
            text = "Click Here",
            category = "Information"
    )
    Runnable pinkulu = () -> UDesktop.browse(URI.create("https://pinkulu.com"));


    @Button(
            name = "Support Discord Server",
            text = "Click Here",
            category = "Information"
    )
    Runnable discord = () -> UDesktop.browse(URI.create("https://inv.wtf/pink"));

    @Button(
            name = "Block Height Overlay (Hytils Reborn by Polyfrost)",
            text = "Click Here",
            category = "Information"
    )
    Runnable hytils_reborn = () ->
            UDesktop.browse(URI.create("https://github.com/Polyfrost/Hytils-Reborn/releases/"));


    @Button(
            name = "Sound List",
            text = "Click Here",
            category = "Information"
    )
    Runnable soundlist = () ->
            UDesktop.browse(URI.create("https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments"));


    public HeightLimitModConfig() {
        super(new Mod(HeightLimitMod.NAME, ModType.HYPIXEL), HeightLimitMod.MODID + ".json");
        initialize();
    }
}