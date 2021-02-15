package com.pinkulu.util;

import java.awt.*;

public class RainbowColor {
    public static int getColor() {
        final long l = System.currentTimeMillis();
        return Color.HSBtoRGB(l % 2000L / 2000.0f, 0.8f, 0.8f);
    }
}