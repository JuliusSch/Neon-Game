package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class SkillsButton extends UIButton {

	private Game game;
	
	public SkillsButton(int x, int y, int width, int height, String label, int fontColour, Game game) {
		super(x, y, width, height, label, fontColour);
		this.game = game;
	}

	public void doFunction() {
		Game.getUIManager().setMenu(game.pauseSkillsMenu);
	}

}
