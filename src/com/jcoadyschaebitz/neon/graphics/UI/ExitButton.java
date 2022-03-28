package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class ExitButton extends UIButton {

	public ExitButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	@Override
	public void doFunction() {
		Game.getUIManager().loadGameMenu.saveState();
		System.exit(0);
	}
}
