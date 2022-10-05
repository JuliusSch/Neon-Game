package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class Weapon extends Entity {

	protected Mob owner;
	protected boolean owned;
	protected int xRenderOffset, yRenderOffset, spriteWidth, spriteHeight, attackBuildup;
	protected Sprite sprite, rotSprite, muzzleFlashSprite;
	protected double xShootOffset, yShootOffset, direction;
	protected WeaponState state;
	protected int flashTimer, muzzleGlowTimer;
	protected AnimatedSprite muzzleGlow;

	public int getWeaponAttackBuildup(boolean isSecondaryAttack) {
		return attackBuildup;
	}

	public enum WeaponState {
		IDLE, ATTACKING, BLOCKING
	}

	public void setState(WeaponState state) {
		this.state = state;
	}

	public WeaponState getState() {
		return state;
	}

	public boolean isOwned() {
		return owned;
	}

	public Weapon(int x, int y, int width, int height, Sprite sprite) {
		this.x = x << 4;
		this.y = y << 4;
		spriteWidth = width;
		spriteHeight = height;
		this.sprite = sprite;
		state = WeaponState.IDLE;
		attackBuildup = 30;
		initiateValues();
	}

	public abstract void beginPreAttackAnimations();

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
		double xp = Math.cos(direction);
		double yp = Math.sin(direction);
		screen.renderSprite((int) (x + xRenderOffset + xp * 4), (int) (y + yRenderOffset + yp * 4/* + bob */), rotSprite, true);
		if (flashTimer > 0) screen.renderTranslucentSprite((int) (x + xp * 12), (int) (y + yp * 12), muzzleFlashSprite, true);
		if (muzzleGlowTimer > 0) screen.renderTranslucentSprite((int) (x + xp * 12), (int) (y + yp * 12), Sprite.enemy_bullet_1, true, 1 - ((double) muzzleGlowTimer / (double) attackBuildup));
	}

	public void projectileBlocked(Projectile projectile) {
	}

	public abstract void attack(double x, double y, double direction);

	public abstract void attack(double x, double y, double direction, double speed);

	public void secondaryAttack(double x, double y, double direction) {
	}

	public void updateSprite(double dir) {
		direction = dir;
		if (dir > 0 && dir <= Math.PI / 2) rotSprite = Sprite.rotateSprite(sprite, dir, spriteWidth, spriteHeight);
		else if (dir > 0) rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir, spriteWidth, spriteHeight));
		if (dir < 0 && dir >= Math.PI / -2) rotSprite = Sprite.rotateSprite(sprite, dir, spriteWidth, spriteHeight);
		else if (dir < 0) rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir, spriteWidth, spriteHeight));
	}

}
