package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class EliteRifleBullet extends Projectile {

	public EliteRifleBullet(Entity source, double x, double y, double angle, double speed, Level level) {
		super(source, x, y, angle, level);
		isEnemyBullet = true;
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
		life = 1000;
		range = 1000;
		damage = 4;
		this.speed = speed;
		glow = Sprite.rotateSprite(Sprite.eliteRifleBullet, angle, 48, 48);
		particle = Sprite.particleCrimson;
	}

}
