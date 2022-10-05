package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.EnemyBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class EnemyGun extends Gun {

	public EnemyGun(int x, int y) {
		super(x, y, 32, 32, Sprite.enemyGun);
		initiateValues();
	}

	public EnemyGun(Mob owner) {
		super(owner, 32, 32, Sprite.enemyGun);
		initiateValues();
	}

	public void initiateValues() {
		sprite = Sprite.enemyGun;
		DAMAGE = 5;
		xRenderOffset = -7;
		yRenderOffset = -2;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
	}
	
	public void attack(double x, double y, double angle, double speed) {
		double xp = Math.cos(angle) * 12;
		double yp = Math.sin(angle) * 12;
		Projectile p = new EnemyBullet(owner, x, y + 6, angle, speed, 1, level);
		p.move(owner, xp, yp);
		level.add(p);
		addFlash((int) x, (int) y, angle);
	}
	
	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 3);
	}
	
	public void addFlash(int x, int y, double angle) {
	}

}
