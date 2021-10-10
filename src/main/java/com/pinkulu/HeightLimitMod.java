package com.pinkulu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.pinkulu.config.ConfigCommand;
import com.pinkulu.config.Config;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.renderHightLimit.guiTexts.BlocksTillMax;
import com.pinkulu.gui.renderHightLimit.guiTexts.CurrentMap;
import com.pinkulu.gui.renderHightLimit.guiTexts.MaxHeight;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.readFile;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
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
    public static final String VERSION = "3.0";
    public static final String NAME = "heightLimitMod";
    public final Config config = new Config();
    @Mod.Instance("HeightLimitMod")
    public static HeightLimitMod instance;

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        config.preload();
        MinecraftForge.EVENT_BUS.register(new HeightLimitListener());
        HudPropertyApi api = HudPropertyApi.newInstance();
        ClientCommandHandler.instance.registerCommand(new ConfigCommand(api));

        api.register(new MaxHeight());
        api.register(new CurrentMap());
        api.register(new BlocksTillMax());
        loadConfig();
        saveConfig();
        APICaller.getVersion();
        APICaller.get();
    }

    @NotNull
    public Config getConfig() {
        return config;
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