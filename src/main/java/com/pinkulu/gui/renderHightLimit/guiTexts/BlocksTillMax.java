package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.config.Config;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.IRenderer;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.util.ScreenPosition;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.ChromaStringRenderer;
import com.pinkulu.util.readFile;
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
        if(Config.heightLimitMod && Config.showHeightLeft){
            if(!readFile.isInvalid && HeightLimitListener.shouldRender) {
                if (Config.displayBackground) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(1.0, 1.0, -100);
                    Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 4, position.getAbsoluteY() + getHeight(), Config.backgroundColor.getRGB());
                    GlStateManager.translate(1.0, 1.0, 0);
                    GlStateManager.popMatrix();
                }
                if(Config.rgb){
                    ChromaStringRenderer.drawChromaText("Blocks Left: " + (readFile.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY()), position.getAbsoluteX(), position.getAbsoluteY(), Config.renderShadow);
                }else {
                    Minecraft.getMinecraft().fontRendererObj.drawString("Blocks Left: " + (readFile.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY()), position.getAbsoluteX(), position.getAbsoluteY(), Config.heightLimitModTextColour.getRGB(), Config.renderShadow);
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
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth("Blocks Left: 100");
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        if (Config.displayBackground) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(1.0, 1.0, -100);
            Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 5, position.getAbsoluteY() + getHeight(), Config.backgroundColor.getRGB());
            GlStateManager.translate(1.0, 1.0, 0);
            GlStateManager.popMatrix();
        }
        if(Config.rgb){
            ChromaStringRenderer.drawChromaText("Blocks Left: " + "100", position.getAbsoluteX(), position.getAbsoluteY(), Config.renderShadow);
        }else {
            Minecraft.getMinecraft().fontRendererObj.drawString("Blocks Left: " + "100", position.getAbsoluteX(), position.getAbsoluteY(), Config.heightLimitModTextColour.getRGB(), Config.renderShadow);
        }
    }

}