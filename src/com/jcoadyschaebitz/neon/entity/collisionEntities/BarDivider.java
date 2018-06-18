package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BarDivider extends CollisionEntity {

	public BarDivider(int x, int y) {
		super(x, y);
		sprite = Sprite.barScreenSolid;
		shadowSprite = Sprite.barScreenTranslucent;
		int[] xPoints = { 3, 13, 23, 33, 43, 57, 3, 13, 23, 33, 43, 57 };
		int[] yPoints = { 41, 41, 41, 41, 41, 41, 43, 43, 43, 43, 43, 43 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { 3, 57, 3, 57 };
		int[] yCorners = { 41, 41, 43, 43 };
		corners = new CollisionBox(xCorners, yCorners);
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 1, shadowSprite, true, 0.2);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
