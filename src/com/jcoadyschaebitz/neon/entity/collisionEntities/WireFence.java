package com.jcoadyschaebitz.neon.entity.collisionEntities;

import java.util.Random;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;

public class WireFence extends CollisionEntity {
	
	private int xRenderShadowOffset, yRenderShadowOffset;		//generalise random sprites with interface

	public WireFence(int x, int y, Orientation2D dir) {
		super(x, y);
		switch (dir) {
		case HORIZONTAL:
			initHorizontal();
			break;
		case VERTICAL:
			initVert();
			break;
		default:
			initHorizontal();
			break;
		}
	}
	
	public int getYAnchor() {
		return (int) y + getSpriteH();
	}
	
	private void initHorizontal() {
		Sprite[] sprites = Spritesheet.wireFenceHorizontal.getSprites();
		random = new Random(Level.getUniversalSeed() * ((int) x + 1 << 4) / ((int) y + 1 << 4));
		int i = (int) (random.nextDouble() * x * y) % sprites.length;
		sprite = sprites[i];
		shadowSprite = Sprite.wireFenceHozShadow;
		int[] xPoints = { 1, 15, 1, 15 };
		int[] yPoints = { 44, 44, 48, 48 };
		int[] xCorners = { 1, 15, 1, 15 };
		int[] yCorners = { 40, 40, 52, 52 };
		xRenderShadowOffset = 0;
		yRenderShadowOffset = 48;
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xCorners, yCorners);
	}
	
	private void initVert() {
		sprite = Sprite.wireFenceVert;
		shadowSprite = Sprite.wireFenceVertShadow;
		int[] xPoints = { 6, 9, 6, 9, 6, 9 };
		int[] yPoints = { 32, 32, 47, 47, 40, 40 };
		xRenderShadowOffset = 8;
		yRenderShadowOffset = 32;
		entityBounds = new CollisionBox(xPoints, yPoints);
		corners = new CollisionBox(xPoints, yPoints);
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x + xRenderShadowOffset, (int) y + yRenderShadowOffset, shadowSprite, true, 0.6);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}
}
