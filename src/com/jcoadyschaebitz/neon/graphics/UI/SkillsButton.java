package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.Game;

public class SkillsButton extends UIButton {
	
	public SkillsButton(int x, int y, int width, int height, String label, int fontColour, Game game) {
		super(x, y, width, height, label, fontColour);
	}

	public void doFunction() {
		Game.getUIManager().setMenu(Game.getUIManager().getGame().shopMenu);
	}
}
