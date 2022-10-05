package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;
import java.util.Random;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class RandomisedTileDecorator extends Tile {

	private Sprite[] sprites;
	private Random random;
	
	public RandomisedTileDecorator(Tile tile, Sprite[] sprites, List<Renderer> renderers) {
		super(sprites[0], tile.getColour(), tile.getZ(), renderers);
		if (tile instanceof FloorTile) this.additionalRenderers = FloorTile.addRenderer(renderers, Tile.edgeShadows);
		tile.colour = -1;
		this.sprites = sprites;
//		blocksSightline = tile.blocksSightline;
		blocksProjectiles = tile.blocksProjectiles;
		isOutdoors = tile.isOutdoors;
		castsShadow = tile.castsShadow;
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		random = new Random(seed * (x + 1 << 4) / (y + 1 << 4));
		int i = (int) (random.nextDouble() * x * y) % sprites.length;
		sprite = sprites[i];
		screen.renderTile(x << 4, y << 4, this);
		if (additionalRenderers != null) for (Renderer r : additionalRenderers) {
			r.render(x, y, screen, level, seed);
		}
	}

}
