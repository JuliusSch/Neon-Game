package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class HealthKit extends Entity {

	private double amount;

	public HealthKit(int x, int y, double amount) {
		this.x = x;
		this.y = y;
		sprite = Sprite.healthKit;
		this.amount = amount;
	}

	public HealthKit(int x, int y) {
		this(x, y, 8);
	}

	public void update() {
		if (level.isPlayerInRad(this, 20)) {
			if (level.getPlayer().getHealth() == level.getPlayer().maxHealth) return;
			level.getPlayer().changeHealth(amount);
			remove();
		}
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, Sprite.healthKitShadow, true, 0.7);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}
}
