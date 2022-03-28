package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class MainMenuState implements GameState {
	
	Game game;
	UIManager ui;
	
	public MainMenuState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	@Override
	public void update() {
		ui.update();
		game.getKeyboard().update();
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		screen.renderSheet(0, 0, Spritesheet.mainMenuBackground, false);
	}

	@Override
	public boolean canScrollWeapons() {
		return false;
	}

	@Override
	public void enterState() {
		Game.getUIManager().setMenu(game.mainMenu);
	}

	@Override
	public void exitState() {
	}
}
