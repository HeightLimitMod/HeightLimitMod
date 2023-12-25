package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawUtil;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.utils.APICaller;
import com.pinkulu.heightlimitmod.utils.HeightLimitUtil;

import java.util.Objects;

public class GameType extends SingleTextHud {
    private final String notSupportedText = "None";

    public GameType() {
        super("Game Type", false);
    }

    @Override
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() && LocrawUtil.INSTANCE.getLocrawInfo() != null ? LocrawUtil.INSTANCE.getLocrawInfo().getRawGameType() : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && HeightLimitUtil.getLimit() != 0) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}