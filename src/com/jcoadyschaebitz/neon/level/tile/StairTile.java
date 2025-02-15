package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class StairTile extends FloorTile {
	
	int dir = StairTile.LEFT;
	public static final int NOT_A_STAIR = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	
	public StairTile(Sprite sprite, int colour, int dir, List<Renderer> renderers) {
		super(sprite, colour, renderers);
		this.dir = dir;
	}
	
	public int isStair() {
		return dir;
	}
	
	public static double changeXa(Level level, double xa, double ya, int x, int y, int dir) {
		switch (dir) {
		case UP:
			return xa * 0.7;
		case DOWN:
			return xa * 0.7;
		case LEFT:
			return xa * 0.7;
		case RIGHT:
			return xa * 0.7;
		default:
			return xa;
		}
	}
	
	public static double changeYa(Level level, double xa, double ya, int x, int y, int dir) {
		switch (dir) {
		case UP:
			return ya * 0.4;
		case DOWN:
			return ya * 0.7;
		case RIGHT:
			return (ya * 0.5) + (xa * 0.7);
		case LEFT:
			return (ya * 0.5) - (xa * 0.7);
		default:
			return ya;
		}
	}
}
