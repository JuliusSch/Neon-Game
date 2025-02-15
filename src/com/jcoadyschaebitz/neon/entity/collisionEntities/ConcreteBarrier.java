package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class ConcreteBarrier extends CollisionEntity {

	public ConcreteBarrier(int x, int y, Orientation2D orientation) {
		super(x, y);
		switch (orientation) {
		case HORIZONTAL:
			sprite = Sprite.concreteBarrierhoriz;
			shadowSprite = Sprite.concreteBarrierhorizShadow;
			int[] xPoints = { 2, 14, 25, 35, 45, 54, 61, 2, 61, 2, 14, 25, 35, 45, 54, 61 };
			int[] yPoints = { 16, 16, 16, 16, 16, 16, 16, 22, 22, 28, 28, 28, 28, 28, 28, 28 };
			int[] xCorners = { 2, 61, 2, 61 };
			int[] yCorners = { 16, 16, 28, 28};
			entityBounds = new CollisionBox(xPoints, yPoints);
			corners = new CollisionBox(xCorners, yCorners);
			break;
		case VERTICAL:
			sprite = Sprite.concreteBarrierhoriz;
			shadowSprite = Sprite.crowdBarrierVertShadow;
			int[] xPoints2 = {  };
			int[] yPoints2 = {  };
			int[] xCorners2 = {  };
			int[] yCorners2 = {  };
			entityBounds = new CollisionBox(xPoints2, yPoints2);
			corners = new CollisionBox(xCorners2, yCorners2);
			break;
		default:
			break;
		}
	}

}
