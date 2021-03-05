package com.pinkulu.events;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.renderHightLimit.DelayedTask;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.isHypixel;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WorldLoad {
    private int ticks;
    private boolean checked = true;
    private boolean firstJoin;
    private boolean shouldPlaySound;
    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        new DelayedTask(() -> {
            if (isHypixel.isHypixel()) {
                if (!firstJoin && HeightLimitMod.instance.getConfig().shouldNotifyUpdate) {
                    APICaller.getVersion();
                }
                ticks = 60;
                checked = false;
                shouldPlaySound = false;
            }
        }, 1);
    }
    @SubscribeEvent
    public void frame(TickEvent.PlayerTickEvent event){

        if(OnChat.shouldRender && HeightLimitMod.instance.getConfig().shouldPlaySound &&
                (APICaller.limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                        == HeightLimitMod.instance.getConfig().blocksWhenPlay && shouldPlaySound && !APICaller.isInvalid){
            if(HeightLimitMod.instance.getConfig().soundToPlay == 0){
                Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1f, 1f);
                    shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 1){
                Minecraft.getMinecraft().thePlayer.playSound("mob.irongolem.hit", 1f, 1f);
                    shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 2){
                Minecraft.getMinecraft().thePlayer.playSound("mob.blaze.hit", 1f, 1f);
                    shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 3){
                Minecraft.getMinecraft().thePlayer.playSound("random.anvil_land", 1f, 1f);
                    shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 4){
                Minecraft.getMinecraft().thePlayer.playSound("mob.horse.death", 1f, 1f);
                shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 5){
                Minecraft.getMinecraft().thePlayer.playSound("mob.ghast.scream", 1f, 1f);
                shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 6){
                Minecraft.getMinecraft().thePlayer.playSound("mob.guardian.land.hit", 1f, 1f);
                shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 7){
                Minecraft.getMinecraft().thePlayer.playSound("mob.cat.meow", 1f, 1f);
                shouldPlaySound = false;
            }
            else if(HeightLimitMod.instance.getConfig().soundToPlay == 8){
                Minecraft.getMinecraft().thePlayer.playSound("mob.wolf.bark", 1f, 1f);
                shouldPlaySound = false;
            }
        }
        if(OnChat.shouldRender && HeightLimitMod.instance.getConfig().shouldPlaySound) {
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
            //make it check if thres a bed in the inventory
        }
        if(ticks <= 0 && checked){
            return;
        }
        if(ticks <= 0 && !checked){
            checked = true;
            OnChat.shouldCheck = true;
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
