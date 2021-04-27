package com.pinkulu.gui;

import club.sk1er.mods.core.ModCore;
import com.google.common.collect.Sets;
import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.util.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

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
		this.registeredRenderers.addAll(Arrays.asList(renderers));
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
		ModCore.getInstance().getGuiHandler().open(new PropertyScreen(this));
	}

	@SubscribeEvent
	protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
			if (HeightLimitMod.instance.getConfig().heightLimitMod && (Minecraft.getMinecraft().currentScreen == null || HeightLimitMod.instance.getConfig().showInGui) && Minecraft.getMinecraft().thePlayer != null)
				if(!(mc.currentScreen instanceof PropertyScreen)){
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
