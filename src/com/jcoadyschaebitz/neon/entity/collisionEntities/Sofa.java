package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Sofa extends CollisionEntity {

	public Sofa(int x, int y, Orientation ori) {
		super(x, y);
		blocksProjectiles = false;
		switch (ori) {
		case LEFT:
			sprite = Sprite.sofa_left;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints = { 1, 11, 23, 1, 11, 23, 1, 23, 1, 23, 1, 23 };
			int[] yPoints = { 6, 6, 6, 42, 42, 42, 15, 15, 24, 24, 33, 33 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 1, 23, 1, 23 };
			int[] yCorners = { 6, 6, 42, 42 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case RIGHT:
			sprite = Sprite.sofa_right;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints2 = { 0, 11, 22, 0, 11, 22, 0, 22, 0, 22, 0, 22 };
			int[] yPoints2 = { 6, 6, 6, 42, 42, 42, 15, 15, 24, 24, 33, 33 };
			entityBounds = new CollisionBox(xPoints2, yPoints2);
			int[] xCorners2 = { 0, 22, 0, 22 };
			int[] yCorners2 = { 6, 6, 42, 42 };
			corners = new CollisionBox(xCorners2, yCorners2);
			break;
		case DOWN:
			sprite = Sprite.sofa_down;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints3 = { 0, 12, 24, 36, 47, 0, 12, 24, 36, 47, 0, 47 };
			int[] yPoints3 = { 4, 4, 4, 4, 4, 19, 19, 19, 19, 19, 12, 12 };
			entityBounds = new CollisionBox(xPoints3, yPoints3);
			int[] xCorners3 = { 0, 0, 47, 47 };
			int[] yCorners3 = { 4, 19, 4, 19 };
			corners = new CollisionBox(xCorners3, yCorners3);
			break;
		case UP:
			sprite = Sprite.sofa_up;
			shadowSprite = Sprite.nullSprite;
			int[] xPoints4 = { 0, 12, 24, 36, 47, 0, 12, 24, 36, 47, 0, 47 };
			int[] yPoints4 = { 4, 4, 4, 4, 4, 19, 19, 19, 19, 19, 12, 12 };
			entityBounds = new CollisionBox(xPoints4, yPoints4);
			int[] xCorners4 = { 0, 0, 47, 47 };
			int[] yCorners4 = { 4, 19, 4, 19 };
			corners = new CollisionBox(xCorners4, yCorners4);
			break;
		default:
			break;
		}
	}

	public int getYAnchor() {
		return (int) y + 12;
	}
	
}
