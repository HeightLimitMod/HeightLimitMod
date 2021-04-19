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
            description = "Enables the Height Limit Mod."
    )
    public boolean heightLimitMod = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Update Notify",
            category = "General",
            description = "Notifies you when there's an update."
    )
    public boolean shouldNotifyUpdate = true;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Colour",
            category = "Render",
            description = "Selects the color for all HUD elements.",
            options = {"White", "Light Gray", "Gray", "Dark Gray", "Black", "Red", "Pink", "Orange", "Yellow", "Green", "Magenta", "Cyan", "Blue", "Chroma"}
    )
    public int heightLimitModColour = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Max Height",
            category = "Render",
            description = "Shows the maximum height of the map."
    )
    public boolean showMaxHeight = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Left",
            category = "Render",
            description = "Shows the amount of blocks you need to place upwards before you reach the built height limit."
    )
    public boolean showHeightLeft = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Map",
            category = "Render",
            description = "Shows the map you are on."
    )
    public boolean showMap = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Enable Shadow",
            category = "Render",
            description = "Make the HUD have a text shadow."
    )
    public boolean renderShadow;

    @Property(
            type = PropertyType.SWITCH,
            name = "Enable Background",
            category = "Render",
            description = "Make the HUD have a background."
    )
    public boolean displayBackground;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show in GUIs",
            category = "Render",
            description = "Make the HUD render in GUIs"
    )
    public boolean showInGui;

    @Property(
            type = PropertyType.SWITCH,
            name = "Play A Sound",
            category = "Sound",
            description = "Toggles the function to play a sound when you are near height limit."
    )
    public boolean shouldPlaySound = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sound",
            category = "Sound",
            description = "Chooses what sound you want to play.",
            options = {"Hypixel DING", "Golem Hit", "Blaze Hit", "Anvil Land", "Horse Death", "Ghast Scream", "Guardian Floop", "Cat Meow", "Dog Bark"}
    )
    public int soundToPlay = 0;

    @Property(
            type = PropertyType.SLIDER,
            name = "Notify Blocks",
            category = "Sound",
            description = "Allows you to customize how many blocks are left until the sound is played",
            max = 15
    )
    public int blocksWhenPlay = 10;

    @Property(
            type = PropertyType.SWITCH,
            name = "Spam The Sound",
            category = "Sound",
            description = "Makes the 'play a sound' feature spam the sound."
    )
    public boolean shouldSpamSound = false;

    public Config() {
        super(new File("./HeightLimitMod/config.toml"));
        initialize();
    }
}
