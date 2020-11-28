package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Sprite;

public class FloorTile extends Tile {
	
	public FloorTile(Sprite sprite, int colour, Renderer[] renderers) {
		super(sprite, colour, 0, renderers);
//		blocksSightline = false;
		blocksProjectiles = false;
		castsShadow = false;
	}

}
