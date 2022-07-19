package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pinkulu.heightlimitmod.hud.GameMode;
import net.minecraftforge.fml.common.API;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;

public class HeightLimitUtil {
    public static boolean shouldRender(){
        if (!APICaller.cacheReady) return false;
        if (!HypixelUtils.INSTANCE.isHypixel()) return false;
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        if (location == null) return false;
        return APICaller.heightCache.has(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH));
    }
    public static int getLimit(){
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
    }
    public static String getMapName(){
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        try {
            if(APICaller.heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH)).getAsInt() != 0)
            return "";
        }catch (Exception ignored){}
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return "";
        final JsonObject mapNames = (JsonObject) APICaller.heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH));
        if (mapNames == null) return "";
        final JsonElement height = mapNames.get(mapName.replace(" ", "_").toLowerCase(Locale.ENGLISH));
        if (height == null) return "";
        return location.getMapName();
    }
}
