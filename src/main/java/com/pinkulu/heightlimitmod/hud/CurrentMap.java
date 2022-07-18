package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.Multithreading;
import cc.polyfrost.oneconfig.utils.NetworkUtils;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.APICaller;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class CurrentMap extends SingleTextHud {

    private final String notSupportedText = "None";

    public CurrentMap() {
        super("Map", true);
    }

    @Override
    protected String getText() {
        if (!APICaller.cacheReady) return notSupportedText;
        if (!HypixelUtils.INSTANCE.isHypixel()) return notSupportedText;
        final LocrawInfo location = HypixelUtils.INSTANCE.getLocrawInfo();
        if (location == null) return notSupportedText;
        final String mapName = location.getMapName();
        if (StringUtils.isBlank(mapName)) return notSupportedText;
        final HashMap<String, Integer> mapNames = APICaller.heightCache.get(location.getGameType().getServerName().toLowerCase(Locale.ENGLISH));
        if (mapNames == null) return notSupportedText;
        final Integer height = mapNames.get(mapName.replace(" ", "_").toLowerCase(Locale.ENGLISH));
        if (height == null) return notSupportedText;
        return mapName;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(), notSupportedText)) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}
