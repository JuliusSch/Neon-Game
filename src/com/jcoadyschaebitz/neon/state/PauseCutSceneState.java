package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class PauseCutSceneState implements GameState {

	Game game;
	UIManager ui;
	CutScene currentScene;
	
	public PauseCutSceneState(Game game) {
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
		ui.update();
		Game.getKeyboard().update();
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render(currentScene.getX(), currentScene.getY(), screen, game.getLevel());
		screen.renderTranslucentSprite(0, 0, new Sprite(640, 360, 0xff000000), false, 0.6);
		screen.renderSprite(81, 27, Sprite.menuOutline, false);
	}

	@Override
	public void enterState() {
		ui.setMenu(game.pauseSettingsMenu);
	}

	@Override
	public void exitState() {
	}

}
