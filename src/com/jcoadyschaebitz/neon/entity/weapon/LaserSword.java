package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.LaserShot;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.SwordSlash;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.sound.SoundClip;

public class LaserSword extends MeleeWeapon {

	public LaserSword(int x, int y) {
		super(x, y, 48, 48, Sprite.laserSword);
	}
	
	public LaserSword(Mob owner) {
		super(owner, 48, 48, Sprite.laserSword);
	}

	public void attack(double x, double y, double direction) {
		if (attackCooldown <= 0) {
			SoundClip.laser_sword_slash.play();
			attackCooldown = 8;
			Projectile p = new SwordSlash(owner, x + owner.getSpriteW() / 2, y + owner.getSpriteH() / 2, direction, 0.6, 12, level);
			level.add(p);
			spriteOffset *= -1;
			slashSprite = Sprite.mirror(slashSprite);
		}
	}

	public void attack(double x, double y, double direction, double speed) {
	}
	
	public int getWeaponAttackBuildup(boolean isSecondaryAttack) {
		return isSecondaryAttack ? attackBuildup * 3 : attackBuildup;
	}
	
	protected void initiateValues() {
		sprite = Sprite.laserSword;
		damage = 6;
		slashSprite = Sprite.swordSlash2;
		xRenderOffset = -16;
		yRenderOffset = -10;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		rotSlashSprite = slashSprite;
		attackBuildup = 10;
	}
	
	public void secondaryAttack(double x, double y, double direction) {
		if (attackCooldown <= 0) {
			attackCooldown = 8;
			double xp = Math.cos(direction) * 8;
			double yp = Math.sin(direction) * 8;
			Projectile p = new LaserShot(owner, x + xp + 8, y + yp + 8, direction, 5, level);
			level.add(p);
		}
	}

}
