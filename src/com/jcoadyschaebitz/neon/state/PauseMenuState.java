package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class PauseMenuState implements GameState {

	Game game;
	UIManager ui;
//	public int lastMouseX, lastMouseY;

	public PauseMenuState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	@Override
	public void update() {
		ui.update();
		Game.getKeyboard().update();
	}

//	public void recordMouse(int x, int y) {
//		lastMouseX = x;
//		lastMouseY = y;
//	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
//		screen.renderSprite(0, 0, Sprite.pauseMenuBackground, false);
		game.playState.render(screen, (int) xScroll, (int) yScroll);
		screen.renderTranslucentSprite(0, 0, new Sprite(640, 360, 0xff000000), false, 0.6);
		screen.renderSprite(81, 27, Sprite.menuOutline, false);
	}

	@Override
	public boolean canScrollWeapons() {
		return false;
	}

	@Override
	public void enterState() {
		ui.setMenu(game.pauseSettingsMenu);
//		pauseState.recordMouse(Mouse.getX(), Mouse.getY());
	}

	@Override
	public void exitState() {
//		Mouse.move(pauseState.lastMouseX, pauseState.lastMouseY);
	}

}
