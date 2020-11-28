package com.jcoadyschaebitz.neon;

import com.jcoadyschaebitz.neon.graphics.Screen;

public interface GameState {
	
	public boolean canScrollWeapons();
	
	public void update();
	
	public void render(Screen screen, double xScroll, double yScroll);
	
}
