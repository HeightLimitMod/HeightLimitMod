package com.pinkulu.hlm.config;

import com.pinkulu.hlm.HeightLimitMod;
import com.pinkulu.hlm.util.DelayedTask;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.UDesktop;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class Config extends Vigilant {
    @Property(
            type = PropertyType.SWITCH,
            name = "Height Limit Mod",
            category = "General",
            subcategory = "General",
            description = "Enable Height Limit Mod."
    )
    public static boolean heightLimitMod = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Update Notify",
            category = "General",
            subcategory = "General",
            description = "Notifies you when theres an update."
    )
    public static boolean shouldNotifyUpdate = true;
    @Property(
            type = PropertyType.BUTTON,
            name = "GUI Location",
            category = "General",
            subcategory = "General",
            description = "Opens a screen that lets you drag where you want to put the gui elements."
    )
    public static void openGui(){
        EssentialAPI.getGuiUtil().openScreen(null);
        new DelayedTask(HeightLimitMod.api::openConfigScreen, 1);
    }
    @Property(
            type = PropertyType.SWITCH,
            name = "Chroma",
            category = "Render",
            description = "Renders the text with a rainbow effect"
    )
    public static boolean rgb;

    @Property(
            type = PropertyType.COLOR,
            name = "Colour",
            category = "Render",
            description = "Selects the color for all HUD elements.",
            allowAlpha = false
    )
    public static Color heightLimitModTextColour = Color.WHITE;

    @Property(
            type = PropertyType.SWITCH,
            name = "Max Height",
            category = "Render",
            description = "Shows The Max Height Of the Map"
    )
    public static boolean showMaxHeight = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Left",
            category = "Render",
            description = "Shows The Height Left Till Max Height."
    )
    public static boolean showHeightLeft = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Map",
            category = "Render",
            description = "Shows The Map You Are On."
    )
    public static boolean showMap = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Play A Sound",
            category = "Sound",
            description = "Should A Sound Be Played When You`re Near The Block Limit."
    )
    public static boolean shouldPlaySound = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sound",
            category = "Sound",
            description = "Choose What Sound You Want To Play",
            options = {"Hypixel DING", "Golem Hit", "Blaze Hit", "Anvil Land", "Horse Death", "Ghast Scream", "Guardian Floop", "Cat Meow", "Dog Bark"}
    )
    public static int soundToPlay = 0;

    @Property(
            type = PropertyType.SLIDER,
            name = "Notify Blocks",
            category = "Sound",
            description = "How many blocks left until the sound is played",
            max = 15
    )
    public static int blocksWhenPlay = 10;
    @Property(
            type = PropertyType.SWITCH,
            name = "Spam The Sound",
            category = "Sound",
            description = "WARNING: This Will Spam The Sound, Making It Super Loud, And Play A lot Of Time."
    )
    public static boolean shouldSpamSound = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Display Background",
            category = "Render",
            description = "Display the background, what else."
    )
    public static boolean displayBackground;

    @Property(
            type = PropertyType.COLOR,
            name = "Display Background Color",
            category = "Render",
            description = "Choose the color for the display background."
    )
    public static Color backgroundColor = new Color(0, 0, 0, 128);

    @Property(
            type = PropertyType.SWITCH,
            name = "Render Shadow",
            category = "Render",
            description = "Render the shadow."
    )
    public static boolean renderShadow;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show in GUIs",
            category = "Render",
            description = "Show in GUIS."
    )
    public static boolean showInGui;

    @Property(
            type = PropertyType.BUTTON,
            name = "My website",
            category = "Information",
            description = "The coolest website on the planet"
    )
    public static void openWebsite() {
        UDesktop.browse(URI.create("https://pinkulu.com"));
    }
    @Property(
            type = PropertyType.BUTTON,
            name = "Support Discord Server",
            category = "Information",
            description = "Are you having any issues with the mod? Join the support discord server :)"
    )
    public static void openDiscord() {
        UDesktop.browse(URI.create("https://discord.gg/Fykpshg"));
    }
    @Property(
            type = PropertyType.BUTTON,
            name = "GUI Location",
            category = "Information",
            description = "Opens a screen that lets you drag where you want to put the gui elements"
    )
    public static void openGUIInfo(){
        Minecraft.getMinecraft().thePlayer.closeScreen();
        ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/hlm gui");
    }
    @Property(
            type = PropertyType.BUTTON,
            name = "Height Limit Web",
            category = "Information",
            description = "Need to use height limit mod on a client like lunar or badlion? well fear no more, as HLW " +
                    "gots you, search all the bedwars maps in your browser"
    )
    public static void openPinkuluHLW() {
        UDesktop.browse(URI.create("https://pinkulu.com/HeightLimitWeb"));
    }

    public Config() {
        super(new File("./config/HeightLimitMod/config.toml"));
        initialize();
    }
}