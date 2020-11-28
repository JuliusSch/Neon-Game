package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class CutSceneState implements GameState {
	
	Game game;
	UIManager ui;
	CutScene currentScene;
	private double xScroll, yScroll;
	
	public CutSceneState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}
	
	public void setScene(CutScene scene) {
		currentScene = scene;
	}

	@Override
	public boolean canScrollWeapons() {
		return false;
	}

	@Override
	public void update() {
		currentScene.setScrollValues(xScroll, yScroll);
		game.getKeyboard().update();
		game.getLevel().update();
		if (ui.currentMenu != game.cutSceneUI) {
			ui.setMenu(game.cutSceneUI);
		}
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render(currentScene.getX(), currentScene.getY(), screen, game.getLevel());
		this.xScroll = xScroll;
		this.yScroll = yScroll;
	}

}
