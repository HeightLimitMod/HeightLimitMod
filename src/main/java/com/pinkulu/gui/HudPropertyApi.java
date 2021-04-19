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

	public static HudPropertyApi getNewInstance(){
		HudPropertyApi api = new HudPropertyApi();
		MinecraftForge.EVENT_BUS.register(api);
		return api;
	}

	private final Set<IRenderer> registeredRenderers = Sets.newHashSet();

	private HudPropertyApi(){}

	public void register(IRenderer... renderers){
		this.registeredRenderers.addAll(Arrays.asList(renderers));
	}

	public Collection<IRenderer> getHandlers(){
		return Sets.newHashSet(registeredRenderers);
	}

	public void openConfigScreen(){
		ModCore.getInstance().getGuiHandler().open((new PropertyScreen(this)));
	}

	@SubscribeEvent
	protected void onGameOverlayRendered(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
			for (IRenderer element : this.registeredRenderers) {
				if (HeightLimitMod.instance.getConfig().heightLimitMod && (Minecraft.getMinecraft().currentScreen == null || HeightLimitMod.instance.getConfig().showInGui) && Minecraft.getMinecraft().thePlayer != null)
					callRenderer(element);
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
