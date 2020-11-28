package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.PistolBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class Pistol extends PlayerWeapon {
	
	public Pistol(int x, int y, int ammoCount) {
		super(x, y, 32, 32, Sprite.pistol, ammoCount);
		initiateValues();
	}
	
	public Pistol(Player player, int ammoCount) {
		super(player, 32, 32, Sprite.pistol, ammoCount);
		initiateValues();
	}

	protected void initiateValues() {
		xRenderOffset = -7;
		yRenderOffset = -2;
		cooldown = 15;		//13? 20?
		slotSprite = Sprite.pistolSlotSprite;
		sprite = Sprite.pistol;
		muzzleFlashSprite = Sprite.pistolFlash;
		shine = new AnimatedSprite(Spritesheet.pistolShine, 32, 32, 7, 4);
		maxAmmo = 480;
		standardAmmoBoxAmount = 48;
		recoil = 0.3;
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.pistol_shot.play();
		double xp = Math.cos(direction) * 15;
		double yp = Math.sin(direction) * 15;
		Projectile p = new PistolBullet(owner, x + xp, y + yp + 6, angle + (random.nextDouble() - 0.5) / 12, speed);
		level.add(p);
		flashTimer = 4;
		shotsFired++;

	}

	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 10);
	}
	
	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		this.attack(x, y, direction, 10 * bulletSpeedMultiplier);
	}
	
}
