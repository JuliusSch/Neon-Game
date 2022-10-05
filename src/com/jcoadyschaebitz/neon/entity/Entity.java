package com.jcoadyschaebitz.neon.entity;

import java.util.Random;

import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;
import com.jcoadyschaebitz.neon.util.Vec2i;

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
		if (sprite != null) return (int) x + sprite.getWidth() / 2;
		else return (int) x + spriteWidth / 2;
	}

	public int getMidY() {
		if (sprite != null) return (int) y + sprite.getHeight() / 2;
		else return (int) y + spriteHeight / 2;
	}
	
	public Vec2i getPos() {
		return new Vec2i(getIntX(), getIntY());
	}
	
	public Vec2i getMidPos() {
		return new Vec2i(getMidX(), getMidY());
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
