package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class AmmoBox extends Entity {

	public PlayerWeapon weapon;
	public int amount;

	public AmmoBox(int x, int y, PlayerWeapon weapon, int amount) {
		this.x = x;
		this.y = y;
		this.weapon = weapon;
		sprite = weapon.ammoSprite;
		this.amount = amount;
	}

	public void update() {
		if (level.isPlayerInRad(this, 20)) {
			if (weapon.ammoCount == weapon.maxAmmo) return;
			else {
				weapon.ammoChange(amount);
				remove();
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
