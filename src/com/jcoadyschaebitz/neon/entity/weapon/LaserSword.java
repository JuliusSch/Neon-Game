package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class LaserSword extends MeleeWeapon {

	public LaserSword(int x, int y) {
		super(x, y, 48, 48, Sprite.laserSword);
	}
	
	public LaserSword(Mob owner) {
		super(owner, 48, 48, Sprite.laserSword);
	}

	public void attack(double x, double y, double direction) {
		if (attackCooldown <= 0) {
//			SoundClip.laser_sword_slash.play();
			attackCooldown = 8;
			level.getPlayer().hitReceived(this);
			spriteOffset *= -1;
			slashSprite = Sprite.mirror(slashSprite);
		}
	}

	public void attack(double x, double y, double direction, double speed) {
	}

	protected void initiateValues() {
		sprite = Sprite.laserSword;
		damage = 6;
		slashSprite = Sprite.swordSlash2;
		glow = Sprite.laserSwordGlow;
		xRenderOffset = -16;
		yRenderOffset = -10;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		rotSlashSprite = slashSprite;
		rotGlow = glow;
	}

}
