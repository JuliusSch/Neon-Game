package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class PlayerDiedState implements GameState {

	private Game game;
	private UIManager uiManager;
	
	public PlayerDiedState(Game game) {
		this.game = game;
		uiManager = Game.getUIManager();
	}
	
	@Override
	public boolean canScrollWeapons() {
		return false;
	}

	@Override
	public void update() {
		uiManager.update();
		Game.getInputManager().update();
		game.getLevel().update();
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render((int) xScroll, (int) yScroll, screen, game.getLevel());
	}

	@Override
	public void enterState() {
		game.getLevel().toggleMobility(true);
		uiManager.setMenu(uiManager.deadMenu);
		Game.getInputManager().setInMenu(true);
	}

	@Override
	public void exitState() {
		game.getLevel().toggleMobility(false);
		uiManager.setMenu(uiManager.gamePlayUI);
		Game.getInputManager().setInMenu(false);
	}

}
