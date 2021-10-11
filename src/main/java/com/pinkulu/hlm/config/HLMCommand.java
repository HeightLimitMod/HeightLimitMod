package com.pinkulu.hlm.config;

import com.pinkulu.hlm.HeightLimitMod;
import com.pinkulu.hlm.gui.HudPropertyApi;
import com.pinkulu.hlm.util.APICaller;
import com.pinkulu.hlm.util.DelayedTask;
import gg.essential.api.EssentialAPI;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import gg.essential.api.commands.SubCommand;
import gg.essential.universal.ChatColor;
import gg.essential.universal.UDesktop;
import kotlin.Unit;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class HLMCommand extends Command {
    private final HudPropertyApi api;
    private final Set<Alias> aliases = new HashSet<>();

    @Nullable
    @Override
    public Set<Alias> getCommandAliases() {
        if (aliases.isEmpty()) {
            aliases.add(new Alias("hlm"));
            aliases.add(new Alias("heightlimit"));
            aliases.add(new Alias("heightmod"));
        }
        return aliases;
    }

    public HLMCommand(HudPropertyApi api) {
        super("heightlimitmod", true);
        this.api = api;
    }

    @DefaultHandler
    public void handle() {
        EssentialAPI.getGuiUtil().openScreen(HeightLimitMod.config.gui());
    }

    @SubCommand(value = "gui", aliases = {"hud"}, description = "Opens a GUI to edit the HUD.")
    public void openGUI() {
        new DelayedTask(api::openConfigScreen, 1);
    }

    @SubCommand(value = "update", description = "Checks for an update.")
    public void update() {
        if (Double.parseDouble(APICaller.Version) > Double.parseDouble(HeightLimitMod.VERSION)) {
            EssentialAPI.getNotifications().push("Height Limit Mod", "A new version is available: V" + APICaller.Version
                    + "\nClick Here", () -> {
                UDesktop.browse(URI.create("https://www.curseforge.com/minecraft/mc-mods/height-limit-mod-1-8-9-forge"));
                return Unit.INSTANCE;
            });
        } else {
            EssentialAPI.getNotifications().push("Height Limit Mod", "You are on the latest version");
        }
    }

    @SubCommand(value = "reload", description = "Reloads the API.")
    public void reload() {
        APICaller.get();
        EssentialAPI.getMinecraftUtil().sendMessage(ChatColor.DARK_PURPLE + "[HeightLimitMod] ",ChatColor.LIGHT_PURPLE + "API has been called and the file has been reloaded");
    }
}
