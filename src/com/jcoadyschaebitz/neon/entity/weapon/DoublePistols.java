package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.SilverBullet;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class DoublePistols extends Gun {

	boolean alternate;
	int defaultSpeed;
	
	public DoublePistols(Mob owner) {
		super(owner, 32, 32, Sprite.doublePistols);
		attackBuildup = 10;
	}	

	@Override
	protected void initiateValues() {
		DAMAGE = 1;
		sprite = Sprite.doublePistols;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 32, 32);
		xRenderOffset = -7;
		yRenderOffset = -2;
		recoil = 2;
		defaultSpeed = 4;
	}

	@Override
	public void attack(double x, double y, double direction) {
		if (alternate) attack(x, y, direction, defaultSpeed);
		else secondaryAttack(x, y, direction);
		alternate = !alternate;
	}

	@Override
	public void attack(double x, double y, double direction, double speed) {
		level.add(new SilverBullet(owner, x, y, direction, defaultSpeed, level));
	}

	
	public void secondaryAttack(double x, double y, double direction) {
		level.add(new SilverBullet(owner, x, y + 8, direction, defaultSpeed, level));
	}

	public void renderOnOwner(Screen screen, int bob) {
		screen.renderSprite((int) x + xRenderOffset - 4, (int) y + yRenderOffset/* + bob*/, rotSprite, true);
		screen.renderSprite((int) x + xRenderOffset + 4, (int) y + yRenderOffset/* + bob*/, rotSprite, true);
		double xp = Math.cos(direction) * 12;
		double yp = Math.sin(direction) * 12 + 6;
//		screen.renderSprite((int) (x + xRenderOffset - xp / 3), (int) (y + yRenderOffset/* + bob*/ - yp / 3), rotSprite, true);
		if (flashTimer > 0) screen.renderTranslucentSprite((int) (x + xp), (int) (y + yp), muzzleFlashSprite, true);
		if (muzzleGlowTimer > 0) screen.renderTranslucentSprite((int) (x + xp), (int) (y + yp), Sprite.enemy_bullet_1, true, 1 - ((double) muzzleGlowTimer / (double) attackBuildup));
	}
	
}
