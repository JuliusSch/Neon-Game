package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BarStool extends CollisionEntity {

	public BarStool(int x, int y) {
		super(x, y);
		int[] xPoints = { 3, 12, 3, 12 };
		int[] yPoints = { 11, 11, 14, 14 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
		sprite = Sprite.barStool;
		shadowSprite = Sprite.barStoolShadow;
	}
	
	public int getYAnchor() {
		return (int) y + 24;
	}

}
