package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class CollisionEntity extends Entity {

	protected Sprite shadowSprite;

	public CollisionEntity(int x, int y) {
		this.x = x;
		this.y = y;
		int[] xPoints = { 0, 15, 0, 15 };
		int[] yPoints = { 0, 0, 15, 15 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
	}

	public CollisionBox getProjectileBounds() {
		return corners;
	}

	public void update() {
		time++;
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 1, shadowSprite, true, 0.6);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}

}
