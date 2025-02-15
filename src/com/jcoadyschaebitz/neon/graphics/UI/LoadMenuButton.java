package com.jcoadyschaebitz.neon.graphics.UI;

public class LoadMenuButton extends UIButton {

	public LoadMenuButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	@Override
	public void doFunction() {
		uiManager.setMenu(uiManager.loadMenu);
	}

	@Override
	public String getId() {
		return "loadMenuButton";
	}
}
