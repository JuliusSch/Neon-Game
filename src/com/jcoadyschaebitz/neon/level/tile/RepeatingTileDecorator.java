package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class RepeatingTileDecorator extends Tile {
	
	private int repeatAfterW, repeatAfterH;
	private Sprite[] sprites, sprites1, sprites2, sprites3;
	private boolean rotateRandomly;
	
	public RepeatingTileDecorator(Tile tile, Vec2i repeatAfter, Sprite[] sprites, List<Renderer> renderers, boolean rotateRandomly) {
		super(sprites[0], tile.getColour(), tile.getZ(), renderers);
		if (tile instanceof FloorTile) this.additionalRenderers = FloorTile.addRenderer(renderers, Tile.edgeShadows);
		tile.colour = -1;
		this.repeatAfterW = repeatAfter.x;
		this.repeatAfterH = repeatAfter.y;
		this.sprites = sprites;
//		blocksSightline = tile.blocksSightline;
		blocksProjectiles = tile.blocksProjectiles;
		isOutdoors = tile.isOutdoors;
		castsShadow = tile.castsShadow;
		this.rotateRandomly = rotateRandomly;
		if (rotateRandomly) {
			
		}
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		int i = (x << 4) % repeatAfterW + ((y << 4) % repeatAfterH * repeatAfterH);
		sprite = sprites[i];
		screen.renderTile(x << 4, y << 4, this);
		if (additionalRenderers != null) for (Renderer r : additionalRenderers) {
			r.render(x, y, screen, level, seed);
		}
	}

}
