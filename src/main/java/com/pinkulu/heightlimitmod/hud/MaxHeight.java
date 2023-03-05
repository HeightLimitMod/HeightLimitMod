package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.HeightLimitUtil;

import java.util.Objects;

public class MaxHeight extends SingleTextHud {

    private final String notSupportedText = "000";

    public MaxHeight() {
        super("Max Height", true);
    }

    @Override
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() ? String.valueOf(HeightLimitUtil.getLimit()) : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && !String.valueOf(HeightLimitUtil.getLimit()).equals(notSupportedText)) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}
