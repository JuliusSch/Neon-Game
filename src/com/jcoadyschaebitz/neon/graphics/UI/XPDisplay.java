package com.jcoadyschaebitz.neon.graphics.UI;

import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class XPDisplay implements UIComp {
	
	private int playerXPProgress, playerLevel;
	private Font font;
	private int x, y;

	public XPDisplay(int x, int y, UIManager ui) {
		font = new Font(Font.SIZE_8x8, 0xff00FFFF);
		this.x = x;
		this.y = y;
	}

	public void update() {
		
	}
	
	public void setValues(int progress, int level) {
		playerXPProgress = progress;
		playerLevel = level;
	}
	
	public void render(Screen screen) {
		int xPlus = (playerXPProgress * 30) / 100;
		screen.drawRect(true, x, y, xPlus, 9, 0xffADFFFF, false);
		if (playerLevel > 9) font.render(x + 5, y + 1, Integer.toString(playerLevel), screen, false);
		else font.render(x + 6, y + 1, Integer.toString(playerLevel), screen, false);
	}
	
}
