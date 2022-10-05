package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class SilverBullet extends Projectile {

	public SilverBullet(Entity source, double x, double y, double angle, int speed, Level level) {
		super(source, x, y, angle, level);
		sprite = Sprite.silverBullet;
		this.speed = speed;
		range = 500;
		damage = 4;
		isEnemyBullet = true;
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

}
