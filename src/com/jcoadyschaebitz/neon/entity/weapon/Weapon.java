package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class Weapon extends Entity {
	
	protected Mob owner;
	protected boolean owned;
	protected int xRenderOffset, yRenderOffset, spriteWidth, spriteHeight;
	protected Sprite sprite, rotSprite, muzzleFlashSprite; 
	protected double xShootOffset, yShootOffset, direction;

	public Weapon(int x, int y, int width, int height, Sprite sprite) {
		this.x = x << 4;
		this.y = y << 4;
		spriteWidth = width;
		spriteHeight = height;
		this.sprite = sprite;
		initiateValues();
	}
	
	public Weapon(Mob owner, int width, int height, Sprite sprite) {
		this(owner.getIntX(), owner.getIntY(), width, height, sprite);
		this.owner = owner;
		owned = true;
		initiateValues();
	}
	
	protected abstract void initiateValues();
	
	public void render(Screen screen) {
		if (!owned) screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
	}
	
	public void renderOnOwner(Screen screen, int bob) {
		screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset/* + bob*/, rotSprite, true);
	}
	
	public abstract void addFlash(int x, int y, double angle);

	public abstract void attack(double x, double y, double direction);
	
	public abstract void attack(double x, double y, double direction, double speed);
	
	public void updateSprite(double dir) {
		direction = dir;
		if (dir > 0 && dir <= Math.PI / 2) rotSprite = Sprite.rotateSprite(sprite, dir, spriteWidth, spriteHeight);
		else if (dir > 0) rotSprite = Sprite.flipSprite(Sprite.rotateSprite(sprite, Math.PI - dir, spriteWidth, spriteHeight));
		if (dir < 0 && dir >= Math.PI / -2)	rotSprite = Sprite.rotateSprite(sprite, dir, spriteWidth, spriteHeight);
		else if (dir < 0) rotSprite = Sprite.flipSprite(Sprite.rotateSprite(sprite, Math.PI - dir, spriteWidth, spriteHeight));
	}

}
