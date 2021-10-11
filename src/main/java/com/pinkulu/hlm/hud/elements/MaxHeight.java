package com.pinkulu.hlm.hud.elements;

import com.pinkulu.hlm.config.Config;
import com.pinkulu.hlm.events.HeightLimitListener;
import com.pinkulu.hlm.hud.IRenderer;
import com.pinkulu.hlm.hud.util.PositionConfig;
import com.pinkulu.hlm.hud.util.ScreenPosition;
import com.pinkulu.hlm.util.ChromaStringRenderer;
import com.pinkulu.hlm.util.FileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class MaxHeight implements IRenderer {

    public MaxHeight() {
    }

    @Override
    public void save(ScreenPosition position) {
        PositionConfig.MaxHeightX = position.getRelativeX();
        PositionConfig.MaxHeightY = position.getRelativeY();
    }

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromRelativePosition(PositionConfig.MaxHeightX, PositionConfig.MaxHeightY);
    }

    @Override
    public void render(ScreenPosition position) {
        if (Config.heightLimitMod && Config.showMaxHeight) {
            if (!FileUtil.isInvalid && HeightLimitListener.shouldRender) {
                if (Config.displayBackground) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(1.0, 1.0, -100);
                    Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 4, position.getAbsoluteY() + getHeight(), Config.backgroundColor.getRGB());
                    GlStateManager.translate(1.0, 1.0, 0);
                    GlStateManager.popMatrix();
                }
                if (Config.rgb) {
                    ChromaStringRenderer.drawChromaText("Max Height: " + FileUtil.limit, position.getAbsoluteX(), position.getAbsoluteY(), Config.renderShadow);
                } else {
                    Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + FileUtil.limit, position.getAbsoluteX(), position.getAbsoluteY(), Config.heightLimitModTextColour.getRGB(), Config.renderShadow);

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
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth("Max Height: 150");
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        if (Config.heightLimitMod && Config.showMaxHeight) {
            if (Config.displayBackground) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(1.0, 1.0, -100);
                Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 5, position.getAbsoluteY() + getHeight(), Config.backgroundColor.getRGB());
                GlStateManager.translate(1.0, 1.0, 0);
                GlStateManager.popMatrix();
            }
            if (Config.rgb) {
                ChromaStringRenderer.drawChromaText("Max Height: " + "150", position.getAbsoluteX(), position.getAbsoluteY(), Config.renderShadow);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + "150", position.getAbsoluteX(), position.getAbsoluteY(), Config.heightLimitModTextColour.getRGB(), Config.renderShadow);

            }
        }
    }

}