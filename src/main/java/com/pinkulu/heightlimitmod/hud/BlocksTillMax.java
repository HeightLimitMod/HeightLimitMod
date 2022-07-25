package com.pinkulu.heightlimitmod.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.HeightLimitUtil;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public class BlocksTillMax extends SingleTextHud {
    private final String notSupportedText = "000";

    public BlocksTillMax() {
        super("Blocks Left", true);
    }

    @Override
    protected String getText(boolean example) {
        return String.valueOf(HeightLimitUtil.getLimit());
    }

    @Override
    public boolean isEnabled() {
        return (super.isEnabled() && !Objects.equals(getText(false), notSupportedText) && !String.valueOf(HeightLimitUtil.getLimit()).equals(notSupportedText)) || (super.isEnabled() && HeightLimitListener.editingHUD);
    }
}