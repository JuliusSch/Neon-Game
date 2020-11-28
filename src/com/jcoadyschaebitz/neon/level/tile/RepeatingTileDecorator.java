package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class RepeatingTileDecorator extends Tile {
	
	int repeatAfterW, repeatAfterH;
	private Sprite[] sprites;
	
	public RepeatingTileDecorator(Tile tile, Vec2i repeatAfter, Spritesheet sheet, Renderer[] renderers) {
		super(sheet.getSprites()[0], tile.getColour(), tile.getZ(), renderers);
		tile.colour = -1;
		this.repeatAfterW = repeatAfter.X();
		this.repeatAfterH = repeatAfter.Y();
		this.sprites = sheet.getSprites();
//		blocksSightline = tile.blocksSightline;
		blocksProjectiles = tile.blocksProjectiles;
		isOutdoors = tile.isOutdoors;
		castsShadow = tile.castsShadow;
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		int i = (x << 4) % repeatAfterW + ((y << 4) % repeatAfterH * repeatAfterH);
		sprite = sprites[i];
		screen.renderTile(x << 4, y << 4, this);
	}

}
