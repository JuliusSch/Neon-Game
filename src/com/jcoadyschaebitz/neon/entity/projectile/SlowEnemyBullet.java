package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class SlowEnemyBullet extends Projectile {

	public SlowEnemyBullet(Entity source, double x, double y, double angle, Level level) {
		this(source, x, y, angle, 1, level);
	}
	
	public SlowEnemyBullet(Entity source, double x, double y, double angle, double speed, Level level) {
		super(source, x, y, angle, level);
		isEnemyBullet = true;
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
		life = 1000;
		range = 1000;
		spriteWidth = 5;
		spriteHeight = 5;
		damage = 4;
		this.speed = speed;
		sprite = Sprite.rotateSprite(Sprite.slowEnemyBullet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.slowEnemyBulletGlow, angle, 32, 32);
		particle = Sprite.particleYellow;
	}
	
	public void update() {
		time++;
		move(source, nx, ny);

		if (time > life) remove();
	}
	
	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, Sprite.particleYellow));
		remove();
	}

}
