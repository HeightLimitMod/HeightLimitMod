package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.hud.SingleTextHud;
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils;
import cc.polyfrost.oneconfig.utils.hypixel.LocrawInfo;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.APICaller;
import com.pinkulu.heightlimitmod.util.HeightLimitUtil;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Locale;
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
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && HeightLimitUtil.getLimit() != 0) || (super.isEnabled() && HeightLimitListener.editingHUD) ;
    }
}
