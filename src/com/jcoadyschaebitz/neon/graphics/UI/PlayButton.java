package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.level.Level;

public class PlayButton extends UIButton {

	Game game;
	
	public PlayButton(int x, int y, int width, int height, String label, int fontColour) {
		super(x, y, width, height, label, fontColour);
		game = Game.getUIManager().getGame();
	}

	@Override
	public void doFunction() {
		Level.refreshLevels(ui.getGame().getLevel().getPlayer());
		game.loadDataFromSelectedSave();
		game.switchToGameState(game.playState);
	}
}
