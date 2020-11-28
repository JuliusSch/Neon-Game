package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class Gun extends Weapon {

	public static int DAMAGE;
	public int recoil;

	public Gun(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);
	}

	public Gun(Mob owner, int width, int height, Sprite sprite) {
		super((int) owner.getX(), (int) owner.getY(), width, height, sprite);
		this.sprite = sprite;
		this.owner = owner;
		owned = true;
		owner.addWeapon(this);
	}

	public void update() {
		if (owned) {
			if (owner.isRemoved()) remove();
			this.x = owner.getX();
			this.y = owner.getY() + 1;
		}
		if (muzzleGlowTimer > 0) muzzleGlowTimer--;
	}
	
	public void beginPreAttackAnimations() {
		muzzleGlowTimer = attackBuildup + 30;
	}

	public void recoil(double angle) {
		if (recoil == 0) return;
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}
}
