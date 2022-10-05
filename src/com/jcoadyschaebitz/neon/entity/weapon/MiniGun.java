package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.EnemyBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class MiniGun extends Gun {

	public MiniGun(int x, int y) {
		super(x, y, 32, 32, Sprite.miniGun);
		initiateValues();
	}
	
	public MiniGun(Mob owner) {
		super(owner, 32, 32, Sprite.miniGun);
		initiateValues();
	}
	
	public void initiateValues() {
		sprite = Sprite.miniGun;
		DAMAGE = 5;
		xRenderOffset = -3;
		yRenderOffset = 1;
		attackBuildup = 10;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
	}

	public void attack(double x, double y, double angle) {
		attack(x, y, angle, 2);
		addFlash((int) x, (int) y, angle);
	}

	public void attack(double x, double y, double angle, double speed) {
		Projectile p;
		double xp = Math.cos(angle) * 18;
		double yp = Math.sin(angle) * 18;
		if (speed == 1) p = new EnemyBullet(owner, x + xp, y + yp + 6, angle, speed, 1, level);
		else p = new EnemyBullet(owner, x + xp, y + yp + 6, angle, speed, 2, level);
		level.add(p);
		addFlash((int) x, (int) y, angle);
	}
	
	public void addFlash(int x, int y, double angle) {
	}
}
