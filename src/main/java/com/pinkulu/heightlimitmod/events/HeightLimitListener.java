package com.pinkulu.heightlimitmod.events;

import com.google.gson.Gson;
import com.pinkulu.heightlimitmod.util.FileUtil;
import com.pinkulu.heightlimitmod.util.JsonResponse;
import com.pinkulu.heightlimitmod.util.Replace;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HeightLimitListener {
    public static boolean checked = true;
    public static boolean shouldCheck;
    public static boolean shouldRender;
    public static String map = null;
    private int ticks;
    private boolean shouldPlaySound;
    private static final Gson GSON = new Gson();

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void chat(ClientChatReceivedEvent event) {
        if (shouldCheck) {
            final String msg = event.message.getUnformattedText();
            if (msg.startsWith("{")) {
                JsonResponse Jresponse = GSON.fromJson(msg, JsonResponse.class);
                shouldRender = false;
                try {
                    if (Jresponse.map != null && !Jresponse.map.equals("?")) {
                        map = Jresponse.map;
                        FileUtil.read(Replace.space(Jresponse.gametype.toLowerCase()), Replace.space(Jresponse.map.toLowerCase()));
                    } else {
                        map = Jresponse.mode;
                        FileUtil.read(Replace.space(Jresponse.gametype.toLowerCase()), Replace.space(Jresponse.mode.toLowerCase()));
                    }
                    shouldRender = true;
                } catch (Exception e) {
                    shouldRender = false;
                }
                shouldCheck = false;
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        /*
        if (EssentialAPI.getMinecraftUtil().isHypixel()) {
            ticks = 20;
            checked = false;
            shouldPlaySound = false;
        }
         */
    }
}
