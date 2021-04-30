package com.pinkulu.config;

import club.sk1er.mods.core.ModCore;
import club.sk1er.mods.core.universal.ChatColor;
import club.sk1er.mods.core.util.MinecraftUtils;
import club.sk1er.mods.core.util.Multithreading;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.HudPropertyApi;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
                MinecraftUtils.sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ", ChatColor.LIGHT_PURPLE + "Unknown argument. Type /heightlimitmod help for correct usage.");
                break;
            case "gui":
            case "hud":
                Multithreading.schedule(() -> api.openConfigScreen(), 5, TimeUnit.MILLISECONDS);
                break;
            case "help":
                MinecraftUtils.sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",
                        ChatColor.LIGHT_PURPLE + "Command Help\n" +
                                "/heightlimitmod - Open Config Menu\n" +
                                "/heightlimitmod help - Shows help for command usage\n" +
                                "/heightlimitmod hud or /heightlimitmod gui - Opens a GUI to configure where the hud is rendered.\n" +
                                "/heightlimitmod config - Open Config Menu. \n" + "/heightlimitmod aliases - Shows the aliases of the command.");
                break;
            case "aliases":
                MinecraftUtils.sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",ChatColor.LIGHT_PURPLE + "/heightlimitmod, /hlm, /heightlimit, /heightmod");
                break;
            case "update":

            case "config":
                ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
