package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Sprite;

public class LowerWallTile extends WallTile {

	public LowerWallTile(Sprite sprite, int colour, boolean border, List<Renderer> renderers) {
		super(sprite, colour, border, renderers);
		blocksProjectiles = false;
	}

}
