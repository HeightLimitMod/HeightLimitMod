package com.pinkulu.gui.renderHightLimit.guiTexts;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.config.Config;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * @author Filip, modified by Wyvest
 */
public class GUI extends GuiScreen {
    private boolean dragging;
    private int prevX, prevY;
    public boolean currentMap;
    public boolean blocksTillMax;
    public boolean maxHeight;

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        updatePos(mouseX, mouseY);
        BlocksTillMax.drawBlocksTillMax();
        CurrentMap.drawCurrentMap();
        MaxHeight.drawMaxHeight();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.prevX = mouseX;
        this.prevY = mouseY;
        if (mouseButton == 0) {
            this.dragging = true;
        }
        currentMap = new MouseHoveringElement(mouseX, mouseY).testCurrentMap();
        blocksTillMax = new MouseHoveringElement(mouseX, mouseY).testBlocksTillMax();
        maxHeight = new MouseHoveringElement(mouseX, mouseY).testMaxHeight();
    }

    private void updatePos(int x, int y) {
        if (this.dragging) {
            if (currentMap) {
                Config.currentMapX = this.prevX - 10;
                Config.currentMapY = this.prevY - 10;
            } else if (blocksTillMax) {
                Config.blocksTillMaxX = this.prevX - 10;
                Config.blocksTillMaxY = this.prevY - 10;
            } else if (maxHeight) {
                Config.maxHeightX = this.prevX - 10;
                Config.maxHeightY = this.prevY - 10;
            }
        }
        this.prevX = x;
        this.prevY = y;

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.dragging = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        HeightLimitMod.instance.getConfig().markDirty();
        HeightLimitMod.instance.getConfig().writeData();
        super.onGuiClosed();
    }
    public static class MouseHoveringElement {

        private final int x;
        private final int y;

        public MouseHoveringElement(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public boolean testBlocksTillMax() {
            int posX = Config.blocksTillMaxX;
            int posY = Config.blocksTillMaxY;
            if (x >= posX && x <= posX + BlocksTillMax.instance.width) {
                if (y >= posY && y <= posY + BlocksTillMax.instance.height) {
                    return HeightLimitMod.instance.getConfig().showHeightLeft;
                }
            }
            return false;
        }

        public boolean testCurrentMap() {
            int posX = Config.currentMapX;
            int posY = Config.currentMapY;
            if (x >= posX && x <= posX + CurrentMap.instance.width) {
                if (y >= posY && y <= posY + CurrentMap.instance.height) {
                    return HeightLimitMod.instance.getConfig().showMap;
                }
            }
            return false;
        }

        public boolean testMaxHeight() {
            int posX = Config.maxHeightX;
            int posY = Config.maxHeightY;
            if (x >= posX && x <= posX + MaxHeight.instance.width) {
                if (y >= posY && y <= posY + MaxHeight.instance.height) {
                    return HeightLimitMod.instance.getConfig().showMaxHeight;
                }
            }
            return false;
        }
    }
}