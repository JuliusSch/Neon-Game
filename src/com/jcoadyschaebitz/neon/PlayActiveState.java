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

	@Override
	public void update() {
		game.getKeyboard().update();
		game.getLevel().update();
		if (ui.currentMenu != game.gamePlayUI) {
			ui.setMenu(game.gamePlayUI);
		}
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render((int) xScroll, (int) yScroll, screen, game.getLevel());
	}

	@Override
	public boolean canScrollWeapons() {
		return true;
	}
}
