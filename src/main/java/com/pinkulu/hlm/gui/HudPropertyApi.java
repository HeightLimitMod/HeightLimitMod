package com.pinkulu.hlm.gui;

import com.google.common.collect.Sets;
import com.pinkulu.hlm.config.Config;
import com.pinkulu.hlm.gui.util.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public final class HudPropertyApi {

    private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean renderOutlines = true;

    private HudPropertyApi() {
    }

    public static HudPropertyApi newInstance() {
        HudPropertyApi api = new HudPropertyApi();
        MinecraftForge.EVENT_BUS.register(api);
        return api;
    }

    public void register(IRenderer... renderers) {
        Collections.addAll(this.registeredRenderers, renderers);
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer renderer : renderers) {
            this.registeredRenderers.remove(renderer);
        }
    }

    public Collection<IRenderer> getHandlers() {
        return Sets.newHashSet(registeredRenderers);
    }

    public boolean getRenderOutlines() {
        return renderOutlines;
    }

    public void setRenderOutlines(boolean renderOutlines) {
        this.renderOutlines = renderOutlines;
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new PropertyScreen(this));
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (event.type == ElementType.ALL) {
            if (!(mc.currentScreen instanceof PropertyScreen)) {
                if (Config.showInGui || mc.currentScreen == null) {
                    registeredRenderers.forEach(this::callRenderer);
                }
            }
        }
    }

    private void callRenderer(IRenderer renderer) {
        if (!renderer.isEnabled()) {
            return;
        }

        ScreenPosition position = renderer.load();

        if (position == null) {
            position = ScreenPosition.fromRelativePosition(0.5, 0.5);
        }

        renderer.render(position);
    }

}
