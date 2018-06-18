package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class FloorTile extends Tile {
	
	public FloorTile(Sprite sprite, int colour, boolean canHaveShadow, boolean isOutdoors) {
		super(sprite, colour, canHaveShadow, 0);
		this.isOutdoors = isOutdoors;
	}
	
	public FloorTile(Sprite sprite, boolean canHaveShadow, boolean isOutdoors, int zIndex) {
		super(sprite, 0, canHaveShadow, zIndex);
		this.isOutdoors = isOutdoors;
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
