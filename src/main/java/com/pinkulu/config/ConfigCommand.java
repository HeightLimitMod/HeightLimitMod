
package com.pinkulu.config;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.events.HeightLimitListener;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.util.APICaller;
import com.pinkulu.util.DelayedTask;
import gg.essential.api.EssentialAPI;
import gg.essential.universal.ChatColor;
import gg.essential.universal.UDesktop;
import kotlin.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class ConfigCommand extends CommandBase {
    private final HudPropertyApi api;

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
                new DelayedTask(api::openConfigScreen, 1);
                break;
            case "help":
                EssentialAPI.getMinecraftUtil().sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",
                        ChatColor.LIGHT_PURPLE + "Command Help\n" +
                                ChatColor.AQUA + "/heightlimitmod config"+ ChatColor.WHITE +" - Open Config Menu. \n" +
                                ChatColor.AQUA + "/heightlimitmod hud or /heightlimitmod gui" + ChatColor.WHITE + " - Opens a GUI to configure where the hud is rendered.\n" +
                                ChatColor.AQUA + "/heightlimitmod help" + ChatColor.WHITE +" - Shows help for command usage\n" +
                                ChatColor.AQUA + "/heightlimitmod aliases"+ ChatColor.WHITE +" - Shows the aliases of the command.\n" +
                                ChatColor.AQUA + "/heightlimitmod update"+ ChatColor.WHITE +" - Check if you are on the newest version \n" +
                                ChatColor.AQUA + "/heightlimitmod load"+ ChatColor.WHITE +" - If a map doesn't show up when you join a game, or if a map gets " +
                                "added while you are in game, you can use this command to recall the api \n"
                );
                break;
            case "aliases":
                EssentialAPI.getMinecraftUtil().sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",ChatColor.LIGHT_PURPLE + "/heightlimitmod, /hlm, /heightlimit, /heightmod");
                break;
            case "update":
                if(Double.parseDouble(APICaller.Version) > Double.parseDouble(HeightLimitMod.VERSION)){
                    EssentialAPI.getNotifications().push("Height Limit Mod", "A new version is available: V"+APICaller.Version
                            + "\nClick Here", () -> {
                        UDesktop.browse(URI.create("https://www.curseforge.com/minecraft/mc-mods/height-limit-mod-1-8-9-forge"));
                        return Unit.INSTANCE;
                    });
                }else{
                    EssentialAPI.getNotifications().push("Height Limit Mod", "You are on the latest version");
                }
                break;
            case "config":
                EssentialAPI.getInstance().guiUtil().openScreen(HeightLimitMod.instance.getConfig().gui());
                break;
            case "load":
                if(Minecraft.getMinecraft().isSingleplayer() && EssentialAPI.getMinecraftUtil().isHypixel()){
                    HeightLimitListener.shouldRender = false;
                    HeightLimitListener.checked = false;
                }
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}