package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class KitchenUnit extends CollisionEntity {

	public KitchenUnit(int x, int y, Orientation2D direction) {
		super(x, y);
		if (direction == Orientation2D.HORIZONTAL) {
			sprite = Sprite.kitchen_unit_h;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints = { 4, 14, 23, 33, 43, 4, 14, 23, 33, 43, 4, 4, 4, 4, 43, 43, 43, 43 };
			int[] yPoints = { 12, 12, 12, 12, 12, 39, 39, 39, 39, 39, 15, 21, 27, 33, 15, 21, 27, 33 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 5, 42, 5, 42 };
			int[] yCorners = { 12, 12, 42, 42 };
			corners = new CollisionBox(xCorners, yCorners);
		} else {
			sprite = Sprite.kitchen_unit_v;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints = { 9, 19, 28, 38, 9, 19, 28, 38, 9, 9, 9, 9, 38, 38, 38, 38 };
			int[] yPoints = { 8, 8, 8, 8, 40, 40, 40, 40, 14, 20, 26, 33, 14, 20, 26, 33 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 9, 38, 9, 38 };
			int[] yCorners = { 8, 8, 40, 40 };
			corners = new CollisionBox(xCorners, yCorners);
		}
	}

}
