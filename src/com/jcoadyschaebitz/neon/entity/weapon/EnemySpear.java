package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.MultiAttackWeapon;
import com.jcoadyschaebitz.neon.entity.projectile.LaserShot;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.SwordSlash;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class EnemySpear extends MeleeWeapon implements MultiAttackWeapon {

	public EnemySpear(Mob owner) {
		super(owner, 48, 48, Sprite.laserSword);
	}

	protected void initiateValues() {
		sprite = Sprite.energySpear;
		damage = 10;
		slashSprite = Sprite.laserSwordSlash;
		xRenderOffset = -4;
		yRenderOffset = 0;
		weaponLength = 12;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		rotSlashSprite = slashSprite;
		flash = new AnimatedSprite(Spritesheet.swordFlash, 64, 64, 3, 2);
	}

	public void attack(double x, double y, double direction) {
//		level.add(new SwordSlash(owner, this, x - 24 + Math.cos(direction) * 14, y - 24 + Math.sin(direction) * 14, direction, flash));
//		if (attackCooldown <= 0) {
			SoundClip.laser_sword_slash.play();
//			attackCooldown = 8;
			Projectile p = new SwordSlash(owner, x + Math.cos(direction) * 14/* + owner.getSpriteW() / 2 */, y + Math.sin(direction) * 14/*y + owner.getSpriteH() / 2*/, direction, 0.6, 42, level);
			level.add(p);
//			spriteOffset *= -1;
//			slashSprite = Sprite.mirror(slashSprite);
//		}
	}

	public void attack(double x, double y, double direction, double speed) {
	}

	@Override
	public void secondaryAttack(double x, double y, double direction) {
		if (attackCooldown <= 0) {
			attackCooldown = 8;
			double xp = Math.cos(direction) * 8;
			double yp = Math.sin(direction) * 8;
			Projectile p = new LaserShot(owner, x + xp + 8, y + yp + 8, direction, 5, level);
			level.add(p);
		}
	}

	@Override
	public void tertiaryAttack(double x, double y, double direction) {
	}
}
