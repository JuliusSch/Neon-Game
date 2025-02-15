package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.EliteRifleBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class EliteRifle extends Gun {

	public EliteRifle(Mob owner) {
		super(owner, 48, 48, Sprite.eliteRifle);
		initiateValues();
	}

	@Override
	protected void initiateValues() {
		sprite = Sprite.eliteRifle;
		DAMAGE = 4;
		xRenderOffset = -12;
		yRenderOffset = -4;
		rotSprite = Sprite.rotateSprite(sprite, Math.abs(random.nextDouble() * 3), 4, 48);
	}

	@Override
	public void attack(double x, double y, double direction) {
		attack(x, y, direction, 5);
	}

	@Override
	public void attack(double x, double y, double direction, double speed) {
		double xx = Math.cos(direction) * 12;
		double yy = Math.sin(direction) * 12;
		Projectile p = new EliteRifleBullet(owner, x - 9 + xx, y - 8 + yy, direction, speed + (random.nextDouble() / 2), level);
		level.add(p);
		addFlash((int) x, (int) y, direction);
	}
	
	private void addFlash(int x, int y, double direction) {
		
	}
	
	public void renderOnOwner(Screen screen, int bob) {
		double xp = Math.cos(direction);
		double yp = Math.sin(direction);
		screen.renderTranslucentSprite((int) (x + xRenderOffset + xp * 4), (int) (y + yRenderOffset + yp * 4/* + bob */), rotSprite, true);
//		if (flashTimer > 0) screen.renderTranslucentSprite((int) (x + xp * 12), (int) (y + yp * 12), muzzleFlashSprite, true);
		if (muzzleGlowTimer > 0) screen.renderTranslucentSprite((int) (x + 4 + xp * 30), (int) (y + 12 + yp * 30), Sprite.enemy_bullet_2, true, 1 - ((double) muzzleGlowTimer / (double) attackBuildup));
	}

}
