package com.pinkulu.heightlimitmod;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.addon.AddonMeta;
import co.uk.isxander.evergreenhud.addon.EvergreenAddon;
import com.pinkulu.heightlimitmod.element.BlocksLeftElement;
import com.pinkulu.heightlimitmod.element.BlocksTotalElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeightLimitMod extends EvergreenAddon {

    public static final String MOD_ID = "hlmevergreen";
    public static final String MOD_VERSION = "1.1";
    public static final String MOD_NAME = "Height Limit Mod (EvergreenHUD)";

    public static final Logger LOGGER = LogManager.getLogger("HeightLimitMod");

    @Override
    public void init() {
        EvergreenHUD.getInstance().getElementManager().registerElement("HLM_BLOCKS_LEFT", BlocksLeftElement.class);
        EvergreenHUD.getInstance().getElementManager().registerElement("HLM_BLOCKS_TOTAL", BlocksTotalElement.class);
    }

    @Override
    public AddonMeta metadata() {
        return new AddonMeta(MOD_NAME, "Displays information about the height limit of your bedwars map.", MOD_VERSION);
    }
}