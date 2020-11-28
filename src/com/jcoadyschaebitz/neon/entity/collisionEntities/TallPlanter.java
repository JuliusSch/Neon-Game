package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class TallPlanter extends CollisionEntity {

	public TallPlanter(int x, int y) {
		super(x, y);
		int[] xPoints = { 1, 15, 1, 15 };
		int[] yPoints = { -1, -1, 0, 0 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
		sprite = Sprite.tallPlanter;
		shadowSprite = Sprite.nullSprite;
	}
	
	public int getYAnchor() {
		return (int) y;
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y - sprite.getHeight(), sprite, true);
		entityBounds.renderBounds(screen, 0xff00ffff, (int) x, (int) y);
	}

}
