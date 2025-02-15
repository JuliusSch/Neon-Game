package com.jcoadyschaebitz.neon.graphics.UI;

public class SettingsButton extends UIButton {
	
	public SettingsButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	public void doFunction() {
		uiManager.setMenu(uiManager.pauseSettingsMenu);
	}

	@Override
	public String getId() {
		return "settingsButton";
	}
}
