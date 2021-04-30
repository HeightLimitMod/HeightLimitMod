package com.pinkulu.gui.elements;

import com.pinkulu.config.Config;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.IRenderer;
import com.pinkulu.gui.util.PositionConfig;
import com.pinkulu.gui.util.ScreenPosition;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class MaxHeight implements IRenderer{

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
		if(Config.heightLimitMod && Config.showMaxHeight){
			if(!APICaller.isInvalid && HeightLimitListener.shouldRender) {
				if (Config.displayBackground) {
					GlStateManager.pushMatrix();
					GlStateManager.translate(1.0, 1.0, -100);
					Gui.drawRect(position.getAbsoluteX() - 2, position.getAbsoluteY() - 3, position.getAbsoluteX() + getWidth() + 5, position.getAbsoluteY() + getHeight(), Integer.MIN_VALUE);
					GlStateManager.translate(1.0, 1.0, 0);
					GlStateManager.popMatrix();
				}
				Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor(), Config.renderShadow);
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
		if(Config.heightLimitMod && Config.showMaxHeight) {
			Minecraft.getMinecraft().fontRendererObj.drawString("MaxHeight: 150", position.getAbsoluteX(), position.getAbsoluteY(), Color.getColor());
		}
	}

}