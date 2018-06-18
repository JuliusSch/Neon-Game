package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;

public class RepeatingTile extends Tile {
	
	int repeatAfterW, repeatAfterH;
	private Sprite[] sprites;
	
	public RepeatingTile(int colour, boolean canHaveShadow, int repeatAfter, Spritesheet spritesheet) {
		this(colour, canHaveShadow, repeatAfter, repeatAfter, spritesheet);
	}
	
	public RepeatingTile(int colour, boolean canHaveShadow, int repeatAfterW, int repeatAfterH, Spritesheet spritesheet) {
		super(Sprite.dirt, colour, canHaveShadow, 1);
		this.repeatAfterW = repeatAfterW;
		this.repeatAfterH = repeatAfterH;
		this.sprites = spritesheet.getSprites();
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		int i = (x << 4) % repeatAfterW + ((y << 4) % repeatAfterH * repeatAfterH);
		sprite = sprites[i];
		screen.renderTile(x << 4, y << 4, this);
	}

}
