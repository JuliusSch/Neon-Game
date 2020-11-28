package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class DeadMob extends Entity {

	public DeadMob(Mob mob, int x, int y) {
		this.x = x;
		this.y = y;
		sprite = mob.getDeadSprite();
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y + 2, Sprite.shadow, true, 0.3);
		screen.renderSprite((int) x, (int) y + 4, sprite, true);
	}
	
	public int getYAnchor() {
		return (int) y + 20;
	}

	public void update() {	
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}
	
}
