package com.pinkulu.events;

import com.google.gson.Gson;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.JsonResponse;
import com.pinkulu.util.Replace;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnChat {
    public static boolean shouldCheck;
    public static String map;
    public static boolean shouldRender;


    @SubscribeEvent
    public void chat(ClientChatReceivedEvent event) {
        if (shouldCheck) {
            final String msg = event.message.getUnformattedText();
            if (msg.startsWith("{")) {
                JsonResponse Jresponse = new Gson().fromJson(msg, JsonResponse.class);
                if (msg.contains("map") && msg.contains("BEDWARS")) {
                    if (msg.contains("BEDWARS_EIGHT_ONE") || msg.contains("BEDWARS_EIGHT_TWO")) {
                        APICaller.get("8team", Replace.space(Jresponse.map.toLowerCase()));
                        map = Jresponse.map;
                        shouldRender = true;
                    } else if (msg.contains("BEDWARS_FOUR_THREE") || msg.contains("BEDWARS_FOUR_FOUR")) {
                        APICaller.get("4team", Replace.space(Jresponse.map.toLowerCase()));
                        map = Jresponse.map;
                        shouldRender = true;
                    } else {
                        shouldRender = false;
                    }
                } else {
                    shouldRender = false;
                }
                shouldCheck = false;
                event.setCanceled(true);
            }
        }
    }
}
