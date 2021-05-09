package com.pinkulu.heightlimitmod.api;

import club.sk1er.mods.core.util.Multithreading;
import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.hypixel.locraw.GameType;
import co.uk.isxander.xanderlib.hypixel.locraw.LocationParsed;
import co.uk.isxander.xanderlib.utils.HttpsUtils;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ApiManager {

    public static final ApiManager instance = new ApiManager();
    public static final String API_URL = "https://api.pinkulu.com/HeightLimitMod/BedWars/%s/%s";

    private String currentMap = "";
    private int heightLimit = -1;

    public ApiManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        LocationParsed location = XanderLib.getInstance().getLocrawManager().getCurrentLocation();
        if (location.getGameType() != GameType.BEDWARS || location.isLobby() || location.getMap() == null || location.getMode() == null || location.getMode().contains("VOIDLESS")) {
            heightLimit = -1;
            currentMap = "";

            return;
        }

        if (location.getMap().equalsIgnoreCase(currentMap))
            return;

        currentMap = location.getMap();

        refreshLimit();
    }

    public void refreshLimit() {
        LocationParsed location = XanderLib.getInstance().getLocrawManager().getCurrentLocation();

        Multithreading.runAsync(() -> {
            BetterJsonObject response;
            String map = location.getMap().replaceAll(" ", "_").toLowerCase();
            if (location.getMode().equalsIgnoreCase("BEDWARS_EIGHT_ONE") || location.getMode().equalsIgnoreCase("BEDWARS_EIGHT_TWO")) {
                response = getResponse("8team", map);
            } else {
                response = getResponse("4team", map);
            }

            if (response.has("error")) {
                HeightLimitMod.LOGGER.error("API Error: " + response.optString("error"));
                return;
            }

            heightLimit = response.optInt("Limit", -1);
        });
    }

    private BetterJsonObject getResponse(String mode, String map) {
        String url = String.format(API_URL, mode, map);
        HeightLimitMod.LOGGER.info("Fetching: " + url);
        return new BetterJsonObject(HttpsUtils.getString(url));
    }

    public int getHeightLimit() {
        return heightLimit;
    }

}
