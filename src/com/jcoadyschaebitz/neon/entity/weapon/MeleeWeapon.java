package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class MeleeWeapon extends Weapon {
	
	protected int attackCooldown;
	public int damage;
	protected Sprite slashSprite, rotSlashSprite;
	protected AnimatedSprite flash;
	protected double spriteOffset = 2.7;

	public MeleeWeapon(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);
	}
	
	public MeleeWeapon(Mob owner, int width, int height, Sprite sprite) {
		super((int) owner.getX(), (int) owner.getY(), width, height, sprite);
		this.owner = owner;
		owned = true;
		owner.addWeapon(this);
	}
	
	public void update() {
		if (owned) {
			if (owner.isRemoved()) remove();
			this.x = owner.getX();
			this.y = owner.getY();
		} else if (level.isPlayerInRad(this, 20) && !level.getPlayer().hasGun) {
			owner = level.getPlayer();
			owner.addWeapon(this);
			owned = true;
			this.x = owner.getX();
			this.y = owner.getY();
		}
		if (attackCooldown > 0) attackCooldown--;
	}
	
	public void beginPreAttackAnimations() {
	}
	
	public void projectileBlocked(Projectile projectile) {
		
	}
	
	public void updateSprite(double dir) {
		direction = dir;
		if (state == WeaponState.BLOCKING) {
			rotSprite = Sprite.rotateSprite(sprite, dir + Math.PI / 2, spriteWidth, spriteHeight);
			return;
		}
		rotSlashSprite = Sprite.rotateSprite(slashSprite, dir, slashSprite.getWidth(), slashSprite.getHeight());
//		if (dir > 0 && dir <= Math.PI / 2) {
//			rotSprite = Sprite.rotateSprite(sprite, dir + spriteOffset, spriteWidth, spriteHeight);
//		}
//		else if (dir > 0) {
//			rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir + spriteOffset, spriteWidth, spriteHeight));
//		}
//		if (dir < 0 && dir >= Math.PI / -2)	{
//			rotSprite = Sprite.rotateSprite(sprite, dir + spriteOffset, spriteWidth, spriteHeight);
//		}
//		else if (dir < 0) {
//			rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir + spriteOffset, spriteWidth, spriteHeight));
//		}
		rotSprite = Sprite.rotateSprite(sprite, direction, spriteWidth, spriteHeight);
	}
	
	public void renderOnOwner(Screen screen, int bob) {
		double xp = Math.cos(direction);
		double yp = Math.sin(direction);
		if (state == WeaponState.BLOCKING) screen.renderTranslucentSprite((int) (x + xRenderOffset + xp * 8), (int) (y + yRenderOffset + yp * 8/* + bob*/), rotSprite, true);
		else screen.renderTranslucentSprite((int) (x + xRenderOffset + xp * -16), (int) (y + yRenderOffset + yp * -16/* + bob*/), rotSprite, true);
//		if (flashTimer > 0) screen.renderTranslucentSprite((int) (x + xp * 12), (int) (y + yp * 12), muzzleFlashSprite, true);
//		if (muzzleGlowTimer > 0) screen.renderTranslucentSprite((int) (x + xp * 12), (int) (y + yp * 12), Sprite.enemy_bullet_1, true, 1 - ((double) muzzleGlowTimer / (double) attackBuildup));
	}
	
	public void render(Screen screen) {
		if (!owned) {
			screen.renderTranslucentSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
		}
	}

	public void hitReceived(Projectile projectile) {
	}

}
