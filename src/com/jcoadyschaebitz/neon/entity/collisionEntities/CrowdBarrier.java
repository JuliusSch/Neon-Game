package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class CrowdBarrier extends CollisionEntity {
	
	private int xRenderOffset, yRenderOffset;
	private Or_2D dir;

	public CrowdBarrier(int x, int y, Or_2D dir) {
		super(x, y);
		this.dir = dir;
		switch (dir) {
		case HORIZONTAL:
			sprite = Sprite.crowdBarrierHoriz;
			shadowSprite = Sprite.crowdBarrierHorizShadow;
			int[] xPoints = { 3, 14, 25, 35, 45, 3, 14, 25, 35, 45, };
			int[] yPoints = { 14, 14, 14, 14, 14, 18, 18, 18, 18, 18, };
			int[] xCorners = { 3, 45, 3, 45 };
			int[] yCorners = { 10, 10, 18, 18};
			entityBounds = new CollisionBox(xPoints, yPoints);
			corners = new CollisionBox(xCorners, yCorners);
			xRenderOffset = 0;
			yRenderOffset = 23;
			break;
		case VERTICAL:
			sprite = Sprite.crowdBarrierVert;
			shadowSprite = Sprite.crowdBarrierVertShadow;
			int[] xPoints2 = { 10, 12, 10, 12, 10, 12, 10, 12, 10, 12, 10, 12, 10, 12, 10, 12 };
			int[] yPoints2 = { 27, 27, 33, 33, 39, 39, 45, 45, 51, 51, 57, 57, 63, 63, 69, 69 };
			int[] xCorners2 = { 10, 12, 10, 12 };
			int[] yCorners2 = { 27, 27, 69, 69};
			entityBounds = new CollisionBox(xPoints2, yPoints2);
			corners = new CollisionBox(xCorners2, yCorners2);
			xRenderOffset = 7;
			yRenderOffset = 25;
			break;
		default:
			break;
		}
	}
	
	public int getYAnchor() {
		if (dir == Or_2D.HORIZONTAL) return (int) y + 24;
		else return (int) y + 36;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x + xRenderOffset, (int) y + yRenderOffset, shadowSprite, true, 0.4);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}
}
