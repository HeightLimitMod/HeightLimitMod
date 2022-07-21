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
import com.pinkulu.heightlimitmod.util.HeightLimitUtil;
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
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() ? HeightLimitUtil.getMapName() : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && !HeightLimitUtil.getMapName().equals("")) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}
