package com.jcoadyschaebitz.neon.entity.weapon;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.RotCollisionBox;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class SwordSlash extends Entity {

	protected RotCollisionBox bounds;
	protected double xa, ya, direction;
	protected AnimatedSprite flash;
	protected int life;
	protected boolean hit;
	protected MeleeWeapon weapon;

	public SwordSlash(MeleeWeapon weapon, double x, double y, double direction, AnimatedSprite animSprite) {
		this.x = x;
		this.y = y;
//		xa = Math.cos(direction) * 3;
//		ya = Math.sin(direction) * 3;
		this.direction = direction;
		this.weapon = weapon;
		flash = animSprite;					
		life = animSprite.getTotalLength();																		//42, 32
		int[] xPoints = { 37, 40, 46, 50, 54, 52, 50, 47, 43, 36, 30, 25, 21, 15, 23, 31, 37, 45, 42 };
		int[] yPoints = { 12, 16, 20, 23, 25, 31, 36, 42, 43, 45, 46, 48, 49, 50, 44, 39, 35, 27, 22 };
		bounds = new RotCollisionBox(xPoints, yPoints);
		bounds.rotPoints(direction, 64, 64);
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
			List<Entity> playerList = new ArrayList<Entity>();
			playerList.add(level.getPlayer());
			List<Entity> hitEntities = bounds.checkForCollisions(playerList, x, y);
			if (hitEntities.contains(level.getPlayer())) {
				level.getPlayer().hitReceived(weapon);
				hit = true;
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite((int) (x + Math.cos(direction) * 5), (int) (y + Math.sin(direction) * 5), Sprite.rotateSprite(flash.getSprite(), direction, 64, 64), true);
//		bounds.renderBounds(screen, 0xffFFD800, (int) x, (int) y);
	}

}
