package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class voidTile extends Tile {

	public voidTile(Sprite sprite) {
		super(sprite, 0, false, 1);
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean isSolid() {
		return true;
	}

}
