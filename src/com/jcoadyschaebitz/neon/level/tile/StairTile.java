package com.jcoadyschaebitz.neon.level.tile;

import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class StairTile extends FloorTile {
	
	private Direction dir;

	public StairTile(Sprite sprite, int colour, boolean canHaveShadow, boolean isOutdoors, Direction dir) {
		super(sprite, colour, canHaveShadow, isOutdoors);
		this.dir = dir;
	}
	
	public Direction getDir() {
		return dir;
	}
	
	public static double changeXa(Level level, double xa, double ya, int x, int y) {
		Direction dir2;
		try {
			dir2 = ((StairTile) level.getTile(x, y)).getDir();
		} catch (ClassCastException e) {
			return xa;
		}
		switch (dir2) {
		case UP:
			return xa * 0.7;
		case DOWN:
			return xa + 0.7;
		case LEFT:
			return xa * 0.7;
		case RIGHT:
			return xa * 0.7;
		default:
			return xa;
		}
	}
	
	public static double changeYa(Level level, double xa, double ya, int x, int y) {
		Direction dir2;
		try {
			dir2 = ((StairTile) level.getTile(x, y)).getDir();
		} catch (ClassCastException e) {
			return ya;
		}
		switch (dir2) {
		case UP:
			return ya * 0.7;
		case DOWN:
			return ya * 0.7;
		case LEFT:
			return (ya + xa) * 0.7;
		case RIGHT:
			return (ya - xa) * 0.7;
		default:
			return ya;
		}
	}

}
