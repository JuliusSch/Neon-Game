package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class PistolBullet extends Projectile {

	public PistolBullet(Entity source, double x, double y, double angle, double speed) {
		super(source, x, y, angle);
		range = 500;
		damage = 3;
		this.speed = speed;
		sprite = Sprite.rotateSprite(Sprite.pistolBullet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.pistolBulletGlow, angle, 32, 32);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x - 8, (int) y - 8, glow, true);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
