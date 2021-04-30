package com.pinkulu.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;
@SuppressWarnings("FieldMayBeFinal")
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
            description = "Notifys you when theres an update."
    )
    public static boolean shouldNotifyUpdate = true;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Colour",
            category = "Render",
            description = "Selects the color for all HUD elements.",
            options = {"White", "Light Gray", "Gray", "Dark Gray", "Black", "Red", "Pink", "Orange", "Yellow", "Green", "Magenta", "Cyan", "Blue", "Chroma"}
    )
    public static int heightLimitModColour = 0;

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
            description = "WARNING: This Will Spam The Sound, Making It Supper Loud, And Play A lot Of Time."
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

    public Config() {
        super(new File("./HeightLimitMod/config.toml"));
        initialize();
    }
}
