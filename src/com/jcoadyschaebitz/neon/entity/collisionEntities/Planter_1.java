package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Planter_1 extends CollisionEntity {

	public Planter_1(int x, int y, Or_2D or) {
		super(x, y);
		int[] xPoints, yPoints, xCorners, yCorners;
		switch (or) {
		case HORIZONTAL:
			sprite = Sprite.planter_1_h;
			shadowSprite = Sprite.nullSprite;
			xPoints = new int[] { 7, 7, 7, 12, 21, 31, 41, 50, 56, 7, 12, 21, 31, 41, 50, 56, 56, 56 };
			yPoints = new int[] { 8, 15, 24, 8, 8, 8, 8, 8, 8, 29, 29, 29, 29, 29, 29, 29, 15, 24 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			xCorners = new int[] { 7, 56, 7, 56 };
			yCorners = new int[] { 8, 8, 29, 29 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case VERTICAL:
			sprite = Sprite.planter_1_v;
			shadowSprite = Sprite.nullSprite;
			xPoints = new int[] { 8, 16, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 16 };
			yPoints = new int[] { 8, 8, 16, 21, 26, 31, 36, 42, 47, 54, 60, 8, 16, 21, 26, 31, 36, 42, 47, 54, 60, 60 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			xCorners = new int[] { 8, 25, 8, 25 };
			yCorners = new int[] { 8, 8, 60, 60 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		default:
			break;
		}
	}
	
	public int getYAnchor() {
		return (int) y + 36;
	}

}
