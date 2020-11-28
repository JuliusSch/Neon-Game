package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BarCounter extends CollisionEntity {

	public BarCounter(int x, int y) {
		super(x, y);
		sprite = Sprite.barCounter;
		shadowSprite = Sprite.nullSprite;
		int[] xPoints = { 1, 11, 21, 31, 41, 51, 61, 71, 81, 91, 101, 111, 121, 134, 134, 134, 134, 134, 134, 134 };
		int[] yPoints = { 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 21, 26, 31, 36, 41, 46 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { 1, 134, 1, 134 };
		int[] yCorners = { 1, 1, 55, 55 };
		corners = new CollisionBox(xCorners, yCorners);
	}
	
	public int getYAnchor() {
		return (int) y;
	}

}
