package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.utils.HeightLimitUtil;

import java.util.Objects;

public class MapType extends SingleTextHud {
    private final String notSupportedText = "None";

    public MapType() {
        super("Map Type", true);
    }

    @Override
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() ? HeightLimitUtil.getMapType() : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && HeightLimitUtil.getLimit() != 0) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}