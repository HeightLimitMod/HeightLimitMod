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
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.commons.lang3.ArrayUtils;

import static com.pinkulu.heightlimitmod.events.ForgeEventListener.placedBlocks;

public class HeightLimitListener {
    public static boolean shouldPlaySound;
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
        if (joinedOnce) return;
        joinedOnce = true;
        if (HeightLimitUtil.shouldUpdate(APICaller.latest_version, HeightLimitMod.VERSION))
            Notifications.INSTANCE.send("Height Limit Mod", "version " + APICaller.latest_version + " available \nInfo: " + APICaller.info, 15000);
    }

    @Subscribe
    private void placeBlockPacket(ReceivePacketEvent event) {
        if (event.packet.toString().contains("S23PacketBlockChange")) {
            S23PacketBlockChange packetBlockChange = (S23PacketBlockChange) event.packet;
             if (!HeightLimitUtil.shouldRender()) return;
            if (!packetBlockChange.getBlockState().getBlock().getLocalizedName().toLowerCase().contains("wool")) {
                ForgeEventListener.placedBlocks = ArrayUtils.removeElement(ForgeEventListener.placedBlocks, packetBlockChange.getBlockPosition());
                return;
            }

            if (Math.ceil(HeightLimitUtil.heightLimit(
                    packetBlockChange.getBlockPosition().getX(),
                    packetBlockChange.getBlockPosition().getZ(),
                    HeightLimitUtil.getBuildRadius()
            ) - 1) > packetBlockChange.getBlockPosition().getY()) return;

            for (BlockPos pos : ForgeEventListener.placedBlocks) {
                if (pos.getX() == packetBlockChange.getBlockPosition().getX() &&
                        pos.getY() == packetBlockChange.getBlockPosition().getY() &&
                        pos.getZ() == packetBlockChange.getBlockPosition().getZ()) {
                    return;
                }
            }

            ForgeEventListener.placedBlocks = ArrayUtils.add(ForgeEventListener.placedBlocks, packetBlockChange.getBlockPosition());
        }
        if (event.packet.toString().contains("S01PacketJoinGame")) {
            ForgeEventListener.placedBlocks = new BlockPos[0];
        }

        }

    @Subscribe
    private void onTick(TickEvent event) {
        if (event.stage == Stage.START) {
            if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().theWorld == null) return;
            if(!HeightLimitUtil.shouldRender()) return;
            int limit = (int) Math.ceil(HeightLimitUtil.heightLimit(
                    Math.floor(Minecraft.getMinecraft().thePlayer.posX),
                    Math.floor(Minecraft.getMinecraft().thePlayer.posZ),
                    HeightLimitUtil.getBuildRadius()
            ));
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