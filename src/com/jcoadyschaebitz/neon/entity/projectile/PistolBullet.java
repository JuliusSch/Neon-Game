package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class PistolBullet extends Projectile {

	public PistolBullet(Entity source, double x, double y, double angle, double speed, Level level) {
		super(source, x, y, angle, level);
		range = 500;
		damage = 3;
		this.speed = speed;
		sprite = Sprite.rotateSprite(Sprite.pistolBullet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.pistolBulletGlow, angle, 32, 32);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
}
