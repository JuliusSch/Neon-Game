package com.jcoadyschaebitz.neon.graphics.UI.skillTrees;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.UI.UIComp;
import com.jcoadyschaebitz.neon.graphics.UI.UIManager;

public class ActionSkillDisplay implements UIComp {
	
	public boolean actionSkillActive;
	private int backgroundColour, foregroundColour, x, y, totalCooldown;
	private double timeTilUse;

	public ActionSkillDisplay(int x, int y, UIManager ui) {
		backgroundColour = 0xffaa451a;
		foregroundColour = 0xff55996f;
		this.x = x;
		this.y = y;
	}

	public void update() {
		
	}
	
	public void updateStats(boolean active, int ttu, int tts, int tcd) {
		actionSkillActive = active; 
		timeTilUse = ttu;
		totalCooldown = tcd;
	}
	
	public void render(Screen screen) {
		int height;
		height = 20 - (int) ((timeTilUse / totalCooldown) * 20);				//temporary
		screen.drawRect(true, x, y, 20, 20, backgroundColour, false);
		if (height == 0) return;
		screen.drawRect(true, x, y + 20 - height, 20, height, foregroundColour, false);
	}

}
