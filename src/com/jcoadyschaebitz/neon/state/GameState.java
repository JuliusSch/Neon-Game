package com.jcoadyschaebitz.neon.state;

import com.jcoadyschaebitz.neon.graphics.Screen;

public interface GameState {
	
	public boolean canScrollWeapons();
	
	public void update();
	
	public void render(Screen screen, double xScroll, double yScroll);
	
	public void enterState();
	
	public void exitState();
	
}
