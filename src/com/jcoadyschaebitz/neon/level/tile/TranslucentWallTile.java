package com.jcoadyschaebitz.neon.level.tile;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class TranslucentWallTile extends WallTile {
	
	Tile tile;
	List<Sprite> sprites;
	
	public TranslucentWallTile(int colour, boolean canHaveShadow, List<Renderer> renderers) {
		super(Sprite.fakeWallLeft, colour, canHaveShadow, renderers);
		int tc = ((colour - 0xaa000000) + 0xff000000);
//		System.out.println(Integer.toHexString(colour) + ", " + Integer.toHexString(tc));
		for (Tile t : Tile.tiles) {
			if (t.getColour() == tc) {
				this.tile = t;
				this.castsShadow = t.castsShadow;
				return;
			}
		}
	}
	
	public int isStair() {
		return tile.isStair();
	}
	
	public void render(int x, int y, Screen screen, Level level, long seed) {
		screen.renderTile(x << 4, y << 4, tile);
	}
	
	
	public void renderTranslucentWall(int x, int y, int width, int height, Screen screen, Level level, boolean[] wallMap) {
		sprites = getSprites(x, y, wallMap, width, height);
		for (Sprite sprite : sprites) {
			screen.renderTranslucentSprite(x << 4, y << 4, sprite, true);
		}
		//detect cursor and player in radius, turn translucent if either are on translucent wall tile within radius
	}
	
	private List<Sprite> getSprites(int x, int y, boolean[] wallMap, int width, int height) {
		int gridSize = 3;
		List<Sprite> result = new ArrayList<Sprite>();
		boolean[] x3Map = new boolean[gridSize * gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				x3Map[j * gridSize + i] = wallMap[(y + j - 1) * width + (x + i - 1)];
			}
		}
		if (!x3Map[1]) result.add(Sprite.fakeWallTop);
		if (!x3Map[7]) result.add(Sprite.fakeWallBottom);
		if (!x3Map[3]) result.add(Sprite.fakeWallLeft);
		if (!x3Map[5]) result.add(Sprite.fakeWallRight);
		return result;
	}
	
	public boolean border() {
		return true;
	}
	
	public boolean isSolid() {
		 return false;
	}
	
}
