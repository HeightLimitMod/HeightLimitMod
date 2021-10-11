package com.pinkulu.hlm.events;

import com.google.gson.Gson;
import com.pinkulu.hlm.config.Config;
import com.pinkulu.hlm.util.FileUtil;
import com.pinkulu.hlm.util.JsonResponse;
import com.pinkulu.hlm.util.Replace;
import gg.essential.api.EssentialAPI;
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

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void chat(ClientChatReceivedEvent event) {
        if (shouldCheck) {
            final String msg = event.message.getUnformattedText();
            if (msg.startsWith("{")) {
                JsonResponse Jresponse = new Gson().fromJson(msg, JsonResponse.class);
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
        if (!Minecraft.getMinecraft().isSingleplayer() && EssentialAPI.getMinecraftUtil().isHypixel()) {
            ticks = 20;
            checked = false;
            shouldPlaySound = false;
        }
    }

    @SubscribeEvent
    public void frame(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (shouldRender && Config.shouldPlaySound &&
                    (FileUtil.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            == Config.blocksWhenPlay && shouldPlaySound && !FileUtil.isInvalid) {
                switch (Config.soundToPlay) {
                    case 0:
                        Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 1:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.irongolem.hit", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 2:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.blaze.hit", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 3:
                        Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 4:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.horse.death", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 5:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 6:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.guardian.land.hit", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 7:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.cat.meow", 1f, 1f);
                        shouldPlaySound = false;
                        break;
                    case 8:
                        Minecraft.getMinecraft().thePlayer.playSound("mob.wolf.bark", 1f, 1f);
                        shouldPlaySound = false;
                }
            }
            if (shouldRender && Config.shouldPlaySound) {
                if (!Config.shouldSpamSound) {
                    if ((FileUtil.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            > Config.blocksWhenPlay) {
                        shouldPlaySound = true;
                    }
                } else {
                    if ((FileUtil.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            >= Config.blocksWhenPlay) {
                        shouldPlaySound = true;
                    }
                }
            }
            if (ticks <= 0 && checked) {
                return;
            }
            if (ticks <= 0) {
                checked = true;
                shouldCheck = true;
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            }
            ticks--;
        }
    }

}
