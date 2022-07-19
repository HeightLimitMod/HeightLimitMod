package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
        /*
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        try {
            return APICaller.heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH)).getAsInt();
        }catch (Exception ignored){

        }
       final String mapName = location.getMapName();
       if (StringUtils.isBlank(mapName)) return 0;
       final JsonObject mapNames = (JsonObject) APICaller.heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH));
       if (mapNames == null) return 0;
       final JsonElement height = mapNames.get(mapName.replace(" ", "_").toLowerCase(Locale.ENGLISH));
       if (height == null) return 0;
       return height.getAsInt();
         */
        return 1;
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
}
