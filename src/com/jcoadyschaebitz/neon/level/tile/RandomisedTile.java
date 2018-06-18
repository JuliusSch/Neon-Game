package com.jcoadyschaebitz.neon.level.tile;

import java.util.Random;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class RandomisedTile extends Tile {

	Random random;

	private Sprite[] sprites;

	public RandomisedTile(int colour, boolean canHaveShadow, Sprite[] sprites, int zIndex) {
		super(Sprite.dirt, colour, canHaveShadow, zIndex);
		this.sprites = sprites;
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		random = new Random(seed * (x + 1 << 4) / (y + 1 << 4));
		int i = (int) (random.nextDouble() * x * y) % sprites.length;
		sprite = sprites[i];
		screen.renderTile(x << 4, y << 4, this);
	}

}
