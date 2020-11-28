package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class InteriorGuardRail extends CollisionEntity {

	public InteriorGuardRail(int x, int y, Orientation or) {
		super(x, y);
		int[] xPoints = {}, yPoints = {}, xCorners = {}, yCorners = {};
		shadowSprite = Sprite.nullSprite;
		switch (or) {
		case LEFT:
			xPoints = new int[]{ -1, 3, -1, 3, -1, 3, -1, 3 };
			yPoints = new int[]{ 16, 16, 21, 21, 26, 26, 31, 31 };
			xCorners = new int[]{ -1, 3, -1, 3 };
			yCorners = new int[]{ 0, 0, 31, 31 };
			sprite = Sprite.guardRail;
			break;
		case RIGHT:
			xPoints = new int[]{ 17, 13, 17, 13, 17, 13, 17, 13 };
			yPoints = new int[]{ 16, 16, 21, 21, 26, 26, 31, 31 };
			xCorners = new int[]{ 17, 13, 17, 13 };
			yCorners = new int[]{ 0, 0, 31, 31 };
			sprite = Sprite.mirror(Sprite.guardRail);
			break;
		case DOWN:
			break;
		default:
			break;
		}
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xCorners, yCorners);
	}

	public int getYAnchor() {
		return (int) y + 20;
	}

}
