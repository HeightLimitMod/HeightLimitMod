package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HeightLimitHud extends SingleTextHud {
    @Text(
            name = "Not Supported Text"
    )
    public String notSupportedText = "0";

    public HeightLimitHud() {
        super("Height", true);
    }

    @Override
    protected String getText() {
        if (!cacheReady) return notSupportedText;
        if (!HypixelUtils.INSTANCE.isHypixel()) return notSupportedText;
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        if (location == null) return notSupportedText;
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return notSupportedText;
        final HashMap<String, Integer> mapNames = heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH));
        if (mapNames == null) return notSupportedText;
        final Integer height = mapNames.get(mapName.replace(" ", "_").toLowerCase(Locale.ENGLISH));
        if (height == null) return notSupportedText;
        return height.toString();
    }

    @SuppressWarnings("UnnecessaryModifier")
    private static final transient HashMap<String, HashMap<String, Integer>> heightCache = new HashMap<>();

    private static boolean cacheReady = false;

    static {
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
            }
        });
    }
}
