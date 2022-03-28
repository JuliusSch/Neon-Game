package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class LoadMenuButton extends UIButton {

	public LoadMenuButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}

	@Override
	public void doFunction() {
		Game.getUIManager().setMenu(Game.getUIManager().getGame().loadMenu);
	}
}
