package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class EnemyBullet extends Projectile {

	public EnemyBullet(Entity source, double x, double y, double angle, double speed, int spriteNo) {
		super(source, x, y, angle);
		isEnemyBullet = true;
		range = 400;
		damage = 4;
		this.speed = speed;
		spriteWidth = 5;
		spriteHeight = 5;
		switch (spriteNo) {
		case 1:
			sprite = Sprite.rotateSprite(Sprite.enemy_bullet_1, angle, 16, 16);
			glow = Sprite.rotateSprite(Sprite.enemy_bullet_glow_1, angle, 32, 32);
			break;
		case 2:
			sprite = Sprite.rotateSprite(Sprite.enemy_bullet_2, angle, 16, 16);
			glow = Sprite.rotateSprite(Sprite.enemy_bullet_glow_2, angle, 32, 32);
			break;
		default:
			sprite = Sprite.nullSprite;
			glow = Sprite.nullSprite;
			break;
		}

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

	public void update() {
		time++;
		move(source, nx, ny);
	}

	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, Sprite.particle_orange));
		remove();
	}

}
