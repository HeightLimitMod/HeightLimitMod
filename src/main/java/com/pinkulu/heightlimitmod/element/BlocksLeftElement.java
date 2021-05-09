package com.pinkulu.heightlimitmod.element;

import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.settings.impl.StringSetting;
import com.pinkulu.heightlimitmod.api.ApiManager;
import net.minecraft.util.BlockPos;

public class BlocksLeftElement extends Element {

    public StringSetting notInGame;

    @Override
    public void initialise() {
        addSettings(notInGame = new StringSetting("Not In Game Text", "Text that is displayed when you are not in a bedwars game.", "0"));
    }

    @Override
    protected ElementData metadata() {
        return new ElementData("HLM: Blocks Left", "Shows how many blocks remaining before you reach the height limit.");
    }

    @Override
    protected String getValue() {
        int limit = ApiManager.instance.getHeightLimit();

        if (limit == -1 || mc.thePlayer == null) {
            return notInGame.get();
        }

        return Integer.toString(Math.max(limit - new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ).getY(), 0));
    }

    @Override
    public String getDisplayTitle() {
        return "Remaining";
    }

}
