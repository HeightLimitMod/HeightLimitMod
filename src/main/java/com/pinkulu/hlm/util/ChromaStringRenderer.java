package com.pinkulu.hlm.util;

import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * Stolen from Requisite under LGPLv3
 * https://github.com/Qalcyo/Requisite/blob/rewrite/LICENSE
 */
public class ChromaStringRenderer {
    /**
     * Renders a string of text to the screen.
     *
     * @param text       The text to render.
     * @param x          The x coordinate to render to.
     * @param y          The y coordinate to render to.
     * @param dropShadow Whether or not to render a dropshadow under the text.
     * @author Unknown
     */
    public static void drawChromaText(String text, float x, float y, boolean dropShadow) {
        for (char c : text.toCharArray()) {
            int col = getChroma(x, y).getRGB();
            String charStr = String.valueOf(c);
            Minecraft.getMinecraft().fontRendererObj.drawString(charStr, x, y, col, dropShadow);
            x += Minecraft.getMinecraft().fontRendererObj.getStringWidth(charStr);
        }
    }

    /**
     * @author Unknown
     */
    private static Color getChroma(double x, double y) {
        float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float) ((System.currentTimeMillis() - x * 10.0 * 1.0 - y * 10.0 * 1.0) % v) / v, 1.0f, 1.0f));
    }
}
