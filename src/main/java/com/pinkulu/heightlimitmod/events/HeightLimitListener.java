package com.pinkulu.heightlimitmod.events;

import cc.polyfrost.oneconfig.events.event.ReceivePacketEvent;
import cc.polyfrost.oneconfig.events.event.ScreenOpenEvent;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.oneconfig.utils.Notifications;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import com.pinkulu.heightlimitmod.config.HeightLimitModConfig;
import com.pinkulu.heightlimitmod.utils.APICaller;
import com.pinkulu.heightlimitmod.utils.HeightLimitUtil;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraftforge.event.world.WorldEvent;

import static com.pinkulu.heightlimitmod.events.ForgeEventListener.placedBlocks;
import static java.lang.Double.parseDouble;

public class HeightLimitListener {
    public static boolean shouldPlaySound;
    public static int limit = 0;
    public static boolean editingHUD = false;
    public static boolean joinedOnce = false;


    //List of sounds https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/mapping-and-modding-tutorials/2213619-1-8-all-playsound-sound-arguments
    public static void PlaySound() {
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


    @Subscribe
    private void onWorldLoad(WorldEvent.Load event) {
        ForgeEventListener.placedBlocks.clear();
        if (joinedOnce) return;
        joinedOnce = true;
        if (parseDouble(HeightLimitMod.VERSION) < APICaller.latest_version)
            Notifications.INSTANCE.send("Height Limit Mod", "version " + APICaller.latest_version + " available \nInfo: " + APICaller.info, 15000);
    }

    @Subscribe
    private void placeBlockPacket(ReceivePacketEvent event){
        if (event.packet.toString().contains("S23PacketBlockChange")) {
            S23PacketBlockChange packetBlockChange = (S23PacketBlockChange) event.packet;
            if(!HeightLimitUtil.shouldRender()) return;
            if(packetBlockChange.getBlockState().getBlock().getLocalizedName().toLowerCase().contains("air")){
                placedBlocks.remove(packetBlockChange.getBlockPosition());
                return;
            };
            if (!packetBlockChange.getBlockState().getBlock().getLocalizedName().toLowerCase().contains("wool")) return;
            if(HeightLimitModConfig.dynamicLimit){
                if(Math.ceil(HeightLimitUtil.heightLimit(
                        packetBlockChange.getBlockPosition().getX(),
                        packetBlockChange.getBlockPosition().getZ(),
                        HeightLimitUtil.getBuildRadius()
                ) - 1) > packetBlockChange.getBlockPosition().getY()) return;
            }else{
                if((HeightLimitUtil.getLimit() - 1) > packetBlockChange.getBlockPosition().getY()) return;
            }
            placedBlocks.put(packetBlockChange.getBlockPosition(), true);
        }
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (event.stage == Stage.START) {
            if (limit == 0) return;
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
    private void screenOpen(ScreenOpenEvent event) {
        if (event.screen == null) {
            editingHUD = false;
            return;
        }
        if (event.screen.toString().contains("HudGui")) editingHUD = true;
        if (event.screen.toString().contains("OneConfigGui")) editingHUD = true;
    }





}