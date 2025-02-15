package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.RotCollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SwordSlash extends Entity {

	protected double xa, ya, direction;	//generalise for animations as entities
	protected AnimatedSprite flash;
	protected int life;
	protected boolean hit;
	protected MeleeWeapon weapon;
	protected Mob origin;

	public SwordSlash(Mob origin, MeleeWeapon weapon, double x, double y, double direction, AnimatedSprite animSprite) {
		this.x = x;
		this.y = y;
		xa = Math.cos(direction) * 1;
		ya = Math.sin(direction) * 1;
		this.direction = direction;
		this.weapon = weapon;
		flash = animSprite;			
		this.origin = origin;
		life = animSprite.getTotalLength();																		//42, 32
		int[] xPoints = { 37, 40, 46, 50, 54, 52, 50, 47, 43, 36, 30, 25, 21, 15, 23, 31, 37, 45, 42 };
		int[] yPoints = { 12, 16, 20, 23, 25, 31, 36, 42, 43, 45, 46, 48, 49, 50, 44, 39, 35, 27, 22 };
		entityBounds = new RotCollisionBox(xPoints, yPoints);
		((RotCollisionBox) entityBounds).rotatePoints(direction, 64, 64);
		flash.playOnce();
	}

	public void update() {
		if (time > life) remove();
		time++;
		flash.update();
		x += xa;
		y += ya;
		xa -= 0.1 * xa;
		ya -= 0.1 * ya;
		if (!hit) {
			
		}
	}

	public void render(Screen screen) {
		screen.renderSprite((int) (x), (int) (y), Sprite.rotateSprite(flash.getSprite(), direction, 64, 64), true);
		entityBounds.renderBounds(screen, 0xffFFD800, (int) x, (int) y);
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}

}
