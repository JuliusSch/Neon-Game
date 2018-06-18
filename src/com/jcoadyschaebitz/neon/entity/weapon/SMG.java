package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.particle.MuzzleFlash;
import com.jcoadyschaebitz.neon.entity.projectile.PistolBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class SMG extends MobGun {

	public SMG(int x, int y) {
		super(x, y, 32, 32, Sprite.smg);
		initiateValues();
	}
	
	public SMG(Mob owner) {
		super(owner, 32, 32, Sprite.smg);
		initiateValues();
	}
	
	public void initiateValues() {
		DAMAGE = 1;
		sprite = Sprite.smg;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		xRenderOffset = -7;
		yRenderOffset = -2;
		recoil = 2;
	}

	public void attack(double x, double y, double angle) {
		SoundClip.pistol_shot.play();
		Projectile p = new PistolBullet(owner, x + 8, y + 10, angle + (random.nextDouble() - 0.5) / 4, 2);
		level.add(p);
		addFlash((int) x, (int) y, angle);
	}

	public void attack(double x, double y, double angle, double speed) {
	}
	
	public void addFlash(int x, int y, double angle) {
		level.add(new MuzzleFlash(x, y, 10, Sprite.pistolFlash, Sprite.pistolFlashGlow, angle));
	}

}
