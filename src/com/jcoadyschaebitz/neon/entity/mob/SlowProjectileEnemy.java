package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.weapon.SlowEnemyGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class SlowProjectileEnemy extends ShootingEnemy {

	public SlowProjectileEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.slowEnemy_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.slowEnemy_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.slowEnemy_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.slowEnemy_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.slowEnemy_left_damage, 24, 24, 4, 3);
		rightDamage = new AnimatedSprite(Spritesheet.slowEnemy_right_damage, 24, 24, 4, 3);
		leftDying = new AnimatedSprite(Spritesheet.slowEnemy_left_dying, 24, 24, 4, 3);
		rightDying = new AnimatedSprite(Spritesheet.slowEnemy_right_dying, 24, 24, 4, 3);
		currentAnim = leftWalking;
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = {0, 16, 0, 16, 0, 16};
		int[] yCollisionValues = {0, 0, 22, 22, 11, 11};
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 8, 8, 22, 22 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 36;
		health = maxHealth;
		xpAmount = 8;
		deadSpriteRight = Sprite.slowGuy_dead_right;
		deadSpriteLeft = Sprite.slowGuy_dead_left;
		viewRange = 250;
		speed = 0.8;
		knockbackMultiplier = 0.8;
	}
	
	protected void doSpecificUpdates() {

		if (weapon == null) level.add(new SlowEnemyGun(this));

		if (time % (random.nextInt(60) + 30) == 0) {
			xa = (random.nextInt(3) - 1) * speed;
			ya = (random.nextInt(3) - 1) * speed;
			if (random.nextInt(3) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		if (beingKnockedBack) {
			move(knockbackX, knockbackY);
			knockbackX -= knockbackX * 0.1;
			knockbackY -= knockbackY * 0.1;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;
	}
	
	protected void attemptShoot(double direction, double distance) {
		if (time % 90 == 0 && random.nextInt(4) != 0) {
			if (distance < viewRange && level.isSightLine((int) x + 8, (int) y + 10, direction, level.getPlayer())) {
				if (weapon != null && health > 0 && damageDelay <= 0) {
					weapon.attack(x, y, direction, 1);
				}
			}
		}
	}

}
