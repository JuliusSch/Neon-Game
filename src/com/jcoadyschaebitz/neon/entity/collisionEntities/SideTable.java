package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SideTable extends CollisionEntity {

	public SideTable(int x, int y) {
		super(x, y);
		blocksProjectiles = false;
		sprite = Sprite.side_table;
		shadowSprite = Sprite.nullSprite;
		int[] xPoints = { };
		int[] yPoints = { };
		entityBounds = new CollisionBox(xPoints, yPoints);
		int[] xCorners = { };
		int[] yCorners = { };
		corners = new CollisionBox(xCorners, yCorners);
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 1, shadowSprite, true, 0.5);
		screen.renderTranslucentSprite((int) x, (int) y, sprite, true);
//		screen.renderSprite((int) x, (int) y - sprite.getHeight(), sprite, true);
//		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}
	
}
