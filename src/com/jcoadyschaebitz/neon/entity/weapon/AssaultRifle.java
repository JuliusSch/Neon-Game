package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.AssaultRifleBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class AssaultRifle extends PlayerWeapon {
	
	public AssaultRifle(int x, int y, int ammoCount) {
		super(x, y, 32, 32, Sprite.assaultRifle, ammoCount);
		initiateValues();
	}
	
	public AssaultRifle(Player player, int ammoCount) {
		super(player, 32, 32, Sprite.assaultRifle, ammoCount);
		initiateValues();
	}
	
	protected void initiateValues() {
		xRenderOffset = -7;
		yRenderOffset = -2;
		cooldown = 7;	//6
		slotSprite = Sprite.assaultRifleSlotSprite;
		sprite = Sprite.assaultRifle;
		shine = new AnimatedSprite(Spritesheet.assaultRifleShine, 32, 32, 7, 4);
		maxAmmo = 360;
		standardAmmoBoxAmount = 60;
		recoil = 0.3;
	}

	public void attack(double x, double y, double direction) {
		double xp = Math.cos(direction) * 25;
		double yp = Math.sin(direction) * 25;
		Projectile p = new AssaultRifleBullet(owner, x + xp, y + yp + 6, direction);
		level.add(p);
		shotsFired++;
	}
	
	public void mouseReleased() {
		shotsFired = 0;
	}

	public void attack(double x, double y, double direction, double speed) {
		
	}

	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		attack(x, y, direction);
	}

	public void addFlash(int x, int y, double angle) {
		
	}
	
	public int getCooldown() {
		if (shotsFired >= 8) {
			shotsFired = 0;
			return 30;
		} else return cooldown;
	}

}
