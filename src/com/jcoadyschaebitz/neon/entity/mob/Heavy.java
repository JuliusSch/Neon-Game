package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.weapon.MiniGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class Heavy extends ShootingEnemy {

	private double secondAttackDelayMeter = 0;

	public Heavy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.heavyLeftWalking, 24, 24, 8, 5);
		rightWalking = new AnimatedSprite(Spritesheet.heavyRightWalking, 24, 24, 8, 5);
		leftIdle = new AnimatedSprite(Spritesheet.heavyLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.heavyRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.heavyLeftDamage, 24, 24, 4, 5);
		rightDamage = new AnimatedSprite(Spritesheet.heavyRightDamage, 24, 24, 4, 5);
		leftDying = new AnimatedSprite(Spritesheet.heavyLeftDying, 24, 24, 4, 5);
		rightDying = new AnimatedSprite(Spritesheet.heavyRightDying, 24, 24, 4, 5);
		currentAnim = leftWalking;
		sprite = rightWalking.getSprite();
		viewRange = 200;
		int[] xCollisionValues = { 0, 24, 0, 24, 12, 12, 0, 24 };
		int[] yCollisionValues = { 0, 0, 24, 24, 0, 24, 12, 12 };
		int[] xTileCollisionValues = { 0, 24, 0, 24 };
		int[] yTileCollisionValues = { 8, 8, 24, 24 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 90;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
		xpAmount = 20;
		speed = 0.7;
		knockbackMultiplier = 0.6;
	}

	public void update() {
		super.update();
		if (time % (random.nextInt(120) + 30) == 0) { // random movement
			xa = (random.nextInt(3) - 1) * speed;
			ya = (random.nextInt(3) - 1) * speed;
			if (random.nextInt(2) == 1) {
				xa = 0;
				ya = 0;
			}
		}
		if (weapon == null) level.add(new MiniGun(this));

		if (beingKnockedBack) {
			move(knockbackX, knockbackY, true);
			knockbackX -= knockbackX * 0.2;
			knockbackY -= knockbackY * 0.2;
		}

		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;
	}

	protected void attackPlayer() {
		if (time % 120 == 0 && random.nextInt(1) == 0) {
			if (distanceP < viewRange && weapon != null) {
				if (random.nextInt(2) == 1) {
					weapon.attack(x, y, directionP, 3);
					weapon.attack(x, y, directionP - 0.15, 3);
					weapon.attack(x, y, directionP + 0.15, 3);
				} else secondAttackDelayMeter = 60;
			}
		}
		if (secondAttackDelayMeter > 0) {
			secondAttackDelayMeter--;
			if (secondAttackDelayMeter % 5 == 0) weapon.attack(x, y, directionP - 1 + (secondAttackDelayMeter / 30), 1);
		}
	}

	public void checkForDrops() {
		double healthPercent = level.getPlayer().getHealth() / level.getPlayer().maxHealth;
		double healthDropChance = 0;
		if (healthPercent <= 0.6) healthDropChance = 0.2;
		if (healthPercent <= 0.4) healthDropChance = 0.3;
		if (healthPercent <= 0.2) healthDropChance = 0.4;
		if (random.nextDouble() < healthDropChance) level.add(new HealthKit((int) x, (int) y));
		for (int i = 0; i < level.getPlayer().slots.size(); i++) {
			double percent;
			if (level.getPlayer().slots.get(i).weapon != null) {
				percent = level.getPlayer().slots.get(i).weapon.checkAmmoPercent();
			} else return;
			if (percent >= 0.4 && percent < 0.6) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.1, x, y);
			if (percent >= 0.2 && percent < 0.4) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.2, x, y);
			if (percent < 0.2) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.35, x, y);
		}
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		// TODO Auto-generated method stub

	}
}
