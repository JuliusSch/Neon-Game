package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.Level;

public class Ray extends Projectile {

	public Ray(Entity source, double x, double y, double angle, Level level) {
		super(source, x, y, angle, level);
		this.speed = 30;
		damage = 0;
		range = 500;
		sprite = Sprite.enemy_bullet_glow_1;
		int[] xCollisionValues = { 4, 12, 4, 12 };
		int[] yCollisionValues = { 4, 4, 12, 12 };
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public Ray(Entity source, double x, double y, double angle, double speed, Level level) {
		this(source, x, y, angle, level);
		this.speed = speed;
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public void collide(int x, int y) {
		remove();
	}

	public void collideEntity(int x, int y, Entity e) {
		remove();
	}

}
