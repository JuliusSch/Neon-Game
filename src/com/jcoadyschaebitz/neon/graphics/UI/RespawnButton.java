package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.level.Level;

public class RespawnButton extends UIButton {

	Game game;
	
	public RespawnButton(int x, int y, int width, int height, String label, int colour) {
		super(x, y, width, height, label, colour);
		game = Game.getUIManager().getGame();
	}

	@Override
	public void doFunction() {
		// resest level, respawn player at last spawn, set state back to state active
		Level.refreshLevels(uiManager.getGame().getLevel().getPlayer());
		game.loadDataFromSelectedSave();
		game.switchToGameState(game.playState);
	}
	
	@Override
	public String getId() {
		return "respawnButton";
	}
}
