package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class CutSceneState implements GameState {
	
	Game game;
	UIManager ui;
	CutScene currentScene;
	
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
		Game.getInputManager().update();
		game.getLevel().update();
		ui.update();
	}

	@Override
	public void render(Screen screen, double xScroll, double yScroll) {
		game.getLevel().render(currentScene.getX(), currentScene.getY(), screen, game.getLevel());
	}

	@Override
	public void enterState() {
		for (SoundClip clip : SoundClip.allClips) {
			clip.resume();
		}
		ui.setMenu(Game.getUIManager().cutSceneUI);
		Game.getInputManager().setInMenu(true);
	}

	@Override
	public void exitState() {
		for (SoundClip clip : SoundClip.allClips) {
			clip.pause();
		}
		Game.getInputManager().setInMenu(false);
	}

}
