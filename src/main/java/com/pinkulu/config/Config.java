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
    public boolean heightLimitMod = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Update Notify",
            category = "General",
            subcategory = "General",
            description = "Notifys you when theres an update."
    )
    public boolean shouldNotifyUpdate = true;
    @Property(
            type = PropertyType.SELECTOR,
            name = "Colour",
            category = "General",
            subcategory = "General",
            description = "Choose What Text Colour You Want",
            options = {"White", "Red", "Green", "Blue", "Pink", "Purple", "Yellow", "Orange", "Chrome(Switching colours)"}
    )
    public int heightLimitModColour = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Limit Mod BedWars",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Enabled Height Limit Mod For BedWars."
    )
    public boolean heightLimitModBedWars = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Max Height",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows The Max Height Of the Map"
    )
    public boolean showMaxHeight = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Height Left",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows The Height Left Till Max Height."
    )
    public boolean showHeightLeft = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Map",
            category = "BedWars",
            subcategory = "BedWars",
            description = "Shows The Map You Are On."
    )
    public boolean showMap = true;
    @Property(
            type = PropertyType.SWITCH,
            name = "Play A Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "Should A Sound Be Played When You`re Near The Block Limit."
    )
    public boolean shouldPlaySound = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "Choose What Sound You Want To Play",
            options = {"Hypixel DING", "Golem Hit", "Blaze Hit", "Anvil Land", "Horse Death", "Ghast Scream", "Guardian Floop", "Cat Meow", "Dog Bark"}
    )
    public int soundToPlay = 0;

    @Property(
            type = PropertyType.SLIDER,
            name = "Notify Blocks",
            category = "BedWars",
            subcategory = "Sound",
            description = "How many blocks left until the sound is played",
            max = 15
    )
    public int blocksWhenPlay = 10;
    @Property(
            type = PropertyType.SWITCH,
            name = "Spam The Sound",
            category = "BedWars",
            subcategory = "Sound",
            description = "WARNING: This Will Spam The Sound, Making It Supper Loud, And Play A lot Of Time."
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
