package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.Item.AmmoBox;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public abstract class PlayerWeapon extends Weapon {

	public int cooldown, ammoCount, maxAmmo, standardAmmoBoxAmount, shotsFired;
	public Player player;
	public Sprite ammoSprite, slotSprite;
	protected AnimatedSprite shine;
	protected double recoil = 0.2;
	protected boolean shining = false;

	public PlayerWeapon(int x, int y, int width, int height, Sprite sprite, int ammoCount) {
		super(x, y, width, height, sprite);
		this.ammoCount = ammoCount;
		standardAmmoBoxAmount = 12;
		ammoSprite = Sprite.shotgun_ammo;
		direction = Math.abs(random.nextDouble() * 6);
		rotSprite = Sprite.rotateSprite(sprite, direction, spriteWidth, spriteHeight);
		shine = new AnimatedSprite(Spritesheet.pistolShine, 32, 32, 5, 4);
	}

	public PlayerWeapon(int x, int y, int width, int height, Sprite sprite) {
		this(x, y, width, height, sprite, 0);
	}

	public PlayerWeapon(Player player, int width, int height, Sprite sprite, int ammoCount) {
		this(player.getIntX(), player.getIntY(), width, height, sprite, ammoCount);
		this.player = player;
		owner = player;
	}

	public PlayerWeapon(Player player, int width, int height, Sprite sprite) {
		this(player.getIntX(), player.getIntY(), width, height, sprite);
		this.player = player;
		owner = player;
	}

	public void update() {
		time++;
		shine.update();
		if (!owned) {
			if (level.isPlayerInRad(this, 20)) {
				owned = true;
				player = level.getPlayer();
				owner = player;
				player.addWeapon(this);
			}
			if (time % 180 == 0) {
				shining = true;
				shine.playOnce();
			}
			if (time % 180 == shine.getTotalLength()) {
				shining = false;
			}
		}
		if (owned) {
			x = player.getX();
			y = player.getY() + 1;
		}
	}

	public void render(Screen screen) {
		if (!owned) {
			if (shining) rotSprite = Sprite.rotateSprite(shine.getSprite(), direction, 32, 32);
			else rotSprite = Sprite.rotateSprite(sprite, direction, 32, 32);
			screen.renderSprite((int) x, (int) y, rotSprite, true);
		}
	}
	
	public double getRecoil() {
		return recoil;
	}

	protected abstract void initiateValues();

	public abstract void attack(double x, double y, double direction);

	public abstract void attack(double x, double y, double direction, double speed);
	
	public abstract void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier);

	public void ammoChange(int i) {
		ammoCount += i;
		if (ammoCount > maxAmmo) ammoCount = maxAmmo;
	}

	public double checkAmmoPercent() {
		double result = (double) ammoCount / (double) maxAmmo;
		return result;
	}

	public void checkAmmoDrop(double chance, double x, double y) {
		if (random.nextDouble() < chance) {
			level.add(new AmmoBox((int) x, (int) y, this, standardAmmoBoxAmount));
		}
	}
	
	public int getCooldown() {
		return cooldown;
	}

}
