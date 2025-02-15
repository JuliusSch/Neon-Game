package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class MainMenuState implements GameState {
	
	UIManager uiManager;
	
	public MainMenuState(Game game) {
		uiManager = Game.getUIManager();
	}

	@Override
	public void update() {
		uiManager.update();
		Game.getInputManager().update();
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
		uiManager.setMenu(uiManager.mainMenu);
		Game.getInputManager().setInMenu(true);
	}

	@Override
	public void exitState() {
		Game.getInputManager().setInMenu(false);
	}
}
