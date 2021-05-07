package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.config.Config;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.IRenderer;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.util.ScreenPosition;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class CurrentMap implements IRenderer {

    public CurrentMap() {
    }

    @Override
    public void save(ScreenPosition position) {
        PositionConfig.CurrentMapX = position.getRelativeX();
        PositionConfig.CurrentMapY = position.getRelativeY();
    }

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromRelativePosition(PositionConfig.CurrentMapX, PositionConfig.CurrentMapY);
    }

    @Override
    public void render(ScreenPosition position) {
        if (Config.heightLimitMod && Config.showMap) {
            if (HeightLimitListener.shouldRender) {
                if (!(APICaller.isInvalid)) {
                    if (Config.displayBackground) {
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(1.0, 1.0, -100);
                        Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 5, position.getAbsoluteY() + getHeight(), Integer.MIN_VALUE);
                        GlStateManager.translate(1.0, 1.0, 0);
                        GlStateManager.popMatrix();
                    }
                    Minecraft.getMinecraft().fontRendererObj.drawString("Map: " + HeightLimitListener.map, position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), HeightLimitMod.instance.getConfig().renderShadow);
                } else {
                    Minecraft.getMinecraft().fontRendererObj.drawString("Map: MAP_NOT_FOUND", position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), HeightLimitMod.instance.getConfig().renderShadow);

                }
            }
        }
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        if(HeightLimitListener.map != null) {
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth("Map: " + HeightLimitListener.map);
        }
        else{
            return Minecraft.getMinecraft().fontRendererObj.getStringWidth("Map: Cool Map");
        }
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        if(Config.heightLimitMod &&  Config.showHeightLeft) {
            Minecraft.getMinecraft().fontRendererObj.drawString("Map: Cool Map", position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), HeightLimitMod.instance.getConfig().renderShadow);
        }
    }
}