package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Bin extends CollisionEntity {

	public Bin(int x, int y, Orientation dir) {
		super(x, y);
		switch (dir) {
		case DOWN:
			sprite = Sprite.down_facing_bin;
			shadowSprite = Sprite.down_facing_bin_shadow;
			int[] xPoints = { 1, 1, 1, 1, 10, 20, 30, 39, 1, 10, 20, 30, 39, 39, 39, 39, 39 };
			int[] yPoints = { 1, 7, 14, 20, 1, 1, 1, 1, 24, 24, 24, 24, 1, 7, 14, 20, 24 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 1, 30, 1, 30 };
			int[] yCorners = { 1, 1, 24, 24 };
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case LEFT:
			sprite = Sprite.left_facing_bin;
			shadowSprite = Sprite.left_facing_bin_shadow;
			int[] xPoints1 = { 2, 12, 21, 2, 12, 21, 2, 2, 2, 21, 21, 21 };
			int[] yPoints1 = { 12, 12, 12, 40, 40, 40, 20, 28, 36, 20, 28, 36 };
			entityBounds = new CollisionBox(xPoints1, yPoints1);
			int[] xCorners1 = { 3, 20, 3, 20 };
			int[] yCorners1 = { 7, 7, 40, 40 };
			corners = new CollisionBox(xCorners1, yCorners1);
			break;
		case RIGHT:
			sprite = Sprite.right_facing_bin;
			shadowSprite = Sprite.left_facing_bin_shadow;
			int[] xPoints2 = { 2, 12, 21, 2, 12, 21, 2, 2, 2, 21, 21, 21 };
			int[] yPoints2 = { 12, 12, 12, 40, 40, 40, 20, 28, 36, 20, 28, 36 };
			entityBounds = new CollisionBox(xPoints2, yPoints2);
			int[] xCorners2 = { 3, 20, 3, 20 };
			int[] yCorners2 = { 7, 7, 40, 40 };
			corners = new CollisionBox(xCorners2, yCorners2);
			break;
		case UP:
			break;
		default:
			break;
		}
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 9, shadowSprite, true, 0.4);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		corners.renderBounds(screen, 0xffff00ff, (int) x, (int) y); 
	}
}
