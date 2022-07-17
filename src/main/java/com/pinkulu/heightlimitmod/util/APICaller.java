package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class APICaller {
    @SuppressWarnings("UnnecessaryModifier")
    public static final transient HashMap<String, HashMap<String, Integer>> heightCache = new HashMap<>();

    public static boolean cacheReady = false;
    public static double latest_version = 0.0;
    public static void GetLimits() {
        Multithreading.runAsync(() -> {
            try {
                final JsonElement json = NetworkUtils.getJsonElement("https://maps.pinkulu.com/");
                if (json != null) {
                    final JsonObject object = json.getAsJsonObject();
                    for (Map.Entry<String, JsonElement> mode : object.entrySet()) {
                        HashMap<String, Integer> maps = new HashMap<>();
                        for (Map.Entry<String, JsonElement> map : mode.getValue().getAsJsonObject().entrySet()) {
                            maps.put(map.getKey(), map.getValue().getAsInt());
                        }
                        heightCache.put(mode.getKey(), maps);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cacheReady = true;
                System.out.println(heightCache.toString());
            }
        });
    }

    public static void GetVersion() {
        Multithreading.runAsync(() -> {
            try {
                final JsonElement json = NetworkUtils.getJsonElement("https://maps.pinkulu.com/version.json");
                if (json != null) {
                    final JsonObject object = json.getAsJsonObject();
                    System.out.println(object);
                    latest_version = object.get("Version").getAsDouble();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
