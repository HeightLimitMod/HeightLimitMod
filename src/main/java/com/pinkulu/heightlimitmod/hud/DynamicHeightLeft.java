package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.utils.HeightLimitUtil;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class DynamicHeightLeft extends SingleTextHud {

    private final String notSupportedText = "000";

    public DynamicHeightLeft() {
        super("Dynamic Height Left", true);
    }

    @Override
    protected String getText(boolean example) {
        return HeightLimitUtil.shouldRender() ? String.valueOf((int) Math.ceil(HeightLimitUtil.heightLimit(
                Math.floor(Minecraft.getMinecraft().thePlayer.posX),
                Math.floor(Minecraft.getMinecraft().thePlayer.posZ),
                HeightLimitUtil.getBuildRadius()
        )) - Minecraft.getMinecraft().thePlayer.posY) : notSupportedText;
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && !String.valueOf(HeightLimitUtil.getLimit()).equals(notSupportedText)) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}
