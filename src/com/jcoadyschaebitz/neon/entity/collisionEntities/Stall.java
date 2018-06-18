package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Stall extends CollisionEntity {

	public Stall(int x, int y, Direction dir, int spriteNo) {
		super(x, y);
		switch (dir) {
		case DOWN:
			sprite = Sprite.stall1_down;
			shadowSprite = Sprite.stall1_down_shadow;
			int[] xPoints1 = { 3, 15, 26, 37, 48, 57, 67, 3, 67, 3, 15, 26, 37, 48, 57, 67 };
			int[] yPoints1 = { 28, 28, 28, 28, 28, 28, 28, 36, 36, 44, 44, 44, 44, 44, 44, 44 };
			int[] xCorners1 = { 4, 69, 4, 69 };
			int[] yCorners1 = { 28, 28, 44, 44};
			entityBounds = new CollisionBox(xPoints1, yPoints1);
			corners = new CollisionBox(xCorners1, yCorners1);
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case UP:
			break;
		default:
			break;
		}
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 28, shadowSprite, true, 0.6);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}

}
