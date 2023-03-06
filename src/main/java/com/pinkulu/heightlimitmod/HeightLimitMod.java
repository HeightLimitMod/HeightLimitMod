package com.pinkulu.heightlimitmod;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import com.pinkulu.heightlimitmod.command.HeightLimitModCommand;
import com.pinkulu.heightlimitmod.config.HeightLimitModConfig;
import com.pinkulu.heightlimitmod.events.HeightLimitListener;
import com.pinkulu.heightlimitmod.util.APICaller;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = HeightLimitMod.MODID, name = HeightLimitMod.NAME, version = HeightLimitMod.VERSION)
public class HeightLimitMod {

    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    @Mod.Instance(MODID)
    public static HeightLimitMod INSTANCE;
    public HeightLimitModConfig config;

    @Mod.EventHandler
    public void onFMLInitialization(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        config = new HeightLimitModConfig();
        CommandManager.INSTANCE.registerCommand(new HeightLimitModCommand());
        EventManager.INSTANCE.register(new HeightLimitListener());
        APICaller.getLimits();
        APICaller.getVersion();
    }
}
