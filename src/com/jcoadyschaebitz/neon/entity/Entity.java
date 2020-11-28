package com.jcoadyschaebitz.neon.entity;

import java.util.Random;

import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public abstract class Entity {

	protected double x, y, centreX, centreY;
	protected boolean removed = false;
	protected Level level;
	protected Sprite sprite;
	protected double health;
	protected Random random = new Random();
	protected int time = 0, spriteWidth, spriteHeight;
	public CollisionBox entityBounds, corners;

	public abstract void update();
	
	public void updateCutscene() {
	}

	public abstract void render(Screen screen);

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public CollisionBox getCollisionBounds() {
		return entityBounds;
	}

	public void init(Level level) {
		this.level = level;
	}

	public int getYAnchor() {
		return (int) y + getSpriteH();
	}

	public int abs(double value) {
		if (value > 0) return 1;
		if (value == 0) return 0;
		else return -1;
	}

	public double getX() {
		return x;
	}

	public int getIntX() {
		return (int) x;
	}

	public double getY() {
		return y;
	}

	public int getIntY() {
		return (int) y;
	}
	
	public int getMidX() {
		return (int) x + spriteWidth / 2;
	}

	public int getMidY() {
		return (int) y + spriteHeight / 2;
	}
	
	public double getHealth() {
		return health;
	}
	
	public int getSpriteW() {
		if (sprite != null) return sprite.getWidth();
		else return 0;
	}

	public int getSpriteH() {
		if (sprite != null) return sprite.getHeight();
		else return 0;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public abstract void hitReceived(Projectile projectile);
}
