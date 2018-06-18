package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public abstract class MobGun extends Weapon {

	public static int DAMAGE;
	public int recoil;

	public MobGun(int x, int y, int width, int height, Sprite sprite) {
		super(x, y, width, height, sprite);
	}

	public MobGun(Mob owner, int width, int height, Sprite sprite) {
		super((int) owner.getX(), (int) owner.getY(), width, height, sprite);
		this.sprite = sprite;
		this.owner = owner;
		owned = true;
		owner.addWeapon(this);
	}

	public void update() {
		if (owned) {
			if (owner.isRemoved()) remove();
			this.x = owner.getX();
			this.y = owner.getY() + 1;
		} 
	}
	
	public void recoil(double angle) {
		if (recoil == 0) return;
	}
	
	public void updateSprite(double dir) {
		
		xShootOffset = Math.cos(dir) * 12;
		yShootOffset = Math.sin(dir) * 12;
		
		if (dir > 0 && dir <= Math.PI / 2) rotSprite = Sprite.rotateSprite(sprite, dir, 32, 32);
		else if (dir > 0) rotSprite = Sprite.flipSprite(Sprite.rotateSprite(sprite, Math.PI - dir, 32, 32));
		if (dir < 0 && dir >= Math.PI / -2)	rotSprite = Sprite.rotateSprite(sprite, dir, 32, 32);
		else if (dir < 0) rotSprite = Sprite.flipSprite(Sprite.rotateSprite(sprite, Math.PI - dir, 32, 32));
	}
}
