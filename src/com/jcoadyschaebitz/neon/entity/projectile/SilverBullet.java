package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SilverBullet extends Projectile {

	public SilverBullet(Entity source, double x, double y, double angle) {
		super(source, x, y, angle);
		sprite = Sprite.silverBullet;
	}

}
