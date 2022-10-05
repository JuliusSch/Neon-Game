package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Stall extends CollisionEntity {
	
	protected int shadowOffset = 0, yAnchor;

	public Stall(int x, int y, Orientation dir, int spriteNo) {
		super(x, y);
		switch (dir) {
		case DOWN:
			sprite = Sprite.stall1_down;
			shadowSprite = Sprite.stall1_down_shadow;
			shadowOffset = 28;
			int[] xPoints = { 3, 15, 26, 37, 48, 57, 67, 3, 67, 3, 15, 26, 37, 48, 57, 67 };
			int[] yPoints = { 28, 28, 28, 28, 28, 28, 28, 36, 36, 44, 44, 44, 44, 44, 44, 44 };
			int[] xCorners = { 4, 69, 4, 69 };
			int[] yCorners = { 28, 28, 44, 44};
			entityBounds = new CollisionBox(xPoints, yPoints);
			corners = new CollisionBox(xCorners, yCorners);
			yAnchor = getSpriteH();
			break;
		case LEFT:
			sprite = Sprite.stall1_left;
			shadowSprite = Sprite.stall1_left_shadow;
			shadowOffset = 0;
			int[] xPoints1 = { 3, 15, 26, 37, 3, 37, 3, 37, 3, 37, 3, 37, 3, 37, 3, 37, 3, 15, 26, 37  };
			int[] yPoints1 = { 28, 28, 28, 28, 36, 36, 44, 44, 52, 52, 60, 60, 68, 68, 76, 76, 80, 80, 80, 80 };
			int[] xCorners1 = { 4, 37, 4, 37 };
			int[] yCorners1 = { 28, 28, 80, 80};
			entityBounds = new CollisionBox(xPoints1, yPoints1);
			corners = new CollisionBox(xCorners1, yCorners1);
			yAnchor = 84;
			break;
		case RIGHT:
			break;
		case UP:
			break;
		default:
			break;
		}
	}
	
	public int getYAnchor() {
	return (int) y + yAnchor;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + shadowOffset, shadowSprite, true, 0.6);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}

}
