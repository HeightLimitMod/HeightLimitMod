package com.pinkulu.heightlimitmod.events;

import cc.polyfrost.oneconfig.events.event.ScreenOpenEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.gui.GuiUtils;
import com.pinkulu.heightlimitmod.config.HeightLimitModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.omg.CORBA.Any;

public class HeightLimitListener {
    public static boolean shouldPlaySound;
    public static int limit = 0;
    public static boolean editingHUD = false;

    @Subscribe
    private void onTick(TickEvent event) {
        if (event.stage == Stage.START) {
            if(limit == 0 ) return;
            if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
            if (HeightLimitModConfig.shouldPlaySound &&
                    (limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            == HeightLimitModConfig.blocksWhenPlay && shouldPlaySound) {
                    PlaySound();
            }
            if (limit != 0 && HeightLimitModConfig.shouldPlaySound) {
                if (!HeightLimitModConfig.shouldSpamSound) {
                    if ((limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            > HeightLimitModConfig.blocksWhenPlay) {
                        shouldPlaySound = true;
                    }
                } else {
                    if ((limit - Minecraft.getMinecraft().thePlayer.getPosition().getY())
                            >= HeightLimitModConfig.blocksWhenPlay) {
                        shouldPlaySound = true;
                    }
                }
            }
        }
    }
    @Subscribe
    private void screenOpen(ScreenOpenEvent event){
        if(event.screen == null) {editingHUD = false; return;}
        if(event.screen.toString().contains("HudGui")) editingHUD = true;
        if(event.screen.toString().contains("OneConfigGui")) editingHUD = true;
    }

    //List of sounds https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments
    public static void PlaySound(){
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
        switch (HeightLimitModConfig.soundToPlay) {
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
                break;
            case 9:
                Minecraft.getMinecraft().thePlayer.playSound("random.explode", 1f, 1f);
                shouldPlaySound = false;
                break;
            case 10:
                Minecraft.getMinecraft().thePlayer.playSound(HeightLimitModConfig.soundName, 1f, 1f);
                shouldPlaySound = false;
                break;
        }
    }
}
