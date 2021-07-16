
package com.pinkulu.config;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.DelayedTask;
import gg.essential.api.EssentialAPI;
import gg.essential.api.gui.Notifications;
import gg.essential.universal.ChatColor;
import gg.essential.universal.UDesktop;
import kotlin.Unit;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

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
            EssentialAPI.getInstance().guiUtil().openScreen(HeightLimitMod.instance.getConfig().gui());
            return;
        }
        switch (args[0].toLowerCase()) {
            default:
                EssentialAPI.getNotifications().push("Height Limit Mod", "Unknown argument. Type /heightlimitmod help for all commands.");
                break;
            case "gui":
            case "hud":
                new DelayedTask(() -> api.openConfigScreen(), 1);
                break;
            case "help":
                EssentialAPI.getMinecraftUtil().sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",
                        ChatColor.LIGHT_PURPLE + "Command Help\n" +
                                "/heightlimitmod - Open Config Menu\n" +
                                "/heightlimitmod help - Shows help for command usage\n" +
                                "/heightlimitmod hud or /heightlimitmod gui - Opens a GUI to configure where the hud is rendered.\n" +
                                "/heightlimitmod config - Open Config Menu. \n" + "/heightlimitmod aliases - Shows the aliases of the command.\n" +
                                "/heightlimitmod update - Check if you are on the newest version");
                break;
            case "aliases":
                EssentialAPI.getMinecraftUtil().sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",ChatColor.LIGHT_PURPLE + "/heightlimitmod, /hlm, /heightlimit, /heightmod");
                break;
            case "update":
                if(Double.parseDouble(APICaller.Version) > Double.parseDouble(HeightLimitMod.VERSION)){
                    EssentialAPI.getNotifications().push("Height Limit Mod", "A new version is available: V"+APICaller.Version
                            + "\nClick Here", () -> {
                        UDesktop.INSTANCE.browse(URI.create("https://www.curseforge.com/minecraft/mc-mods/height-limit-mod-1-8-9-forge"));
                        return Unit.INSTANCE;
                    });
                }else{
                    EssentialAPI.getNotifications().push("Height Limit Mod", "You are on the latest version");
                }
                break;
            case "config":
                EssentialAPI.getInstance().guiUtil().openScreen(HeightLimitMod.instance.getConfig().gui());
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}