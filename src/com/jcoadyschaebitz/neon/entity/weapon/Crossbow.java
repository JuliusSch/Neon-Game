package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.input.Mouse;
import com.jcoadyschaebitz.neon.sound.SoundClip;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class Crossbow extends PlayerWeapon {

	public Crossbow(int x, int y, int ammoCount) {
		super(x, y, 32, 32, Sprite.crossbow, ammoCount);
		initiateValues();
	}

	public Crossbow(Player player, int ammoCount) {
		super(player, 32, 32, Sprite.crossbow, ammoCount);
		initiateValues();
	}

	protected void initiateValues() {
		cooldown = 80;
		xRenderOffset = -7;
		yRenderOffset = -3;
		sprite = Sprite.crossbow;
		slotSprite = Sprite.crossbowSlotSprite;
		shine = new AnimatedSprite(Spritesheet.crossbowShine, 32, 32, 7, 4);
		maxAmmo = 48;
		recoil = 1;
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.crossbow_shot.play();
		Projectile p = new Bolt(owner, x + xShootOffset, y + 6 + yShootOffset, angle, speed);
		level.add(p);
		addFlash((int) x, (int) y, angle);
		shotsFired++;
	}
	
	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 9);
		addFlash((int) x, (int) y, angle);
	}
	
	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		this.attack(x, y, direction, 18 * bulletSpeedMultiplier);
		addFlash((int) x, (int) y, direction);
	}

	public void render(Screen screen) {
		if (owned && player.weapon == this) {
			screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
			int xx = (Mouse.getX() - Game.getWindowWidth() / 2 - Game.getXRenderOffset()) * 10;
			int yy = (Mouse.getY() - Game.getWindowHeight() / 2) * 10;
			double distance = Math.sqrt((xx * xx) + (yy * yy));
			Vector2i targetPoint = level.castRay((int) (x + 9 + xShootOffset), (int) (y + 14 + yShootOffset), direction);
			if (distance > 300) screen.renderLine((int) (x + 9 + xShootOffset), (int) (y + 14 + yShootOffset), targetPoint.getX(), targetPoint.getY(), 0x88ff0000);
		}
		if (!owned){
			if (shining) rotSprite = Sprite.rotateSprite(shine.getSprite(), direction, 32, 32);
			else rotSprite = Sprite.rotateSprite(sprite, direction, 32, 32);
			screen.renderSprite((int) x, (int) y, rotSprite, true);
		}
	}

	public void addFlash(int x, int y, double angle) {
	}
}
