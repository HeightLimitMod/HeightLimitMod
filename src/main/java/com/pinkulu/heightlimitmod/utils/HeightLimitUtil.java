package com.pinkulu.heightlimitmod.utils;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.geometry.Point3D;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class HeightLimitUtil {
    public static JsonObject mapCache;

    public static boolean shouldRender() {
        if (!APICaller.cacheReady) return false;
        if (!HypixelUtils.INSTANCE.isHypixel()) return false;
        final LocrawInfo location = LocrawUtil.INSTANCE.getLocrawInfo();
        if (location == null) return false;
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return false;
        if (!Objects.equals(mapCache.get("name").toString(), "\"" + mapName + "\"")) {
            getMapInfo(mapName, location.getGameType().toString());
        }
        return Objects.equals(mapCache.get("name").toString(), "\"" + mapName + "\"");
    }

    public static int getLimit() {
        try {
            return mapCache.get("maxBuild").getAsInt();
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getMinLimit() {
        try {
            return mapCache.get("minBuild").getAsInt();
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getBuildRadius() {
        try {
            return mapCache.get("buildRadius").getAsInt();
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getMapName() {
        if (!APICaller.cacheReady) return "";
        if (!HypixelUtils.INSTANCE.isHypixel()) return "";
        final LocrawInfo location = LocrawUtil.INSTANCE.getLocrawInfo();
        if (location == null) return "";
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return "";
        return mapName;
    }

    public static void getMapInfo(String mapName, String gameType) {
        AtomicReference<JsonObject> mapInfo = new AtomicReference<>(new JsonObject());

        Multithreading.runAsync(() -> {
            for (JsonElement map : APICaller.heightCache) {
                JsonObject mapObj = map.getAsJsonObject();
                if (mapObj.get("name").toString().contains(mapName) && mapObj.get("gameType").toString().contains(gameType)) {
                    mapInfo.set(mapObj);
                    mapCache = mapInfo.get();
                }
            }
        });
    }

    public static String getMapType() {
        String pool = mapCache.get("pool").getAsString();
        switch (pool) {
            case "BEDWARS_4TEAMS_FAST":
                return "Fast 4 Teams";
            case "BEDWARS_4TEAMS_SLOW":
                return "Slow 4 Teams";
            case "BEDWARS_8TEAMS_FAST":
                return "Fast 8 Teams";
            case "BEDWARS_8TEAMS_SLOW":
                return "Slow 8 Teams";
            case "SKYWARS_MEGA":
                return "Mega Skywars";
            case "SKYWARS_RANKED":
                return "Ranked Skywars";
            case "SKYWARS_STANDARD":
                return "Normal Skywars";
            default:
                return "Unknown";
        }
    }

    public static int getPlayerY() {
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return 0;
        return Minecraft.getMinecraft().thePlayer.getPosition().getY();
    }


    public static double heightLimit(double x, double z, double radius) {
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        double underRoot = Math.pow(radius, 2) - Math.pow(distance, 2);
        if (underRoot < 0) {
            return Double.NaN; // Not a Number
        }
        return 70 + Math.sqrt(underRoot) > getLimit() ? getLimit() : 70 + Math.sqrt(underRoot);
    }




}
