package com.pinkulu.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;
import com.pinkulu.config.Config;
import com.pinkulu.gui.util.ScreenPosition;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class HudPropertyApi {

	public static HudPropertyApi newInstance(){
		HudPropertyApi api = new HudPropertyApi();
		MinecraftForge.EVENT_BUS.register(api);
		return api;
	}

	private final Set<IRenderer> registeredRenderers = Sets.newHashSet();
	private final Minecraft mc = Minecraft.getMinecraft();

	private boolean renderOutlines = true;

	private HudPropertyApi(){}

	public void register(IRenderer... renderers){
		Collections.addAll(this.registeredRenderers, renderers);
	}

	public void unregister(IRenderer... renderers){
		for(IRenderer renderer : renderers){
			this.registeredRenderers.remove(renderer);
		}
	}

	public Collection<IRenderer> getHandlers(){
		return Sets.newHashSet(registeredRenderers);
	}

	public boolean getRenderOutlines(){
		return renderOutlines;
	}

	public void setRenderOutlines(boolean renderOutlines){
		this.renderOutlines = renderOutlines;
	}

	public void openConfigScreen(){
		mc.displayGuiScreen(new PropertyScreen(this));
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event){
		if(event.type == ElementType.TEXT){
			if((!(mc.currentScreen instanceof PropertyScreen)) && (mc.currentScreen == null || Config.showInGui)){
				registeredRenderers.forEach(this::callRenderer);
			}
		}
	}

	private void callRenderer(IRenderer renderer){
		if(!renderer.isEnabled()){
			return;
		}

		ScreenPosition position = renderer.load();

		if(position == null){
			position = ScreenPosition.fromRelativePosition(0.5, 0.5);
		}

		renderer.render(position);
	}

}
