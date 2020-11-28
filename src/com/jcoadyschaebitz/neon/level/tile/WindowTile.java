package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class WindowTile extends Tile {
	
	private boolean solid;

	public WindowTile(Sprite sprite, int colour, int zIndex) {
		super(sprite, colour, zIndex, null);
	}

	public boolean blocksProjectiles() {
		return solid;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		// ADD: perspective shifting backdrop, via level somehow.
		screen.renderTranslucentSprite(x << 4, y << 4, sprite, true);
	}
	
}
