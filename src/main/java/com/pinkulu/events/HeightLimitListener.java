package com.pinkulu.events;

import club.sk1er.mods.core.util.MinecraftUtils;
import club.sk1er.mods.core.util.Multithreading;
import com.google.gson.Gson;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.JsonResponse;
import com.pinkulu.util.Replace;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.TimeUnit;

public class HeightLimitListener {

    private int ticks;
    private boolean checked = true;
    private boolean firstJoin;
    private boolean shouldPlaySound;
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

    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        Multithreading.schedule(() -> {
                    if (MinecraftUtils.isHypixel()) {
                        if (HeightLimitMod.instance.getConfig().shouldNotifyUpdate) {
                            APICaller.getVersion();
                        }
                        ticks = 60;
                        checked = false;
                        shouldPlaySound = false;
                    }
                }, 1, TimeUnit.SECONDS);
    }

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event){
        if (!(event.phase == TickEvent.Phase.START)) return;
        if(shouldRender && HeightLimitMod.instance.getConfig().shouldPlaySound &&
                (APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                        == HeightLimitMod.instance.getConfig().blocksWhenPlay && shouldPlaySound && !APICaller.isInvalid){
            switch (HeightLimitMod.instance.getConfig().soundToPlay) {
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
        if(shouldRender && HeightLimitMod.instance.getConfig().shouldPlaySound) {
            if (!HeightLimitMod.instance.getConfig().shouldSpamSound) {
                if ((APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                        > HeightLimitMod.instance.getConfig().blocksWhenPlay) {
                    shouldPlaySound = true;
                }
            }else{
                if ((APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                        >= HeightLimitMod.instance.getConfig().blocksWhenPlay) {
                    shouldPlaySound = true;
                }
            }
        }
        if(ticks <= 0 && checked){
            return;
        }
        if(ticks <= 0){
            checked = true;
            shouldCheck = true;
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
            if(!firstJoin && HeightLimitMod.instance.getConfig().shouldNotifyUpdate){
                if (APICaller.Version > HeightLimitMod.version) {
                    ChatStyle style = new ChatStyle();
                    style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/height-limit-mod-1-8-9-forge"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§d~~~~~~~~Height Limit Mod~~~~~~~~").setChatStyle(style));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b~~~~~~~~V" + APICaller.Version + " is now available~~~~~~~~").setChatStyle(style));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§b~~~~~~~~Message: " + APICaller.Info ).setChatStyle(style));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§d~~~~~~~~Click Here to download the new version~~~~~~~~").setChatStyle(style));
                    firstJoin = true;
                }
            }
            return;
        }
    ticks--;
    }

}
