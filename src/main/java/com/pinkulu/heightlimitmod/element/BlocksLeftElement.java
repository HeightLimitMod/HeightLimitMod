package com.pinkulu.heightlimitmod.element;

import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.elements.RenderOrigin;
import co.uk.isxander.evergreenhud.elements.type.SimpleTextElement;
import com.pinkulu.heightlimitmod.api.ApiManager;
import net.minecraft.util.BlockPos;

public class BlocksLeftElement extends SimpleTextElement {
    @Override
    protected ElementData metadata() {
        return new ElementData("HLM: Blocks Left", "Shows how many blocks remaining before you reach the height limit.", "Hypixel");
    }

    @Override
    protected String getValue() {
        int limit = ApiManager.instance.getHeightLimit();

        if (limit == -1 || mc.thePlayer == null) {
            return "0";
        }

        return Integer.toString(Math.max(limit - new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ).getY(), 0));
    }

    @Override
    public String getDefaultDisplayTitle() {
        return "Remaining";
    }

    @Override
    public void render(float partialTicks, RenderOrigin origin) {
        if (origin == RenderOrigin.HUD) {
            if (ApiManager.instance.getHeightLimit() == -1 || mc.thePlayer == null) {
                return;
            }
        }

        super.render(partialTicks, origin);
    }
}
