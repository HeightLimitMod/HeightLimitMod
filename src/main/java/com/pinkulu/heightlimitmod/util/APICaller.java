package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class APICaller {
    @SuppressWarnings("UnnecessaryModifier")
    public static transient JsonObject heightCache = new JsonObject();

    public static boolean cacheReady = false;
    public static double latest_version = 0.0;
    public static void GetLimits() {
        heightCache = null;
        Multithreading.runAsync(() -> {
            try {
                JsonElement json = NetworkUtils.getJsonElement("https://maps.pinkulu.com/");

                if (json != null) {
                    heightCache = (JsonObject) json;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cacheReady = true;
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
