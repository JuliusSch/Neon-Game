package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class PlayActiveState implements GameState {
	
	Game game;
	UIManager ui;
	
	public PlayActiveState(Game game) {
		this.game = game;
		ui = Game.getUIManager();
	}

	@Override
	public void update() {
		game.getKeyboard().update();
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
		ui.setMenu(game.gamePlayUI);
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
