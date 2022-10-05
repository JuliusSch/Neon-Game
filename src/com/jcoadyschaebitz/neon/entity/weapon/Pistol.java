package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.PistolBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.Ray;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class Pistol extends PlayerWeapon {
	
	public Pistol(int x, int y, int ammoCount) {
		super(x, y, 24, 24, Sprite.pistol, ammoCount);
		initiateValues();
	}
	
	public Pistol(Player player, int ammoCount) {
		super(player, 24, 24, Sprite.pistol, ammoCount);
		initiateValues();
	}

	protected void initiateValues() {
		xRenderOffset = -3;
		yRenderOffset = 0;
		cooldown = 18;		//13? 20?
		slotSprite = Sprite.pistolSlotSprite;
		sprite = Sprite.pistol;
		muzzleFlashSprite = Sprite.pistolFlash;
		shine = new AnimatedSprite(Spritesheet.pistolShine, 24, 24, 7, 4);
		maxAmmo = 480;
		standardAmmoBoxAmount = 48;
		recoil = 0.3;
		name = "Pistol";
		magSize = 6;
		magCount = magSize;
		reloadTime = 80;
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.pistol_shot.play();
		double xp = Math.cos(direction);
		double yp = Math.sin(direction);
		
		//	Raycast for AI
		Projectile pRay = new Ray(owner, x - 4 + 4 * xp, y + 4 * yp, angle, level);
		pRay.move(owner,  xp * 8,  yp * 8);
		level.add(pRay);
		
//		angle = angle + (random.nextDouble() - 0.5) / 12;
		Projectile p = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed, level);
		p.move(owner, xp * 8, yp * 8);
		level.add(p);
//		Projectile p2 = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed + 10, level);
//		p2.move(owner, xp * 8, yp * 8);
//		level.add(p2);
//		Projectile p3 = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed + 20, level);
//		p3.move(owner, xp * 8, yp * 8);
//		level.add(p3);
//		Projectile p4 = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed + 30, level);	// cool effect for more powerful gun, fire several of increasing speed
//		p4.move(owner, xp * 8, yp * 8);
//		level.add(p4);
//		Projectile p5 = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed + 40, level);
//		p5.move(owner, xp * 8, yp * 8);
//		level.add(p5);
//		Projectile p6 = new PistolBullet(owner, x - 4 + 4 * xp, y + 4 * yp, angle, speed + 50, level);
//		p6.move(owner, xp * 8, yp * 8);
//		level.add(p6);
		flashTimer = 4;
		shotsFired++;
		magCount--;
	}

	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 7);
	}
	
	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		this.attack(x, y, direction, 7 * bulletSpeedMultiplier);
	}
	
}
