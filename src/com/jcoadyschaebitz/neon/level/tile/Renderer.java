package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.level.Level;

public interface Renderer {

	public void render(int x, int y, Screen screen, Level level, long seed);
	
}
