package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class ParkedCar extends CollisionEntity {

	public ParkedCar(int x, int y, Direction dir) {
		super(x, y);
		switch (dir) {
		case DOWN: 
			sprite = Sprite.car_down;
			shadowSprite = Sprite.car_down_shadow;
			int[] xPoints = { 7, 41, 7, 41, 18, 30, 7, 41, 7, 41, 7, 41, 7, 41, 7, 41, 7, 41, 7, 41, 18, 30 };
			int[] yPoints = { 7, 7, 66, 66, 7, 7, 15, 15, 23, 23, 31, 31, 39, 39, 47, 47, 55, 55, 60, 60, 66, 66 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 7, 41, 7, 41 };
			int[] yCorners = { 24, 24, 45, 45 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case LEFT:
			sprite = Sprite.car_left;
			shadowSprite = Sprite.car_left_shadow;
			int[] xPoints1 = { 8, 18, 28, 38, 48, 57, 66, 8, 18, 28, 38, 48, 57, 66, 8, 66, 8, 66 };
			int[] yPoints1 = { 12, 12, 12, 12, 12, 12, 12, 38, 38, 38, 38, 38, 38, 38, 20, 20, 29, 29 };
			entityBounds = new CollisionBox(xPoints1, yPoints1);
			int[] xCorners1 = { 22, 42, 22, 42 };
			int[] yCorners1 = { 8, 8, 32, 32 };
			corners = new CollisionBox(xCorners1, yCorners1);
			break;
		case RIGHT:
			break;
		case UP:
			break;
		default:
			break;
		}
	}

}
