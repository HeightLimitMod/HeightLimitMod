package com.pinkulu.heightlimitmod.events;

import com.pinkulu.heightlimitmod.config.HeightLimitModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

import static com.pinkulu.heightlimitmod.config.HeightLimitModConfig.enableHeightOverlay;
import static com.pinkulu.heightlimitmod.config.HeightLimitModConfig.heightOverlayColor;

public class ForgeEventListener {
    public static Map<BlockPos, Boolean> placedBlocks = new HashMap<>();
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if(!enableHeightOverlay) return;
        if (placedBlocks.isEmpty()) return;
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for (BlockPos pos : placedBlocks.keySet()) {
            System.out.println("Rendering block: " + pos.toString() + " " + placedBlocks.get(pos).toString());
            try {
                double x = pos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
                double y = pos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
                double z = pos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;

                GL11.glColor4f(
                        (float) heightOverlayColor.getRed() / 255,
                        (float) heightOverlayColor.getGreen() / 255,
                        (float) heightOverlayColor.getBlue() / 255,
                        (float) heightOverlayColor.getAlpha() / 255
                );


                switch (HeightLimitModConfig.heightOverlayStyle) {
                    case 1:
                        renderOutline(x, y, z);
                        break;
                    case 2:
                        renderX(x, y, z);
                        break;
                    default:
                        renderSolid(x, y, z);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error rendering block");
                e.printStackTrace();
                placedBlocks.remove(pos);
            }
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public void renderSolid(double x, double y, double z){
        // top
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x, y + 1 + 0.01, z + 1);
        GL11.glVertex3d(x + 1, y + 1 + 0.01, z + 1);
        GL11.glVertex3d(x + 1, y + 1 + 0.01, z);
        GL11.glVertex3d(x, y + 1 + 0.01, z);
        GL11.glEnd();

        // bottom
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x, y - 0.01, z);
        GL11.glVertex3d(x + 1 - 0.01, y, z);
        GL11.glVertex3d(x + 1 - 0.01, y, z + 1);
        GL11.glVertex3d(x, y - 0.01, z + 1);
        GL11.glEnd();

        // front
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x, y, z - 0.01);
        GL11.glVertex3d(x, y + 1, z - 0.01);
        GL11.glVertex3d(x + 1, y + 1, z - 0.01);
        GL11.glVertex3d(x + 1, y, z - 0.01);
        GL11.glEnd();

        //  back
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x, y, z + 1 + 0.01);
        GL11.glVertex3d(x + 1, y, z + 1 + 0.01);
        GL11.glVertex3d(x + 1, y + 1, z + 1 + 0.01);
        GL11.glVertex3d(x, y + 1, z + 1 + 0.01);
        GL11.glEnd();

        // left
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x - 0.01, y, z);
        GL11.glVertex3d(x - 0.01 , y, z + 1);
        GL11.glVertex3d(x - 0.01, y + 1, z + 1);
        GL11.glVertex3d(x - 0.01, y + 1, z);
        GL11.glEnd();

        // right
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(x + 1 + 0.01, y, z);
        GL11.glVertex3d(x + 1 + 0.01, y + 1 + 0.01, z);
        GL11.glVertex3d(x + 1 + 0.01, y + 1 + 0.01, z + 1);
        GL11.glVertex3d(x + 1 + 0.01, y, z + 1);
        GL11.glEnd();
    }

    public void renderOutline(double x, double y, double z){
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y, z + 1 + 0.01);
        GL11.glVertex3d(x + 1 , y, z + 1 + 0.01);
        GL11.glVertex3d(x + 1, y, z);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y + 1, z);
        GL11.glVertex3d(x, y, z + 1);
        GL11.glVertex3d(x, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y, z + 1);
        GL11.glVertex3d(x + 1, y + 1, z + 1);
        GL11.glVertex3d(x + 1, y, z);
        GL11.glVertex3d(x + 1, y + 1, z);
        GL11.glEnd();
    }

    public void renderX(double x, double y, double z){
        // render x on all sides

        // top
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y + 1 + 0.01, z + 1);
        GL11.glVertex3d(x + 1, y + 1 + 0.01, z);

        GL11.glVertex3d(x, y + 1 + 0.01, z);
        GL11.glVertex3d(x + 1, y + 1 + 0.01, z + 1);
        GL11.glEnd();

        // bottom
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y - 0.01, z);
        GL11.glVertex3d(x + 1 - 0.01, y, z + 1);

        GL11.glVertex3d(x, y - 0.01, z + 1);
        GL11.glVertex3d(x + 1 - 0.01, y, z);
        GL11.glEnd();

        // front
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y, z - 0.01);
        GL11.glVertex3d(x + 1, y + 1, z - 0.01);

        GL11.glVertex3d(x, y + 1, z - 0.01);
        GL11.glVertex3d(x + 1, y, z - 0.01);
        GL11.glEnd();

        //  back
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x, y, z + 1 + 0.01);
        GL11.glVertex3d(x + 1, y + 1, z + 1 + 0.01);

        GL11.glVertex3d(x, y + 1, z + 1 + 0.01);
        GL11.glVertex3d(x + 1, y, z + 1 + 0.01);
        GL11.glEnd();

        // left
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x - 0.01, y, z);
        GL11.glVertex3d(x - 0.01, y + 1, z + 1);

        GL11.glVertex3d(x - 0.01, y + 1, z);
        GL11.glVertex3d(x - 0.01, y, z + 1);
        GL11.glEnd();

        // right
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(x + 1 + 0.01, y, z);
        GL11.glVertex3d(x + 1 + 0.01, y + 1 + 0.01, z + 1);

        GL11.glVertex3d(x + 1 + 0.01, y + 1 + 0.01, z);
        GL11.glVertex3d(x + 1 + 0.01, y, z + 1);
        GL11.glEnd();

    }

}
