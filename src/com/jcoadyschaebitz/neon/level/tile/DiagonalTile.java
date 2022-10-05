package com.jcoadyschaebitz.neon.level.tile;

import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Vec2d;

public class DiagonalTile extends FloorTile {
	
	public DiagDirection dir;

	public DiagonalTile(Sprite sprite, int colour, DiagDirection dir, List<Renderer> renderers) {
		super(sprite, colour, renderers);
		this.dir = dir;
	}
	
	public enum DiagDirection {
		TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT;		//indicates corner that is solid;
	}
	
	public boolean isDiagonal() {
		return true;
	}
	
	public static boolean checkDiagCollision(DiagDirection dir, double xa, double ya) {
		switch (dir) {
		case TOPLEFT:
			if (xa + ya < 16) return true;
			break;
		case TOPRIGHT:
			if (xa < ya) return true;
			break;
		case BOTTOMLEFT:
			if (xa > ya) return true;
			break;
		case BOTTOMRIGHT:
			if (xa + ya > 16) return true;
			break;
		default:
			break;	
		}
		return false;
	}
	
	public static Vec2d updateMove(DiagDirection dir, double xa, double ya) {
		Vec2d result = new Vec2d(xa, ya);
		switch (dir) {
		case TOPLEFT:
			if (xa < 0 && ya < 0) result.set(0, 0);
			if (xa < 0 && ya == 0) result.set(xa / 2, Math.abs(xa) / 2);
			if (xa == 0 && ya < 0) result.set(Math.abs(ya / 2), ya / 2);
			break;
		case TOPRIGHT:
			if (xa > 0 && ya < 0) result.set(0, 0);
			if (xa > 0 && ya == 0) result.set(xa / 2, xa / 2);
			if (xa == 0 && ya < 0) result.set(ya / 2, ya / 2);
			break;
		case BOTTOMLEFT:
			if (xa < 0 && ya > 0) result.set(0, 0);
			if (xa < 0 && ya == 0) result.set(xa / 2, xa / 2);
			if (xa == 0 && ya > 0) result.set(ya / 2, ya / 2);
			break;
		case BOTTOMRIGHT:
			if (xa > 0 && ya > 0) result.set(0, 0);
			if (xa > 0 && ya == 0) result.set(xa / 2, 0 - (xa / 2));
			if (xa == 0 && ya > 0) result.set(0 - (ya / 2), ya / 2);
			break;
		default:
			break;	
		}
		return result;
	}

}
