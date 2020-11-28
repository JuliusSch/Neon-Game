package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class Bolt extends Projectile {
	
	private boolean impaled = false;
	private Mob impaleTarget;
	private int impaleXOffset, impaleYOffset, trailCounter;
	private double direction;

	public Bolt(Entity source, double x, double y, double angle, double speed) {
		super(source, x, y, angle);
		range = 400;
		this.speed = speed;
		damage = 16;
		sprite = Sprite.bolt;
		glow = Sprite.bolt;
		sheet = Spritesheet.bolt;
		life = random.nextInt(20) + 2000;
		int[] xCollisionValues = { 8, 8, 8, 8 };
		int[] yCollisionValues = { 8, 8, 8, 8 };
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);

		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;

		direction = angle;
		trailCounter = 30;
		sprite = Sprite.rotateSprite(Sprite.bolt, angle, sprite.getWidth(), sprite.getHeight());
	}
	
	public void update() {
		time++;
		if (!(nx == 0 && ny == 0)) {
//			level.add(new MuzzleFlash(x + nx / 4, y + ny / 4, 20 + trailCounter, Sprite.nullSprite, Sprite.boltTrail, direction, true));
//			level.add(new MuzzleFlash(x + nx / 2, y + ny / 2, 20 + trailCounter, Sprite.nullSprite, Sprite.boltTrail, direction, true));
//			level.add(new MuzzleFlash(x + nx * 0.75, y + ny * 0.75, 20 + trailCounter, Sprite.nullSprite, Sprite.boltTrail, direction, true));
//			level.add(new MuzzleFlash(x + nx, y + ny, 20 + trailCounter, Sprite.nullSprite, Sprite.boltTrail, direction, true));
		}
		move(source, nx, ny);
		
		if (trailCounter > 0) trailCounter--;
		if (time > life) remove();
		if (impaled) {
			nx = 0;
			ny = 0;
			x = impaleTarget.getX() - impaleXOffset;
			y = impaleTarget.getY() - impaleYOffset;
		}
	}

	public void collide(int x, int y) {
		nx = 0;
		ny = 0;
	}
	
	public void collideEntity(int x, int y, Entity e) {
		if (e instanceof CollisionEntity && e.getHealth() <= 0) {
			nx = 0;
			ny = 0;
		}
	}
	
	public void impaled(Mob mob) {
		impaleTarget = mob;
		impaleXOffset = mob.getIntX() - (int) x;
		impaleYOffset = mob.getIntY() - (int) y;
		impaled = true;
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
