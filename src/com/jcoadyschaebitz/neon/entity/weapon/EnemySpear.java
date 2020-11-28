package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.MultiAttackWeapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class EnemySpear extends MeleeWeapon implements MultiAttackWeapon {

	public EnemySpear(Mob owner) {
		super(owner, 48, 48, Sprite.laserSword);
	}

	protected void initiateValues() {
		sprite = Sprite.laserSword;
		damage = 10;
		slashSprite = Sprite.laserSwordSlash;
		xRenderOffset = -16;
		yRenderOffset = -10;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		glow = Sprite.nullSprite;
		rotGlow = glow;
		rotSlashSprite = slashSprite;
		flash = new AnimatedSprite(Spritesheet.swordFlash, 64, 64, 3, 2);
	}

	public void attack(double x, double y, double direction) {
		level.add(new SwordSlash(owner, this, x + Math.cos(direction) * 14, y + Math.sin(direction) * 14, direction, flash));
	}

	public void attack(double x, double y, double direction, double speed) {
		
	}

	@Override
	public void secondaryAttack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tertiaryAttack() {
		// TODO Auto-generated method stub
		
	}

}
