package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.config.Config;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class CurrentMap {
    private static final int textPadding = 5;
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final FontRenderer fontRenderer = mc.fontRendererObj;
    public final int width = 0;
    public final int height = 0;
    public static CurrentMap instance;

    public static void drawCurrentMap() {
        GlStateManager.pushMatrix();
        int x = Config.currentMapX;
        int y = Config.currentMapY;
        int height = 10;
        String text = null;
        int width = x + fontRenderer.getStringWidth(text);
        int color;
        final boolean pad = HeightLimitMod.instance.getConfig().hasPadding;
        int padding = pad ? 5 : 0;
        int yOffset = 2;

        if (!HeightLimitMod.instance.getConfig().heightLimitMod) {
            return;
        }

        if (HeightLimitMod.instance.getConfig().heightLimitMod && HeightLimitListener.shouldRender && HeightLimitMod.instance.getConfig().showHeightLeft) {
            if (!APICaller.isInvalid) {
                    text = "Map: " + HeightLimitListener.map;
            }
        }

            switch (HeightLimitMod.instance.getConfig().heightLimitModColour) {
                case 0:
                    color = Color.WHITE;
                    break;
                case 1:
                    color = Color.LIGHT_GRAY;
                    break;
                case 2:
                    color = Color.GRAY;
                    break;
                case 3:
                    color = Color.DARK_GRAY;
                    break;
                case 4:
                    color = Color.BLACK;
                    break;
                case 5:
                    color = Color.RED;
                    break;
                case 6:
                    color = Color.PINK;
                    break;
                case 7:
                    color = Color.ORANGE;
                    break;
                case 8:
                    color = Color.YELLOW;
                    break;
                case 9:
                    color = Color.GREEN;
                    break;
                case 10:
                    color = Color.MAGENTA;
                    break;
                case 11:
                    color = Color.CYAN;
                    break;
                case 12:
                    color = Color.BLUE;
                    break;
                default:
                    //honestly i have no idea why but chroma doesn't work if i put it in the interface
                    color = java.awt.Color.HSBtoRGB(System.currentTimeMillis() % 2000L / 2000.0F, 0.8F, 0.8F);
            }

            if (HeightLimitMod.instance.getConfig().showHeightLeft) {
                height += 9;
                if (HeightLimitMod.instance.getConfig().renderShadow) {
                    fontRenderer.drawStringWithShadow(text, x + textPadding,
                            y + yOffset + padding,
                            color);
                } else {
                    fontRenderer.drawString(text, x + textPadding,
                            y + yOffset + padding,
                            color);
                }

            }


            if (HeightLimitMod.instance.getConfig().displayBackground) {
                GlStateManager.translate(1.0, 1.0, -100);
                Gui.drawRect(x - 1, y - 1, width + 10, y + height, Integer.MIN_VALUE);
                width = 11 + mc.fontRendererObj.getStringWidth(text);
                height -= 1;
                GlStateManager.translate(1.0, 1.0, 0);
            }


            GlStateManager.popMatrix();
        }
    }
