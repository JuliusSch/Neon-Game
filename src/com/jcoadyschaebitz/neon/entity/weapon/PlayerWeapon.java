package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.entity.IInteractableItem;
import com.jcoadyschaebitz.neon.entity.Item.AmmoBox;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.input.InputManager.InputAction;

public abstract class PlayerWeapon extends Weapon implements IInteractableItem {

	public int cooldown, ammoCount, maxAmmo, standardAmmoBoxAmount, shotsFired, magSize = 100, magCount = magSize, reloadTime = 120;
	public Player player;
	public Sprite ammoSprite, slotSprite;
	protected AnimatedSprite shine;
	protected double recoil = 0.2;
	protected boolean shining = false;
	protected String name;
	protected Font font;
	protected boolean showPrompt;

	public PlayerWeapon(int x, int y, int width, int height, Sprite sprite, int ammoCount) {
		super(x, y, width, height, sprite);
		this.ammoCount = ammoCount;
		standardAmmoBoxAmount = 12;
		ammoSprite = Sprite.shotgun_ammo;
		direction = Math.abs(random.nextDouble() * Math.PI * 2);
		rotSprite = Sprite.rotateSprite(sprite, direction, spriteWidth, spriteHeight);
		shine = new AnimatedSprite(Spritesheet.pistolShine, 24, 24, 7, 4);
		font = new Font(Font.SIZE_12x12, 0xffffffff, 1);
	}

	public void mouseReleased() {
	}

	public void beginPreAttackAnimations() {

	}

	public String toString() {
		return name;
	}
	
	@Override
	public void setShowPrompt(boolean val) {
		showPrompt = val;
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
	
	public void onInteract(InputAction action) {
		owned = true;
		rotSprite = sprite;
		player = level.getPlayer();
		owner = player;
		player.addWeapon(this);
	}
	
	public void disown() {
		owned = false;
		owner = null;
	}

	public void update() {
		time++;
		shine.update();
		if (owned) {
			x = player.getX();
			y = player.getY() + 1;
		} else {
			if (time % 180 == 0) {
				shining = true;
				shine.playOnce();
			}
			if (time % 180 == shine.getTotalLength()) {
				shining = false;
			}
		}
		if (flashTimer > 0) flashTimer--;
	}

	public void render(Screen screen) {
		if (!owned) {
			if (shining) rotSprite = Sprite.rotateSprite(shine.getSprite(), direction, 24, 24);
			else rotSprite = Sprite.rotateSprite(sprite, direction, 24, 24);
			screen.renderSprite((int) x - 12, (int) y - 12, rotSprite, true);
			if (showPrompt) font.render((int) x - 6, (int) y - 6, "E", screen, true);
		}
	}

	public double getRecoil() {
		return recoil;
	}

	protected abstract void initiateValues();

	public abstract void attack(double x, double y, double direction);

	public abstract void attack(double x, double y, double direction, double speed);

	public abstract void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier);

	public void hitReceived(Projectile projectile) {
	}

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
		if (magCount == 0) {
			magCount = magSize;
			return reloadTime;
		}
		return cooldown;
	}

}
