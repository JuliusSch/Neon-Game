package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class PlayButton extends UIButton {

	Game game;
	
	public PlayButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
		game = Game.getUIManager().getGame();
	}

	@Override
	public void doFunction() {
		game.loadSelectedSave();
		game.switchToGameState(game.playState);
	}
}
