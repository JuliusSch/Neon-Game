package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class WallTile extends Tile {
	
	public WallTile(Sprite sprite, int colour, boolean canHaveShadow) {
		super(sprite, colour, canHaveShadow, 1);
	}
	
	public WallTile(Sprite sprite, boolean canHaveShadow, int zIndex) {
		super(sprite, 0, canHaveShadow, zIndex);
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean isSolid() {
		 return true;
	}
}
