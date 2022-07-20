package com.pinkulu.heightlimitmod.util;

import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.Sys;

import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;

import static sun.awt.geom.Curve.next;

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
        return mapCache.get("maxBuild").getAsInt();
        /*
        if(mapCache.get("buildRadius").getAsInt() == -1){
            return mapCache.get("maxBuild").getAsInt();
        }
        int maxBuild = mapCache.get("maxBuild").getAsInt();
        int buildRadius = mapCache.get("buildRadius").getAsInt();
        int x = Minecraft.getMinecraft().thePlayer.getPosition().getX();
        int z = Minecraft.getMinecraft().thePlayer.getPosition().getZ();
        int y = Minecraft.getMinecraft().thePlayer.getPosition().getZ();
        return (int) (buildRadius - (Math.abs(Math.sqrt(x * x + z * z)) * 0.25f));
         */
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

    public static double calculateAngle(double dx, double dy) {
        double alpha;
        if (Math.abs(dx) > Math.abs(dy)) {
            double tg = dy / dx;
            alpha = Math.atan(tg);

            if (dx < 0) {
                alpha += Math.PI;
            }
        } else {
            double ctg = dx / dy;
            alpha = Math.PI / 2. - Math.atan(ctg);

            if (dy < 0) {
                alpha += Math.PI;
            }
        }

        return alpha;
    }

    // calculates the distnace to the top of the sphear, using players x, y and z positions, assuming the player is inside the sphere, where the center of the sphere is x = 0, z = 0 and y = 70
    public static double getAngle(double radius, double x, double y) {
        double angle = calculateAngle(x, y);
        double distance = Math.sqrt(x * x + y * y);
        double height = 70;
        double angle2 = Math.acos((radius * radius + distance * distance - height * height) / (2 * radius * distance));
        return angle + angle2;
    }

}
