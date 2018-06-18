package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.particle.MuzzleFlash;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.ShotgunPellet;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class Shotgun extends PlayerWeapon {

	public Shotgun(int x, int y, int ammoCount) {
		super(x, y, 32, 32, Sprite.shotgun, ammoCount);
		initiateValues();
	}

	public Shotgun(Player player, int ammoCount) {
		super(player, 32, 32, Sprite.shotgun, ammoCount);
		initiateValues();
	}

	public void initiateValues() {
		cooldown = 45;
		slotSprite = Sprite.shotgunSlotSprite;
		xRenderOffset = -7;
		yRenderOffset = -2;
		maxAmmo = 48;
		shine = new AnimatedSprite(Spritesheet.shotgunShine, 32, 32, 7, 4);
		standardAmmoBoxAmount = 12;
		recoil = 1.2;
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.shotgun_shot.play();
		for (int i = 0; i < 8; i++) {
			Projectile p = new ShotgunPellet(owner, x, y + 6, (angle + (random.nextDouble() - 0.5) / 2.2), speed + (random.nextDouble() - 0.5));
			level.add(p);
		}
		addFlash((int) x, (int) y, angle);
		shotsFired++;
	}
	
	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 8);
		addFlash((int) x, (int) y, angle);
	}

	public void attack(boolean multiplier, double x, double y, double angle, double bulletSpeedMultiplier) {
		this.attack(x, y, angle, 8 * bulletSpeedMultiplier);
		addFlash((int) x, (int) y, angle);
	}
	
	public void addFlash(int x, int y, double angle) {
		level.add(new MuzzleFlash(x - 8 + Math.cos(angle) * 25, y + Math.sin(angle) * 25, 2, Sprite.pistolFlash, Sprite.pistolFlashGlow, angle));
	}
}
