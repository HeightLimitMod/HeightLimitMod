package com.pinkulu.gui;

import com.pinkulu.HeightLimitMod;
import com.pinkulu.gui.util.ScreenPosition;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class PropertyScreen extends GuiScreen {

	private final GuiScreen parent;
	private boolean dragging;
	private Optional<IRenderer> selectedElement = Optional.empty();
	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();
	private int prevX, prevY;

	public PropertyScreen(GuiScreen parent) {
		this.parent = parent;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		this.updateElementPosition(mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void onGuiClosed() {
		renderers.forEach(IRenderer::save);
		HeightLimitMod.saveConfig();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		this.prevX = mouseX;
		this.prevY = mouseY;
		this.selectedElement = renderers.keySet().stream().filter(new MouseHoveringElement(mouseX, mouseY)).findFirst();

		if (selectedElement.isPresent()) {
			if (!selectedElement.get().isEnabled()) return;
			this.dragging = true;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		this.dragging = false;
	}

	protected void updateElementPosition(int x, int y) {
		if (selectedElement.isPresent() && this.dragging) {
			IRenderer renderer = selectedElement.get();
			renderer.load().setAbsolute(renderer.load().getAbsoluteX() + x - this.prevX, renderer.load().getAbsoluteY() + y - this.prevY);
		}
		this.prevX = x;
		this.prevY = y;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private static class MouseHoveringElement implements Predicate<IRenderer> {

		private final int x, y;

		public MouseHoveringElement(int x, int y) {
			this.x = x;
			this.y = y;
		}


		@Override
		public boolean test(IRenderer renderer) {
			int posX = renderer.load().getAbsoluteX();
			int posY = renderer.load().getAbsoluteY();
			if (x >= posX && x <= posX + renderer.getWidth()) {
				if (y >= posY && y <= posY + renderer.getHeight()) {
					return renderer.isEnabled();
				}
			}
			return false;
		}
	}

}