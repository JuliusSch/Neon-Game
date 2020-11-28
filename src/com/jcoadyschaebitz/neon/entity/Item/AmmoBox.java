package com.jcoadyschaebitz.neon.entity.Item;

import com.jcoadyschaebitz.neon.entity.weapon.PlayerWeapon;

public class AmmoBox extends Item {

	public PlayerWeapon weapon;
	public int amount;

	public AmmoBox(int x, int y, PlayerWeapon weapon, int amount) {
		super(x, y);
		this.weapon = weapon;
		sprite = weapon.ammoSprite;
		this.amount = amount;
	}

	@Override
	protected void doPickUp() {
		if (weapon.ammoCount == weapon.maxAmmo) return;
		else {
			weapon.ammoChange(amount);
			remove();
		}		
	}
}
