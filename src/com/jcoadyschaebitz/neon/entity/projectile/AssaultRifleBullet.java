package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class AssaultRifleBullet extends Projectile {

	public AssaultRifleBullet(Entity source, double x, double y, double angle, Level level) {
		super(source, x, y, angle, level);
		range = 500;
		damage = 2;
		this.speed = 9;
		sprite = Sprite.rotateSprite(Sprite.assaultRifleBullet, angle, 16, 16);
		glow = Sprite.rotateSprite(Sprite.shotgunPelletGlow, angle, 32, 32); 
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public double getKnockbackMultiplier() {
		return 1.2;
	}
	
	public void update() {
		super.update();
	}

}
