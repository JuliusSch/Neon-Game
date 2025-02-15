package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class LaserShot extends Projectile {

	public LaserShot(Entity source, double x, double y, double angle, double speed, Level level) {
		super(source, x, y, angle, level);
		isEnemyBullet = true;
		range = 500;
		damage = 5;
		this.speed = 0;//speed;
		sprite = Sprite.rotateSprite(Sprite.laserShot, angle, 16, 16);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x - 8, (int) y, sprite, true);
	}


}
