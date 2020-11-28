package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BarTable extends CollisionEntity {

	public BarTable(int x, int y) {
		super(x, y);
		sprite = Sprite.barTable;
		shadowSprite = Sprite.barTableShadow;
		int[] xPoints = { 7, 7, 7, 18, 30, 40, 40, 40, 7, 18, 30, 40 };
		int[] yPoints = { 20, 25, 30, 20, 20, 20, 25, 30, 35, 35, 35, 35 };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { 7, 40, 7, 40 };
		int[] yCorners = { 20, 20, 35, 35 };
		corners = new CollisionBox(xCorners, yCorners);
		this.x = x;
		this.y = y;
	}
	
	public int getYAnchor() {
		return (int) y + 36;
	}
}
