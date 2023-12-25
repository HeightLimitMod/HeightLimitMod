package com.pinkulu.heightlimitmod.command;

import cc.polyfrost.oneconfig.libs.universal.ChatColor;
import cc.polyfrost.oneconfig.libs.universal.UChat;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import cc.polyfrost.oneconfig.utils.commands.annotations.SubCommand;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import com.pinkulu.heightlimitmod.utils.APICaller;

@Command(value = HeightLimitMod.MODID,
        description = "Access the " + HeightLimitMod.NAME + " GUI.", aliases = {"hlm", "heightlimitmod"})
public class HeightLimitModCommand {

    @Main
    private void main() {
        HeightLimitMod.INSTANCE.config.openGui();
    }

    @SubCommand(aliases = "update", description = "Check if theres an update")
    public void update() {
        UChat.chat(ChatColor.GREEN + "API version: " + APICaller.latest_version);
    }

    @SubCommand(aliases = "refreshlimits", description = "Pulls the height limit maps API, pulling the latest limits")
    private void refreshLimits() {
        APICaller.getLimits();
        UChat.chat(ChatColor.GREEN + "The limits have been refreshed!");
    }
}