package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.events.OnChat;
import com.pinkulu.gui.IRenderer;
import com.pinkulu.gui.renderHightLimit.PositionConfig;
import com.pinkulu.gui.util.ScreenPosition;

import com.pinkulu.util.APICaller;
import com.pinkulu.util.RainbowColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class MaxHeight implements IRenderer{

	private FontRenderer fontRenderer;
	
	public MaxHeight() {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
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
		if(HeightLimitMod.instance.getConfig().heightLimitMod && OnChat.shouldRender && HeightLimitMod.instance.getConfig().showMaxHeight && HeightLimitMod.instance.getConfig().heightLimitModBedWars){
			if(!APICaller.isInvalid) {
				if (HeightLimitMod.instance.getConfig().heightLimitModColour == 0) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 1) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0xFF0000);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 2) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0x00FF00);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 3) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0x0000FF);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 4) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFA7F9);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 5) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0x9900FF);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 6) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFF00);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 7) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), 0xF1C232);
				} else if (HeightLimitMod.instance.getConfig().heightLimitModColour == 8) {
					Minecraft.getMinecraft().fontRendererObj.drawString("Max Height: " + APICaller.limit, position.getAbsoluteX(), position.getAbsoluteY(), RainbowColor.getColor());
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
		if(HeightLimitMod.instance.getConfig().heightLimitMod  && HeightLimitMod.instance.getConfig().showMaxHeight && HeightLimitMod.instance.getConfig().heightLimitModBedWars) {
			Minecraft.getMinecraft().fontRendererObj.drawString("MaxHeight: 150", position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
		}
	}

}
