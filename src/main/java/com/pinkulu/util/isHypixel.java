package com.pinkulu.util;

import net.minecraft.client.Minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class isHypixel {
    public static boolean isHypixel() {
        final Pattern SERVER_BRAND_PATTERN = Pattern.compile("(.+) <- (?:.+)");
        final String HYPIXEL_SERVER_BRAND = "Hypixel BungeeCord";

        Minecraft mc = Minecraft.getMinecraft();

        if (!mc.isSingleplayer() && mc.thePlayer.getClientBrand() != null) {
            Matcher matcher = SERVER_BRAND_PATTERN.matcher(mc.thePlayer.getClientBrand());

            if (matcher.find()) {
                // Group 1 is the server brand.
                return matcher.group(1).startsWith(HYPIXEL_SERVER_BRAND);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
