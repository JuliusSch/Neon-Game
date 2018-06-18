package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class PistolBullet extends Projectile {

	private Sprite glow;

	public PistolBullet(Entity source, double x, double y, double angle, double speed) {
		super(source, x, y, angle);
		range = 500;
		damage = 3;
		this.speed = speed;
		sprite = Sprite.rotateSprite(Sprite.pistol_bullet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.pistol_bullet_glow, angle, 32, 32);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

	public void update() {
		time++;
		move(source, nx, ny);
	}

	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, Sprite.particle_blue));
		remove();
	}

	public void render(Screen screen) {
		if (time > speed / 10) {
			screen.renderTranslucentSprite((int) x - 8, (int) y - 8, glow, true, 0.1);
			screen.renderSprite((int) x, (int) y, sprite, true);
		}
	}
}
