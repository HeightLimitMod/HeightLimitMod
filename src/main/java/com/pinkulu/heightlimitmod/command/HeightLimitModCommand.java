package com.pinkulu.heightlimitmod.command;

import com.pinkulu.heightlimitmod.HeightLimitMod;
import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;

@Command(value = HeightLimitMod.MODID, description = "Access the " + HeightLimitMod.NAME + " GUI.")
public class HeightLimitModCommand {

    @Main
    private static void main() {
        HeightLimitMod.INSTANCE.config.openGui();
    }
}