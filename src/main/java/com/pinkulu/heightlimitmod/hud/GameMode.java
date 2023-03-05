package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawUtil;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.APICaller;
import com.pinkulu.heightlimitmod.util.HeightLimitUtil;

import java.util.Objects;

public class GameMode extends SingleTextHud {
    private final String notSupportedText = "None";

    public GameMode() {
        super("Game Mode", false);
    }

    @Override
    protected String getText(boolean example) {
        if (!HeightLimitUtil.shouldRender()) return notSupportedText;
        if (!APICaller.cacheReady) return notSupportedText;
        if (!HypixelUtils.INSTANCE.isHypixel()) return notSupportedText;
        final LocrawInfo location = LocrawUtil.INSTANCE.getLocrawInfo();
        if (location == null) return notSupportedText;
        return location.getGameMode();
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && HeightLimitUtil.getLimit() != 0) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}