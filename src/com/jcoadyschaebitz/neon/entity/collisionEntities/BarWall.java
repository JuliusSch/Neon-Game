package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BarWall extends CollisionEntity {

	public BarWall(int x, int y) {
		super(x, y);
		sprite = Sprite.barWall;
		shadowSprite = Sprite.nullSprite;
		int[] xPoints = { 0 };
		int[] yPoints = { 0 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { 1, 144, 1, 144 };
		int[] yCorners = { 1, 1, 48, 48 };
		corners = new CollisionBox(xCorners, yCorners);
	}

}
