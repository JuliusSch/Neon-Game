package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class WireFence extends CollisionEntity {

	public WireFence(int x, int y) {
		super(x, y);
		sprite = Sprite.wireFence;
		shadowSprite = Sprite.nullSprite;
		int[] xPoints = { 0, 15, 0, 15 };
		int[] yPoints = { 44, 44, 48, 48 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
	}
}
