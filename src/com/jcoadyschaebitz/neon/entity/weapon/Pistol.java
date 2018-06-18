package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.particle.MuzzleFlash;
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
		cooldown = 10;		//13
		slotSprite = Sprite.pistolSlotSprite;
		sprite = Sprite.pistol;
		shine = new AnimatedSprite(Spritesheet.pistolShine, 32, 32, 7, 4);
		maxAmmo = 320;
		standardAmmoBoxAmount = 80;
		recoil = 0.3;
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.pistol_shot.play();
		Projectile p = new PistolBullet(owner, x, y + 6, angle + (random.nextDouble() - 0.5) / 12, speed);
		level.add(p);
		addFlash((int) x, (int) y, angle);
		shotsFired++;

	}

	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 10);
		addFlash((int) x, (int) y, angle);
	}
	
	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		this.attack(x, y, direction, 10 * bulletSpeedMultiplier);
		addFlash((int) x, (int) y, direction);
	}

	public void addFlash(int x, int y, double angle) {
		level.add(new MuzzleFlash(x - 8 + Math.cos(angle) * 25, y + Math.sin(angle) * 25, 2, Sprite.pistolFlash, Sprite.pistolFlashGlow, angle));
	}
}
