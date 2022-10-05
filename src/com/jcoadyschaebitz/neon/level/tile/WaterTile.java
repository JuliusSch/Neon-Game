package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class WaterTile extends Tile {
	
	private Sprite water;
	public static int FULL = 0, TOP_EDGE = 1;
	private int type;
	
	public WaterTile(int colour, Sprite waterSprite, Sprite tileSprite, int type, List<Renderer> renderers) {
		super(tileSprite, colour, 1, renderers);
		water = waterSprite;
		this.type = type;
		zIndex = 1;
		blocksProjectiles = false;
	}
	
	private Sprite calcWaveMask(int xx, int waterLine, double strength, int time) {
		waterLine = 16 - waterLine;
		int out[] = new int[16 * 16];
		for (int dy = 0; dy < 16; dy++) {
			for (int dx = 0; dx < 16; dx++) {
				out[dx + dy * 16] = Screen.ALPHA_COLOUR;
				double sinVal = ((dx + 16 * xx) * 2 * Math.PI / 64) + (time / 6);
				double h = (strength * Math.sin(sinVal));
				if (h + waterLine <= dy) out[dx + dy * 16] = 0xff000000;
			}
		}
		return (new Sprite(out, 16, 16));
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, this);
		Sprite spr = water;
		if (type == WaterTile.TOP_EDGE) spr = water.mask(calcWaveMask(x, 14, 1.5, level.getPlayer().game.getElapsedTime()), 0xff000000, 0, 0);
		
		screen.renderTranslucentSprite(x << 4, y << 4, spr, true);
	}
	
}
