package com.pinkulu.config;

import club.sk1er.mods.core.ModCore;
import club.sk1er.mods.core.gui.notification.Notifications;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.HudPropertyApi;
import com.pinkulu.util.APICaller;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class ConfigCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "heightlimitmod";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " [config | hud]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
        }
        if (args.length <= 0) {
            ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
            return;
        }
        switch (args[0].toLowerCase()) {
            default:
                Notifications.INSTANCE.pushNotification("Height Limit Mod", "Unrecognized argument.");
                break;
            case "config":
                ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
                break;
            case "checkver":
                APICaller.getVersion();
                Notifications.INSTANCE.pushNotification("Height Limit Mod", "The version that is installed is " + HeightLimitMod.VERSION + " and the latest is " + APICaller.Version + ".");
                break;
            case "hud":
            case "gui":
                HudPropertyApi.getNewInstance().openConfigScreen();
                break;
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
