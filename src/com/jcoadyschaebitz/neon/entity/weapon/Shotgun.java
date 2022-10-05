package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.ShotgunPellet;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class Shotgun extends PlayerWeapon {

	public Shotgun(int x, int y, int ammoCount) {
		super(x, y, 24, 24, Sprite.shotgun, ammoCount);
		initiateValues();
	}

	public Shotgun(Player player, int ammoCount) {
		super(player, 24, 24, Sprite.shotgun, ammoCount);
		initiateValues();
	}

	public void initiateValues() {
		cooldown = 45;
		slotSprite = Sprite.shotgunSlotSprite;
		xRenderOffset = -3;
		yRenderOffset = 2;
		maxAmmo = 60;
		shine = new AnimatedSprite(Spritesheet.shotgunShine, 24, 24, 7, 4);
		standardAmmoBoxAmount = 12;
		recoil = 0.8;
		name = "Shotgun";
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.shotgun_shot.play();
		double xp = Math.cos(direction) * 25;
		double yp = Math.sin(direction) * 25;
		for (int i = 0; i < 8; i++) {
			Projectile p = new ShotgunPellet(owner, x + xp, y + yp + 6, (angle + (random.nextDouble() - 0.5) / 2.2), speed + (random.nextDouble() - 0.5), level);
			level.add(p);
		}
		shotsFired++;
	}
	
	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 24);
	}

	public void attack(boolean multiplier, double x, double y, double angle, double bulletSpeedMultiplier) {
		this.attack(x, y, angle, 8 * bulletSpeedMultiplier);
	}

}
