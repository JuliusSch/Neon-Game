package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.AssaultRifleBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class AssaultRifle extends PlayerWeapon {
	
	public AssaultRifle(int x, int y, int ammoCount) {
		super(x, y, 24, 24, Sprite.assaultRifle, ammoCount);
		initiateValues();
	}
	
	public AssaultRifle(Player player, int ammoCount) {
		super(player, 24, 24, Sprite.assaultRifle, ammoCount);
		initiateValues();
	}
	
	protected void initiateValues() {
		xRenderOffset = -3;
		yRenderOffset = 2;
		cooldown = 7;	//6
		slotSprite = Sprite.assaultRifleSlotSprite;
		sprite = Sprite.assaultRifle;
		shine = new AnimatedSprite(Spritesheet.assaultRifleShine, 24, 24, 7, 4);
		maxAmmo = 360;
		standardAmmoBoxAmount = 60;
		recoil = 0.3;
		name = "AssaultRifle";
		magSize = 15;
		magCount = magSize;
		reloadTime = 120;
	}

	public void attack(double x, double y, double direction) {
		SoundClip.pistol_shot.play();
		double xp = Math.cos(direction) * 16;
		double yp = Math.sin(direction) * 16;
		Projectile p = new AssaultRifleBullet(owner, x + xp - 3, y + yp + 2, direction, level);
		level.add(p);
		shotsFired++;
		magCount--;
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
		if (magCount == 0) {
			shotsFired = 0;
			magCount = magSize;
			return reloadTime;
		}
		if (shotsFired >= 5) {
			shotsFired = 0;
			return 30;
		} else return cooldown;
	}

}
