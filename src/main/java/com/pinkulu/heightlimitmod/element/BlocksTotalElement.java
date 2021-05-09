package com.pinkulu.heightlimitmod.element;

import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.settings.impl.StringSetting;
import com.pinkulu.heightlimitmod.api.ApiManager;
import net.minecraft.util.BlockPos;

public class BlocksTotalElement extends Element {

    public StringSetting notInGame;

    @Override
    public void initialise() {
        addSettings(notInGame = new StringSetting("Not In Game Text", "Text that is displayed when you are not in a bedwars game.", "0"));
    }

    @Override
    protected ElementData metadata() {
        return new ElementData("HLM: Total Height", "Shows how many blocks remaining before you reach the height limit.");
    }

    @Override
    protected String getValue() {
        int limit = ApiManager.instance.getHeightLimit();

        if (limit == -1 || mc.thePlayer == null) {
            return notInGame.get();
        }

        return Integer.toString(limit);
    }

    @Override
    public String getDisplayTitle() {
        return "Limit";
    }

}
