package com.pinkulu.heightlimitmod;

import cc.polyfrost.oneconfig.events.EventManager;
import com.pinkulu.heightlimitmod.command.HeightLimitModCommand;
import com.pinkulu.heightlimitmod.config.HeightLimitModConfig;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.APICaller;
import net.minecraftforge.common.MinecraftForge;

@net.minecraftforge.fml.common.Mod(modid = HeightLimitMod.MODID, name = HeightLimitMod.NAME, version = HeightLimitMod.VERSION)
public class HeightLimitMod {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @net.minecraftforge.fml.common.Mod.Instance(MODID)
    public static HeightLimitMod INSTANCE;
    public HeightLimitModConfig config;

    @net.minecraftforge.fml.common.Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        config = new HeightLimitModConfig();
        CommandManager.INSTANCE.registerCommand(HeightLimitModCommand.class);
        EventManager.INSTANCE.register(new HeightLimitListener());
        APICaller.GetLimits();
        APICaller.GetVersion();
    }
}
