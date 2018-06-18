package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class MainMenuState implements GameState {
	
	Game game;
	UIManager ui;
	
	public MainMenuState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	public void update() {
		game.getKeyboard().update();
	}

	public void render(Screen screen, int xScroll, int yScroll) {
	}

	public boolean canScrollWeapons() {
		return false;
	}
}
