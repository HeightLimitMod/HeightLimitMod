package com.pinkulu;

import club.sk1er.mods.core.ModCoreInstaller;
import com.pinkulu.config.Config;
import com.pinkulu.config.ConfigCommand;
import com.pinkulu.events.HeightLimitListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.jetbrains.annotations.NotNull;


@Mod(modid = HeightLimitMod.MODID, version = HeightLimitMod.VERSION, name = HeightLimitMod.NAME)
public class HeightLimitMod {

    static final String MODID = "HeightLimitMod";
    public static final String VERSION = "1.1";
    public static final String NAME = "heightLimitMod";
    public static final double version = 1.1;
    public final Config config = new Config();

    @Mod.Instance(HeightLimitMod.MODID)
    public static HeightLimitMod instance;

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config.preload();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new HeightLimitListener());
        ClientCommandHandler.instance.registerCommand(new ConfigCommand());
    }

    @NotNull
    public Config getConfig() {
        return config;
    }


}