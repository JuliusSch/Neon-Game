package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Railing extends CollisionEntity {

	private int xRenderOffset, yRenderOffset;
	private Orientation2D dir;

	public Railing(int x, int y, Orientation2D dir, int variant) {
		super(x, y);
		this.dir = dir;
		switch (dir) {
		case HORIZONTAL:
			sprite = Sprite.railingHoriz;
			shadowSprite = Sprite.crowdBarrierHorizShadow;
			int[] xPoints = { 0, 8, 15, 0, 8, 15, };
			int[] yPoints = { 12, 12, 12, 15, 15, 15, };
			int[] xCorners = { 0, 15, 0, 15 };
			int[] yCorners = { 12, 12, 15, 15 };
			entityBounds = new CollisionBox(xPoints, yPoints);
			corners = new CollisionBox(xCorners, yCorners);
			xRenderOffset = 0;
			yRenderOffset = 0;
			break;
		case VERTICAL:
			sprite = Sprite.railingVert;
			if (variant == 2) sprite = Sprite.railingVert2;
			shadowSprite = Sprite.crowdBarrierVertShadow;
			int[] xPoints2 = { 13, 13, 13, 13, 15, 15 ,15, 15 };
			int[] yPoints2 = { 13, 19, 25, 31, 13, 19, 25, 31 };
			int[] xCorners2 = { 13, 15, 13, 15 };
			int[] yCorners2 = { 13, 13, 31, 31 };
			entityBounds = new CollisionBox(xPoints2, yPoints2);
			corners = new CollisionBox(xCorners2, yCorners2);
			xRenderOffset = 1;
			yRenderOffset = 0;
			break;
		default:
			break;
		}
	}

	public int getYAnchor() {
		if (dir == Orientation2D.HORIZONTAL)
			return (int) y + 24;
		else
			return (int) y + 36;
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x + xRenderOffset, (int) y + yRenderOffset, sprite, true);
	}

}
