package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class EnemyBullet extends Projectile {

	public EnemyBullet(Entity source, double x, double y, double angle, double speed, int spriteNo, Level level) {
		super(source, x, y, angle, level);
		isEnemyBullet = true;
		range = 400;
		damage = 4;
		this.speed = speed;
		spriteWidth = 5;
		spriteHeight = 5;
		this.level = level;
		switch (spriteNo) {
		case 1:
			sprite = Sprite.enemy_bullet_1;
			glow = Sprite.enemy_bullet_glow_1;
			particle = Sprite.particleOrange;
			break;
		case 2:
			sprite = Sprite.enemy_bullet_2;
			glow = Sprite.enemy_bullet_glow_2;
			particle = Sprite.particleCrimson;
			break;
		default:
			sprite = Sprite.nullSprite;
			glow = Sprite.nullSprite;
			particle = Sprite.particleOrange;
			break;
		}

		nx = Math.cos(angle) * this.speed;
		ny = Math.sin(angle) * this.speed;
	}

	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, particle));
		remove();
	}

}
