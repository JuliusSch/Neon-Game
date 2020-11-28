package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Divider_1 extends CollisionEntity {

	public Divider_1(int x, int y, Or_2D or) {
		super(x, y);
		int[] xPoints, yPoints, xCorners, yCorners;
		switch(or) {
		case HORIZONTAL:
			sprite = Sprite.divider_1_h;
			shadowSprite = Sprite.nullSprite;
			xPoints = new int[]{ 1, 13, 23, 33, 47, 1, 13, 23, 33, 47 };
			yPoints = new int[]{ 27, 27, 27, 27, 27, 29, 29, 29, 29, 29 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			xCorners = new int[]{ 1, 47, 1, 47 };
			yCorners = new int[]{ 31, 31, 33, 33 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case VERTICAL:
			sprite = Sprite.divider_1_v;
			shadowSprite = Sprite.nullSprite;
			xPoints = new int[]{ 5, 5, 5, 5, 5, 5, 5, 9, 9, 9, 9, 9, 9, 9 };
			yPoints = new int[]{ 16, 21, 26, 31, 36, 42, 47, 16, 21, 26, 31, 36, 42, 47 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			xCorners = new int[]{ 5, 9, 5, 9 };
			yCorners = new int[]{ 16, 16, 47, 47 };
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
