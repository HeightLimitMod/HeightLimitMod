package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class HeightLimitUtil {
    public static JsonObject mapCache;

    public static boolean shouldRender(){
        if (!APICaller.cacheReady) return false;
        if (!HypixelUtils.INSTANCE.isHypixel()) return false;
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        if (location == null) return false;
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return false;
        if(!Objects.equals(mapCache.get("name").toString(), "\"" + mapName + "\"" )) {
            getMapInfo(mapName, location.getGameType().toString());
        }
        return Objects.equals(mapCache.get("name").toString(), "\"" + mapName + "\"" );
    }
    public static int getLimit(){
        if(mapCache.get("buildRadius").getAsInt() == -1){
            return mapCache.get("maxBuild").getAsInt();
        }
        int maxBuild = mapCache.get("maxBuild").getAsInt();
        int buildRadius = mapCache.get("buildRadius").getAsInt();
        return maxBuild;
    }
    public static String getMapName(){
        if (!APICaller.cacheReady) return "";
        if (!HypixelUtils.INSTANCE.isHypixel())  return "";
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        if (location == null)  return "";
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName))  return "";
        return mapName;
    }

    public static void getMapInfo(String mapName, String gameType){
        AtomicReference<JsonObject> mapInfo = new AtomicReference<>(new JsonObject());
        System.out.println("Now doing this");

        Multithreading.runAsync(() -> {
            for (JsonElement map : APICaller.heightCache) {
                JsonObject mapObj = map.getAsJsonObject();
                if(mapObj.get("name").toString().contains(mapName) && mapObj.get("gameType").toString().contains(gameType)) {
                    mapInfo.set(mapObj);
                    System.out.println("found a map");
                    mapCache = mapInfo.get();
                }
            }
            });
    }
    public static String getMapType(){
        String pool = mapCache.get("pool").getAsString();
        switch (pool){
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
}
