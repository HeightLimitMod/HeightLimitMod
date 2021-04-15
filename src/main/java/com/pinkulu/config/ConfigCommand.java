package com.pinkulu.config;

import club.sk1er.mods.core.ModCore;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.renderHightLimit.guiTexts.GUI;
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
        else if (args[0].equals("config")){
            ModCore.getInstance().getGuiHandler().open(HeightLimitMod.instance.getConfig().gui());
        }else if (args[0].equals("hud")){
            ModCore.getInstance().getGuiHandler().open(new GUI());
        }
    }


    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
