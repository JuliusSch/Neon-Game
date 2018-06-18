package com.jcoadyschaebitz.neon.entity;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class Shield extends Entity {

	protected double xa, ya, direction;
	protected int duration;
	protected Sprite glow;
	protected AnimatedSprite firstAnim, firstAnimGlow, mainAnim, mainAnimGlow;
	protected RotCollisionBox bounds;
	protected Player player;
	protected boolean readyToRemove;

	public Shield(double x, double y, double direction, int duration, Player player) {
		xa = 30 * Math.cos(direction);
		ya = 30 * Math.sin(direction);
		this.x = x + xa - 8;
		this.y = y + ya - 4;
		this.duration = duration;
		this.direction = direction;
		this.player = player;
		sprite = Sprite.rotateSprite(Sprite.actionSkillFlash, direction, 32, 32);
		glow = Sprite.rotateSprite(Sprite.skillGlow, direction, 32, 32);
		firstAnim = new AnimatedSprite(Spritesheet.firstShieldAnimation, 32, 32, 8, 3);
		firstAnimGlow = new AnimatedSprite(Spritesheet.firstShieldGlowAnimation, 32, 32, 8, 3);
		mainAnim = new AnimatedSprite(Spritesheet.mainShieldAnimation, 32, 32, 4, 10);
		mainAnimGlow = new AnimatedSprite(Spritesheet.mainShieldGlowAnimation, 32, 32, 4, 10);
		int[] xCollPoints = { 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18 };
		int[] yCollPoints = { 2, 2, 4, 4, 6, 6, 8, 8, 11, 11, 13, 13, 15, 15, 18, 18, 21, 21, 23, 23, 26, 26, 28, 28, 31, 31 };
		bounds = new RotCollisionBox(xCollPoints, yCollPoints);
		bounds.rotPoints(direction, 32, 32);
		firstAnim.playOnce();
		firstAnimGlow.playOnce();
		player.getActionSkillManager().shieldCount++;
	}

	public void update() {
		firstAnim.update();
		firstAnimGlow.update();
		mainAnim.update();
		mainAnimGlow.update();
		time++;
		if (time >= duration || readyToRemove) remove();
		checkProjectileCollision();
		if (firstAnim.active) {
			sprite = Sprite.rotateSprite(firstAnim.getSprite(), direction, 32, 32);
			glow = Sprite.rotateSprite(firstAnimGlow.getSprite(), direction, 32, 32);
		} else {
			sprite = Sprite.rotateSprite(mainAnim.getSprite(), direction, 32, 32);
			glow = Sprite.rotateSprite(mainAnimGlow.getSprite(), direction, 32, 32);
		}
	}

	public void readyRemove() {
		readyToRemove = true;
		// player.getActionSkillManager().actionSkillActive = false;
		player.getActionSkillManager().shieldCount--;
		try {
			player.getSkillTreeManager().activeShields.remove(this);
		} catch (NullPointerException e) {
		}
	}

	public void changeDuration(int amount) {
		time -= amount;
	}

	public void checkProjectileCollision() {
		List<Projectile> ps = level.getProjectilesInRad((int) x + 16, (int) y + 16, 50);
		List<Entity> es = new ArrayList<Entity>();
		for (Projectile p : ps) {
			es.add(p);
		}
		List<Entity> es2 = bounds.checkForCollisions(es, x, y);
		for (Entity e : es2) {
			try {
				Projectile p = (Projectile) e;
				if (!p.isEnemyBullet()) continue;
				if (player.getSkillTreeManager().shieldReflectUnlocked) {
					p.shieldCollision(player.getSkillTreeManager().shieldReflectChance, direction, p);
				} else {
					p.collide(p.getIntX(), p.getIntY());
				}
			} catch (ClassCastException c) {
			}
		}
	}

	public void render(Screen screen) {
		for (int i = -8; i < 8; i++) {
			if (random.nextInt(120) == 0) {
				screen.renderSprite((int) x + 3, (int) y + i, sprite, true);
				screen.renderTranslucentSprite((int) x + 3, (int) y + i, glow, true, 0.4);
			}
			else {
				screen.renderSprite((int) x, (int) y + i, sprite, true);
				screen.renderTranslucentSprite((int) x, (int) y + i, glow, true, 0.4);
			}
		}
	}

}
