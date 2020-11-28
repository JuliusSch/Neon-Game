package com.jcoadyschaebitz.neon.entity.collisionEntities;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SlidingDoor extends CollisionEntity {
	
	private boolean open, locked;
	private Sprite openSprite, closedSprite;
	private CollisionBox openBounds, closedBounds, openCorners, closedCorners;
	
	public SlidingDoor(int x, int y, boolean open, boolean locked, Or_2D direction) {
		super(x, y);
		this.open = open;
		this.locked = locked;
		if (locked) open = false;
		if (direction == Or_2D.HORIZONTAL) {
			openSprite = Sprite.door_h_open;
			closedSprite = Sprite.door_h_closed;
			int[] xPoints = { 8, 16, 24, 32, 40, 8, 16, 24, 32, 40, 8, 40 };
			int[] yPoints = { 40, 40, 40, 40, 40, 48, 48, 48, 48, 48, 44, 44 };
			closedBounds = new CollisionBox(xPoints, yPoints);
			int[] xCorners = { 8, 40, 8, 40 };
			int[] yCorners = { 40, 40, 48, 48 };
			closedCorners = new CollisionBox(xCorners, yCorners);
			int[] xOpenPoints = { 0, 56, 0, 56 };
			int[] yOpenPoints = { 40, 40, 48, 48 };
			openBounds = new CollisionBox(xOpenPoints, yOpenPoints);
			int[] xOpenCorners = { 0, 0, 0, 0 };
			int[] yOpenCorners = { 0, 0, 0, 0 };
			openCorners = new CollisionBox(xOpenCorners, yOpenCorners);
		} else {
			
		}
		if (open) {
			entityBounds = openBounds;
			corners = openCorners;
			sprite = openSprite;
			shadowSprite = Sprite.nullSprite;
		} else {
			entityBounds = closedBounds;
			corners = closedCorners;
			sprite = closedSprite;
			shadowSprite = Sprite.nullSprite;
		}
	}
	
	public void update() {
		time++;
		if (time % 10 == 0 && !locked) {
			if (level.isPlayerInRad(x + sprite.getWidth() / 2, y + sprite.getHeight() / 2, 48)) {
				if (!open) open();
			} else if (open) close();
		}
	}
	
	public void lock() {
		locked = true;
	}
	
	public void unlock() {
		locked = false;
	}
	
	public void open() {
		open = true;
		entityBounds = openBounds;
		corners = openCorners;
		sprite = openSprite;
	}
	
	public void close() {
		open = false;
		entityBounds = closedBounds;
		corners = closedCorners;
		sprite = closedSprite;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 1, shadowSprite, true, 0.5);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
