package com.pinkulu;

import club.sk1er.mods.core.ModCoreInstaller;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.pinkulu.config.ConfigCommand;
import com.pinkulu.config.Config;
import com.pinkulu.events.OnChat;
import com.pinkulu.events.WorldLoad;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.renderHightLimit.guiTexts.BlocksTillMax;
import com.pinkulu.gui.renderHightLimit.guiTexts.CurrentMap;
import com.pinkulu.gui.renderHightLimit.guiTexts.MaxHeight;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


@Mod(modid = HeightLimitMod.MODID, version = HeightLimitMod.VERSION, name = HeightLimitMod.NAME)
public class HeightLimitMod {

    static final String MODID = "HeightLimitMod";
    public static final String VERSION = "1.0";
    public static final String NAME = "heightLimitMod";
    public static final double version = 0.7;
    public static boolean isHytilities = false;
    private Config config;

    @Mod.Instance("HeightLimitMod")
    public static HeightLimitMod instance;

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config = new Config();
        config.preload();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WorldLoad());
        MinecraftForge.EVENT_BUS.register(new OnChat());
        HudPropertyApi api = HudPropertyApi.newInstance();

        api.register(new MaxHeight());
        api.register(new CurrentMap());
        api.register(new BlocksTillMax());
        ClientCommandHandler.instance.registerCommand(new ConfigCommand(api));
        isHytilities = Loader.isModLoaded("hytilities");
        loadConfig();
    }

    @NotNull
    public Config getConfig() {
        return config;
    }


    public static void saveConfig() {
        try {
            File file = new File("HeightLimitMod", "POSconfig.json");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            JsonWriter writer = new JsonWriter(new FileWriter(file, false));
            writeJson(writer);
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            File file = new File("HeightLimitMod", "POSconfig.json");
            if (file.exists())
                readJson(file);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void readJson(File file) throws Throwable {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(new FileReader(file)).getAsJsonObject();
        json = json.getAsJsonObject("ScreenPositions");

        PositionConfig.MaxHeightX = json.get("MaxHeightX").getAsDouble();
        PositionConfig.MaxHeightY = json.get("MaxHeightY").getAsDouble();

        PositionConfig.CurrentMapX = json.get("CurrentMapX").getAsDouble();
        PositionConfig.CurrentMapY = json.get("CurrentMapY").getAsDouble();

        PositionConfig.BlocksTillMaxX = json.get("BlocksTillMaxX").getAsDouble();
        PositionConfig.BlocksTillMaxY = json.get("BlocksTillMaxY").getAsDouble();
    }

    public static void writeJson(JsonWriter writer) throws IOException {
        writer.setIndent(" "); // this enabled pretty print
        writer.beginObject();
        writer.name("ScreenPositions");
        writer.beginObject();

        writer.name("MaxHeightX").value(PositionConfig.MaxHeightX);
        writer.name("MaxHeightY").value(PositionConfig.MaxHeightY);

        writer.name("CurrentMapX").value(PositionConfig.CurrentMapX);
        writer.name("CurrentMapY").value(PositionConfig.CurrentMapY);

        writer.name("BlocksTillMaxX").value(PositionConfig.BlocksTillMaxX);
        writer.name("BlocksTillMaxY").value(PositionConfig.BlocksTillMaxY);

        writer.endObject();
        writer.endObject();
    }
}