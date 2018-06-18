package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class ShotgunPellet extends Projectile {

	public ShotgunPellet(Entity source, double x, double y, double angle, double speed) {
		super(source, x, y, angle);
		range = 400;
		this.speed = speed;
		damage = 1.8;
		life = random.nextInt(20) + 50;
		sheet = Spritesheet.shotgun_pellet;

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;

		sprite = Sprite.rotateSprite(Sprite.shotgunPellet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.shotgunPellet_glow, angle, 32, 32);
	}

	public void update() {
		time++;
		if (time > life) remove();
		
		move(source, nx, ny);
		speed -= speed / 40;
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

	public void collide(int x, int y) { 
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 5, 50, level, Sprite.particle_blue));
		remove();
	}
	
}
