package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.IRenderer;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.util.ScreenPosition;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class BlocksTillMax implements IRenderer {

    @Override
    public void save(ScreenPosition position) {
        PositionConfig.BlocksTillMaxX = position.getRelativeX();
        PositionConfig.BlocksTillMaxY = position.getRelativeY();
    }

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromRelativePosition(PositionConfig.BlocksTillMaxX, PositionConfig.BlocksTillMaxY);
    }

    @Override
    public void render(ScreenPosition position) {
        if(HeightLimitMod.instance.getConfig().heightLimitMod && HeightLimitMod.instance.getConfig().showHeightLeft){
            if(!APICaller.isInvalid && HeightLimitListener.shouldRender) {
                if (HeightLimitMod.instance.getConfig().displayBackground) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(1.0, 1.0, -100);
                    Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 1, position.getAbsoluteX() + getWidth() + 10, position.getAbsoluteY() + getHeight(), Integer.MIN_VALUE);
                    GlStateManager.translate(1.0, 1.0, 0);
                    GlStateManager.popMatrix();
                }
                Minecraft.getMinecraft().fontRendererObj.drawString("Blocks Left: " + (APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY()), position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), HeightLimitMod.instance.getConfig().renderShadow);
            }
        }
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public int getWidth() {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth("HELLO WORLD");
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        if(HeightLimitMod.instance.getConfig().heightLimitMod &&  HeightLimitMod.instance.getConfig().showHeightLeft) {
            Minecraft.getMinecraft().fontRendererObj.drawString("Blocks Left: " + (APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY()), position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), HeightLimitMod.instance.getConfig().renderShadow);
        }
    }

}
