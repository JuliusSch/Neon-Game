package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class PauseMenuState implements GameState {
	
	Game game;
	UIManager ui;
	
	public PauseMenuState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	public void update() {
		game.getKeyboard().update();
		if (ui.currentMenu == game.gamePlayUI) {
			ui.setMenu(game.pauseSkillsMenu);
		}
	}

	public void render(Screen screen, int xScroll, int yScroll) {
		screen.renderSprite(0, 0, Sprite.pauseMenuBackground, false);
		screen.renderSprite(81, 27, Sprite.menuOutline, false);
	}

	public boolean canScrollWeapons() {
		return false;
	}

}
