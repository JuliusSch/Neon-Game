package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class MeleeWeapon extends Weapon {
	
	protected int attackCooldown;
	public int damage;
	protected Sprite slashSprite, rotSlashSprite, glow, rotGlow;
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
	
	public void updateSprite(double dir) {
		rotSlashSprite = Sprite.rotateSprite(slashSprite, dir, slashSprite.getWidth(), slashSprite.getHeight());
		if (dir > 0 && dir <= Math.PI / 2) {
			rotSprite = Sprite.rotateSprite(sprite, dir + spriteOffset, spriteWidth, spriteHeight);
			rotGlow = Sprite.rotateSprite(glow, dir + spriteOffset, glow.getWidth(), glow.getHeight());
		}
		else if (dir > 0) {
			rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir + spriteOffset, spriteWidth, spriteHeight));
			rotGlow = Sprite.mirror(Sprite.rotateSprite(glow, Math.PI - dir + spriteOffset, glow.getWidth(), glow.getHeight()));
		}
		if (dir < 0 && dir >= Math.PI / -2)	{
			rotSprite = Sprite.rotateSprite(sprite, dir + spriteOffset, spriteWidth, spriteHeight);
			rotGlow = Sprite.rotateSprite(glow, dir + spriteOffset, glow.getWidth(), glow.getHeight());
		}
		else if (dir < 0) {
			rotSprite = Sprite.mirror(Sprite.rotateSprite(sprite, Math.PI - dir + spriteOffset, spriteWidth, spriteHeight));
			rotGlow = Sprite.mirror(Sprite.rotateSprite(glow, Math.PI - dir + spriteOffset, glow.getWidth(), glow.getHeight()));
		}
	}
	
	public void render(Screen screen) {
		if (owned && owner.weapon == this) {
			screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
			screen.renderTranslucentSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotGlow, true, 0.2);
		}
		if (!owned) screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
	}

	public void hitReceived(Projectile projectile) {
	}

}
