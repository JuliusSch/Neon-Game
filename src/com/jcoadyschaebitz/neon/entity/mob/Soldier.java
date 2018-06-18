package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.weapon.EnemyGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class Soldier extends ShootingEnemy {

	public Soldier(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.soldier_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.soldier_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.soldier_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.soldier_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.soldier_left_damage, 24, 24, 6, 3);
		rightDamage = new AnimatedSprite(Spritesheet.soldier_right_damage, 24, 24, 6, 3);
		leftDying = new AnimatedSprite(Spritesheet.soldier_left_dying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.soldier_right_dying, 24, 24, 10, 3);
		currentAnim = leftWalking;
		sprite = rightWalking.getSprite();
		int[] xEntityCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yEntityCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xEntityCollisionValues, yEntityCollisionValues);
		maxHealth = 12;
		xpAmount = 3;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
		viewRange = 240;
	}

	protected void doSpecificUpdates() {
		if (weapon == null) level.add(new EnemyGun(this));

		if (time % (random.nextInt(60) + 30) == 0) {
			int distanceToPlayer = (int) Vector2i.getDistance(new Vector2i(level.getPlayer().getIntX(), level.getPlayer().getIntY()), new Vector2i((int) x, (int) y));
			if (distanceToPlayer > 120 && distanceToPlayer < viewRange + 100 && random.nextInt(2) == 0) {
				xa = Math.cos(directionToPlayer);
				ya = Math.sin(directionToPlayer);
			} else {
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				if (random.nextInt(3) == 0) {
					xa = 0;
					ya = 0;
				}
			}
		}

		if (beingKnockedBack) {
			move(knockbackX, knockbackY);
			knockbackX -= knockbackX * 0.08;
			knockbackY -= knockbackY * 0.08;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;
	}

	protected void attemptShoot(double direction, double distance) {
		if (time % (70 + random.nextInt(20)) == 0 && random.nextInt(2) == 0) {
			if (distance < viewRange && level.isSightLine((int) x + 8, (int) y + 10, direction, level.getPlayer())) {
				if (weapon != null && health > 0 && damageDelay <= 0) {
					weapon.attack(x, y, direction + (random.nextDouble() - 0.5) / 4, 2);
				}
			}
		}
	}
}
