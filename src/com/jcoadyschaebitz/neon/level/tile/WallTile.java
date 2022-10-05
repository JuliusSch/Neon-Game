package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;
import java.util.Random;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class WallTile extends Tile {
	
	Random random;
	
	public WallTile(Sprite sprite, int colour, List<Renderer> renderers) {
		super(sprite, colour, 1, renderers);
		blocksProjectiles = true;
//		blocksSightline = true;
		dirtBaseSprites = new Sprite[4];
		dirtBaseSprites[0] = Sprite.wallBaseDirt1;
		dirtBaseSprites[1] = Sprite.wallBaseDirt2;
		dirtBaseSprites[2] = Sprite.wallBaseDirt3;
		dirtBaseSprites[3] = Sprite.wallBaseDirt4;
	}
	
//	public WallTile(Sprite sprite, int zIndex, Renderer[] renderers) {
//		this(sprite, 0, renderers);
//	}
	
	public WallTile(Sprite sprite, int colour, boolean border, List<Renderer> renderers) {
		this(sprite, colour, renderers);
		this.border = border;
	}
	
	public void renderDirt(int x, int y, Screen screen, Level level, long seed) {
		random = new Random(seed * (x + 1 << 4) / (y + 1 << 4));
		int num = random.nextInt(dirtBaseSprites.length);
		screen.renderSprite(x << 4, y << 4, dirtBaseSprites[num], true);
	}
}
