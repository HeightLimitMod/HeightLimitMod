package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawUtil;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.utils.HeightLimitUtil;

import java.util.Objects;

public class BlocksTillMax extends SingleTextHud {
    private final String notSupportedText = "000";

    public BlocksTillMax() {
        super("Blocks Left", true);
    }

    @Override
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() && LocrawUtil.INSTANCE.getLocrawInfo() != null ? String.valueOf(HeightLimitUtil.getLimit() - HeightLimitUtil.getPlayerY()) : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && !HeightLimitUtil.getMapName().equals("")) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}