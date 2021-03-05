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
            description = "Enables the Height Limit Mod."
    )
    public boolean heightLimitMod = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Update Notify",
            category = "General",
            subcategory = "General",
            description = "Notifies you when there's an update."
    )
    public boolean shouldNotifyUpdate = true;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Colour",
            category = "General",
            subcategory = "General",
            description = "Selects the color for all HUD elements.",
            options = {"White", "Red", "Green", "Blue", "Pink", "Purple", "Yellow", "Orange", "Chrome(Switching colours)"}
    )
    public int heightLimitModColour = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Limit Mod BedWars",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Toggles the mod."
    )
    public boolean heightLimitModBedWars = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Max Height",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows the maximum height of the map."
    )
    public boolean showMaxHeight = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Left",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows the amount of blocks you need to place upwards before you reach the built height limit."
    )
    public boolean showHeightLeft = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Only While Playing",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Hides the HUD if you are in pre game lobby or final killed."
    )
    public boolean onlyWhilePlaying = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Map",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows the map you are on."
    )
    public boolean showMap = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Play A Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "Toggles the function to play a sound when you are near height limit."
    )
    public boolean shouldPlaySound = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "Chooses what sound you want to play.",
            options = {"Hypixel DING", "Golem Hit", "Blaze Hit", "Anvil Land", "Horse Death", "Ghast Scream", "Guardian Floop", "Cat Meow", "Dog Bark"}
    )
    public int soundToPlay = 0;

    @Property(
            type = PropertyType.SLIDER,
            name = "Notify Blocks",
            category = "BedWars",
            subcategory = "Sound",
            description = "Allows you to customize how many blocks are left until the sound is played",
            max = 15
    )
    public int blocksWhenPlay = 10;
    @Property(
            type = PropertyType.SWITCH,
            name = "Spam The Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "Makes the 'play a sound' feature spam the sound."
    )
    public boolean shouldSpamSound = false;

    public boolean toggle() {

        heightLimitMod = !heightLimitMod;
        markDirty(); // required since directly writing to vars
        writeData();
        return heightLimitMod;

    }
    public Config() {
        super(new File("./HeightLimitMod/config.toml"));
        initialize();
    }
}
