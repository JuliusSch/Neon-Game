package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.SlowEnemyBullet;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SlowEnemyGun extends Gun {

	public SlowEnemyGun(Mob owner) {
		super(owner, 32, 32, Sprite.slowEnemyGun);
		initiateValues();
	}
	
	public SlowEnemyGun(int x, int y) {
		super(x, y, 32, 32, Sprite.slowEnemyGun);
		initiateValues();
	}

	protected void initiateValues() {
		sprite = Sprite.slowEnemyGun;
		DAMAGE = 2;
		xRenderOffset = -7;
		yRenderOffset = -2;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
	}

	public void attack(double x, double y, double angle) {
		attack(x, y, angle, 1);
		addFlash((int) x, (int) y, angle);
	}

	public void attack(double x, double y, double angle, double speed) {
		double xx = Math.cos(angle) * 12;
		double yy = Math.sin(angle) * 12;
		for (int i = 0; i < 8; i++) {
			Projectile p = new SlowEnemyBullet(owner, x + xx, y + 6 + yy, (angle + random.nextDouble() / 2) - 0.25, speed + (random.nextDouble() / 2), level);
			level.add(p);
		}
		addFlash((int) x, (int) y, angle);
	}

	public void addFlash(int x, int y, double angle) {
	}
	
}
