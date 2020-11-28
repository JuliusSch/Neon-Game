package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Window1x3 extends CollisionEntity {

	public Window1x3(int x, int y) {
		super(x, y);
		int[] xPoints = { 1, 15, 1, 15 };
		int[] yPoints = { -1, -1, 0, 0 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
		sprite = new Sprite(16, 0xff87FFFF);
		shadowSprite = Sprite.nullSprite;
	}
	
	public int getYAnchor() {
		return (int) y + 1;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y - 16, sprite, true, 0.2);
		screen.renderTranslucentSprite((int) x, (int) y - 32, sprite, true, 0.2);
		screen.renderTranslucentSprite((int) x, (int) y - 48, sprite, true, 0.2);
	}
}
