package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class VariableWallTile extends WallTile {

	public VariableWallTile(Sprite sprite, int colour, boolean canHaveShadow) {
		super(sprite, colour, canHaveShadow);
	}

	public VariableWallTile(Sprite sprite, boolean canHaveShadow) {
		super(sprite, 0, canHaveShadow);
	}

	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, getSpecificTile(x, y, level));
	}

	public Tile getSpecificTile(int x, int y, Level level) {
		Tile result = Tile.wall;
		if (!level.getTile(x, y + 1).isSolid()) result = Tile.wallDarkTransitionTop;
		if (!level.getTile(x, y - 1).isSolid()) result = Tile.wallDarkTransitionBottom;
		if (!level.getTile(x + 1, y).isSolid()) result = Tile.wallDarkTransitionLeft;
		if (!level.getTile(x - 1, y).isSolid()) result = Tile.wallDarkTransitionRight;
		// if (!level.getTile(x, y + 1).isSolid() && !level.getTile(x + 1, y).isSolid()) result = Tile.wallDarkTransitionBottomRightOpen;
		// if (!level.getTile(x, y + 1).isSolid() && !level.getTile(x - 1, y).isSolid()) result = Tile.wallDarkTransitionBottomLeftOpen;
		if (!level.getTile(x, y - 1).isSolid() && !level.getTile(x + 1, y).isSolid()) result = Tile.wallDarkTransitionTopRightOpen;
		if (!level.getTile(x, y - 1).isSolid() && !level.getTile(x - 1, y).isSolid()) result = Tile.wallDarkTransitionTopLeftOpen;
		return result;
	}

}
