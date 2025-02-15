package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class ShopMenuState implements GameState {
	
	private Game game;
	private UIManager uiManager;
	
	public ShopMenuState(Game game) {
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
//		ui.setMenu(game.shopMenuUI);
		uiManager.setMenu(uiManager.shopMenu);
		game.getLevel().getPlayer().immobilised = true;
		Game.getInputManager().setInMenu(true);
	}

	@Override
	public void exitState() {
		game.getLevel().getPlayer().immobilised = false;
//		ui.setMenu(game.gamePlayUI);
		Game.getInputManager().setInMenu(false);
	}

}
