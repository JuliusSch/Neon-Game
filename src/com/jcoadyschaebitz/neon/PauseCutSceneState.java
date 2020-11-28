package com.jcoadyschaebitz.neon;

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
		game.getKeyboard().update();
		if (ui.currentMenu != game.pauseSkillsMenu) {
			ui.setMenu(game.pauseSkillsMenu);
		}
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render(currentScene.getX(), currentScene.getY(), screen, game.getLevel());
		screen.renderTranslucentSprite(0, 0, new Sprite(400, 250, 0xff000000), false, 0.6);
		screen.renderSprite(81, 27, Sprite.menuOutline, false);
	}

}
