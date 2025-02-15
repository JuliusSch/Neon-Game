package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class SaveAndExitButton extends UIButton {

	public SaveAndExitButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
	}


	public void doFunction() {
		Game.getUIManager().getGame().switchToGameState(Game.getUIManager().getGame().mainMenuState);
//		Game.getUIManager().loadGameMenu.saveToSelectedSlot();
	}

	@Override
	public String getId() {
		return "saveButton";
	}
}
