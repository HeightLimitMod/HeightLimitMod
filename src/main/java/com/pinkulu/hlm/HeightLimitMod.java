package com.pinkulu.hlm;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.pinkulu.hlm.config.Config;
import com.pinkulu.hlm.config.HLMCommand;
import com.pinkulu.hlm.events.HeightLimitListener;
import com.pinkulu.hlm.gui.HudPropertyApi;
import com.pinkulu.hlm.gui.renderHightLimit.PositionConfig;
import com.pinkulu.hlm.gui.renderHightLimit.guiTexts.BlocksTillMax;
import com.pinkulu.hlm.gui.renderHightLimit.guiTexts.CurrentMap;
import com.pinkulu.hlm.gui.renderHightLimit.guiTexts.MaxHeight;
import com.pinkulu.hlm.util.APICaller;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


@Mod(modid = HeightLimitMod.MODID, version = HeightLimitMod.VERSION, name = HeightLimitMod.NAME)
public class HeightLimitMod {

    public static final String VERSION = "3.0";
    public static final String NAME = "HeightLimitMod";
    public static final String MODID = "heightlimitmod";
    public static Config config;
    public static HudPropertyApi api;

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        api = HudPropertyApi.newInstance();
        config = new Config();
        config.preload();
        MinecraftForge.EVENT_BUS.register(new HeightLimitListener());
        new HLMCommand().register();
        api.register(new MaxHeight());
        api.register(new CurrentMap());
        api.register(new BlocksTillMax());
        loadConfig();
        saveConfig();
        APICaller.getVersion();
        APICaller.get();
    }

    private void loadConfig() {
        try {
            File file = new File("config/HeightLimitMod", "POSconfig.json");
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

    public static void saveConfig() {
        try {
            File file = new File("config/HeightLimitMod", "POSconfig.json");
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