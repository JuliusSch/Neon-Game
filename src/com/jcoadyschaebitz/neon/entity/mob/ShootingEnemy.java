package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.weapon.Weapon;

public abstract class ShootingEnemy extends Mob {

	protected Weapon secondaryWeapon;
	
	protected int viewRange = 240;

	public ShootingEnemy(int x, int y) {
		super(x, y);
	}

	public void update() {
		super.update();
		if (weapon != null) {
			if (!inCutscene && aggro) weapon.updateSprite(directionP);
			if (!inCutscene) weapon.update();
		}
		if (distanceP < viewRange && playerSightline()) aggro = true;
		if (distanceP > viewRange + 80) {
			aggro = false;						// temporary code
			state = MobState.UNAWARE;
		}
		if (damageDelay <= 0 && aggro) {
			if (level.getPlayer().getIntX() < x) dir = Orientation.LEFT;
			else dir = Orientation.RIGHT;
			if (xa != 0 || ya != 0) {
				move(xa, ya, true);
				walking = true;
			} else {
				walking = false;
			}
		}
	}

}
