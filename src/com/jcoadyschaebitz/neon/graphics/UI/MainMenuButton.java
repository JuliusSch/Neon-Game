package com.jcoadyschaebitz.neon.graphics.UI;

public class MainMenuButton extends UIButton {

	public MainMenuButton(int x, int y, int width, int height, String label, int colour) {
		super(x, y, width, height, label, colour);
	}
	
	@Override
	public void doFunction() {
		uiManager.setMenu(uiManager.mainMenu);
	}

	@Override
	public String getId() {
		return "mainMenuButton";
	}
}
