package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class PlayActiveState implements GameState {
	
	Game game;
	UIManager ui;
	
	public PlayActiveState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	public void update() {
		game.getKeyboard().update();
		game.getLevel().update();
		if (ui.currentMenu != game.gamePlayUI) {
			ui.setMenu(game.gamePlayUI);
		}
	}

	public void render(Screen screen, int xScroll, int yScroll) {
		game.getLevel().render(xScroll, yScroll, screen, game.getLevel());
	}

	public boolean canScrollWeapons() {
		return true;
	}
}
