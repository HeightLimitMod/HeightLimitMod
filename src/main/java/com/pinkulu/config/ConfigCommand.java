
package com.pinkulu.config;

import club.sk1er.mods.core.ModCore;
import club.sk1er.mods.core.gui.notification.Notifications;
import club.sk1er.mods.core.universal.ChatColor;
import club.sk1er.mods.core.util.MinecraftUtils;
import club.sk1er.mods.core.util.ModCoreDesktop;
import club.sk1er.mods.core.util.Multithreading;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.DelayedTask;
import kotlin.Unit;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConfigCommand extends CommandBase {
    private HudPropertyApi api;

    public ConfigCommand(HudPropertyApi api) {
        this.api = api;
    }
    @Override
    public String getCommandName() {
        return "heightlimitmod";
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("hlm", "heightlimit", "heightmod");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " [config | hud | aliases]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length <= 0) {
            ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
            return;
        }
        switch (args[0].toLowerCase()) {
            default:
                Notifications.INSTANCE.pushNotification("Height Limit Mod", "Unknown argument. Type /heightlimitmod help for all commands.");
                break;
            case "gui":
            case "hud":
                new DelayedTask(() -> api.openConfigScreen(), 1);
                break;
            case "help":
                MinecraftUtils.sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",
                        ChatColor.LIGHT_PURPLE + "Command Help\n" +
                                "/heightlimitmod - Open Config Menu\n" +
                                "/heightlimitmod help - Shows help for command usage\n" +
                                "/heightlimitmod hud or /heightlimitmod gui - Opens a GUI to configure where the hud is rendered.\n" +
                                "/heightlimitmod config - Open Config Menu. \n" + "/heightlimitmod aliases - Shows the aliases of the command.\n" +
                                "/heightlimitmod update - Check if you are on the newest version");
                break;
            case "aliases":
                MinecraftUtils.sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",ChatColor.LIGHT_PURPLE + "/heightlimitmod, /hlm, /heightlimit, /heightmod");
                break;
            case "update":
                if(Double.parseDouble(APICaller.Version) > Double.parseDouble(HeightLimitMod.VERSION)){
                    Notifications.INSTANCE.pushNotification("Height Limit Mod", "A new version is available: V"+APICaller.Version
                            + "\nClick Here", () -> {
                        ModCoreDesktop.INSTANCE.browse(URI.create("https://www.curseforge.com/minecraft/mc-mods/height-limit-mod-1-8-9-forge"));
                        return Unit.INSTANCE;
                    });
                }else{
                    Notifications.INSTANCE.pushNotification("Height Limit Mod", "You are on the latest version");
                }
                break;
            case "config":
                ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}