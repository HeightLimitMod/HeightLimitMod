package com.pinkulu.heightlimitmod.forge;

import co.uk.isxander.evergreenhud.addon.AddonManager;
import com.pinkulu.heightlimitmod.HeightLimitMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = HeightLimitMod.MOD_ID, version = HeightLimitMod.MOD_VERSION, name = HeightLimitMod.MOD_NAME, dependencies = "required-after:evergreenhud", clientSideOnly = true)
public class ForgeMod {
    @Mod.EventHandler
    protected void onInit(FMLInitializationEvent event) {
        AddonManager.getInstance().registerAddon(new HeightLimitMod());
    }
}
