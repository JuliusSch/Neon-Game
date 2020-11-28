package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Divider_2_Glass extends CollisionEntity {
	
	private Sprite windowSprite;

	public Divider_2_Glass(int x, int y) {
		super(x, y);
		sprite = Sprite.divider_2_h_base;
		shadowSprite = Sprite.nullSprite;
		windowSprite = Sprite.divider_2_h_window;
		int[] xPoints = { 1, 15, 1, 15 };
		int[] yPoints = { 40, 40, 48, 48 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y + 32, sprite, true);
		screen.renderTranslucentSprite((int) x, (int) y - 2, windowSprite, true, 0.3);
		screen.renderTranslucentSprite((int) x, (int) y, windowSprite, true, 0.3);
	}
	
	public int getYAnchor() {
		return (int) y + 48;
	}

}
