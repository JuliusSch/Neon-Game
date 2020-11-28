package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class LaserBullet extends Projectile {

	public LaserBullet(Entity source, double x, double y, double angle) {
		super(source, x, y, angle);
		isEnemyBullet = true;
		damage = 6;
		speed = 3;
		sprite = Sprite.rotateSprite(Sprite.fastBulletBlue, angle, 24, 24);
		glow = Sprite.rotateSprite(Sprite.fastBulletBlueGlow, angle, 24, 24);
		bulletAnim = new AnimatedSprite(Spritesheet.fast_bullet_anim, 24, 24, 2, 4);
		spriteWidth = 5;
		spriteHeight = 5;
		range = 16000;
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public void update() {
		time++;
		move(source, nx, ny);
		bulletAnim.update();
	}

	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, Sprite.particleBlue));
		remove();
	}
	
	public void render(Screen screen) {
		if (time > speed / 10) {
			screen.renderTranslucentSprite((int) x, (int) y, glow, true, 0.2);
			screen.renderSprite((int) x, (int) y, Sprite.rotateSprite(bulletAnim.getSprite(), angle, 24, 24), true);
		}
	}

}
