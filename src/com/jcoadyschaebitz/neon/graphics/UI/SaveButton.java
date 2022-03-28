package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class SaveButton extends UIButton {

	public SaveButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}


	public void doFunction() {
		Game.getUIManager().getGame().switchToGameState(Game.getUIManager().getGame().mainMenuState);
		Game.getUIManager().loadGameMenu.saveToSelectedSlot();
	}

}
