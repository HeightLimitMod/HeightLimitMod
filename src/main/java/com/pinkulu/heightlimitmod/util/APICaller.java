package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class APICaller {
    @SuppressWarnings("UnnecessaryModifier")
    public static transient JsonArray heightCache = new JsonArray();

    public static boolean cacheReady = false;
    public static double latest_version = 0.0;
    public static String info = "";
    public static void GetLimits() {
        heightCache = null;
        Multithreading.runAsync(() -> {
            try {
                JsonElement json = NetworkUtils.getJsonElement("https://maps.pinkulu.com/trans-rights-are-human-rights.json");
                if (json != null) {
                    heightCache = (JsonArray) json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cacheReady = true;
                HeightLimitUtil.getMapInfo("Waterfall", "BEDWARS");
            }
        });
    }
    public static void GetVersion() {
        Multithreading.runAsync(() -> {
            try {
                final JsonElement json = NetworkUtils.getJsonElement("https://maps.pinkulu.com/version.json");
                if (json != null) {
                    final JsonObject object = json.getAsJsonObject();
                    latest_version = object.get("Version").getAsDouble();
                    info = object.get("Info").getAsString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
