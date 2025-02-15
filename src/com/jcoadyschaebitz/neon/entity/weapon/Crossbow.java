package com.jcoadyschaebitz.neon.entity.weapon;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.input.InputManager.InputType;
import com.jcoadyschaebitz.neon.sound.SoundClip;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Crossbow extends PlayerWeapon {

	public Crossbow(int x, int y, int ammoCount) {
		super(x, y, 24, 24, Sprite.crossbow, ammoCount);
		initiateValues();
	}

	public Crossbow(Player player, int ammoCount) {
		super(player, 24, 24, Sprite.crossbow, ammoCount);
		initiateValues();
	}

	protected void initiateValues() {
		cooldown = 60;
		xRenderOffset = -3;
		yRenderOffset = 1;
		xShootOffset = -4;
		yShootOffset = -3;
		sprite = Sprite.crossbow;
		slotSprite = Sprite.crossbowSlotSprite;
		shine = new AnimatedSprite(Spritesheet.crossbowShine, 24, 24, 7, 4);
		maxAmmo = 48;
		standardAmmoBoxAmount = 12;
		recoil = 0.3;
		name = "Crossbow";
	}

	public void attack(double x, double y, double angle, double speed) {
		SoundClip.crossbow_shot.play();
		double xp = Math.cos(direction) * 20;
		double yp = Math.sin(direction) * 20;
		Projectile p = new Bolt(owner, x + xShootOffset + xp, y + 6 + yShootOffset + yp, angle, speed, level);
		level.add(p);
		addFlash((int) x, (int) y, angle);
		shotsFired++;
	}
	
	public void attack(double x, double y, double angle) {
		this.attack(x, y, angle, 24);
		addFlash((int) x, (int) y, angle);
	}
	
	public void attack(boolean multiplier, double x, double y, double direction, double bulletSpeedMultiplier) {
		this.attack(x, y, direction, 12 * bulletSpeedMultiplier);
		addFlash((int) x, (int) y, direction);
	}
	
	public void render(Screen screen) {
		if (owned && player.weapon == this) {
			screen.renderSprite((int) x + xRenderOffset, (int) y + yRenderOffset, rotSprite, true);
			int xx = (int) (Game.getInputManager().getMouseXRelMidWithBars());
			int yy = (int) (Game.getInputManager().getMouseYRelMid());
			
			// Change to include gamepad
			if (Game.getUIManager().getCurrentInputType() == InputType.KEYBOARD) {
				Vec2i targetPoint = level.castRay(new Vec2i((int) (x + 8), (int) (y + 14)), new Vec2d(xx, yy), 360, false, false);
				screen.renderLine((int) (x + 8), (int) (y + 14), targetPoint.x, targetPoint.y, 0x88FF002F, 0.5);				
			} else {
				Vec2d dir = Game.getInputManager().getLastJoystickDirectionVector();
				Vec2i targetPoint = level.castRay(new Vec2i((int) (x + 8), (int) (y + 14)), dir, 360, false, false);
				screen.renderLine((int) (x + 8), (int) (y + 14), targetPoint.x, targetPoint.y, 0x88FF002F, 0.5);				
			}
		}
		if (!owned){
			if (shining) rotSprite = Sprite.rotateSprite(shine.getSprite(), direction, 24, 24);
			else rotSprite = Sprite.rotateSprite(sprite, direction, 24, 24);
			screen.renderSprite((int) x, (int) y, rotSprite, true);
		}
	}

	public void addFlash(int x, int y, double angle) {
	}
}
