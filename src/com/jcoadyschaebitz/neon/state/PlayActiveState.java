package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class PlayActiveState implements GameState {
	
	Game game;
	UIManager uiManager;
	
	public PlayActiveState(Game game) {
		this.game = game;
		uiManager = Game.getUIManager();
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
	public boolean canScrollWeapons() {
		return true;
	}

	@Override
	public void enterState() {
		uiManager.setMenu(uiManager.gamePlayUI);
		for (SoundClip clip : SoundClip.allClips) {
			clip.resume();
		}
	}

	@Override
	public void exitState() {
		for (SoundClip clip : SoundClip.allClips) {
			clip.pause();
		}
	}
}
