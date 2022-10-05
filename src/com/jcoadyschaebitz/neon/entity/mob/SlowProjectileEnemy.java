package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.weapon.SlowEnemyGun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class SlowProjectileEnemy extends ShootingEnemy {

	public SlowProjectileEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.slowEnemyLeftWalking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.slowEnemyRightWalking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.slowEnemyLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.slowEnemyRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.slowEnemyLeftDamage, 24, 24, 4, 3);
		rightDamage = new AnimatedSprite(Spritesheet.slowEnemyRightDamage, 24, 24, 4, 3);
		leftDying = new AnimatedSprite(Spritesheet.slowEnemyLeftDying, 24, 24, 4, 3);
		rightDying = new AnimatedSprite(Spritesheet.slowEnemyRightDying, 24, 24, 4, 3);
		currentAnim = leftWalking;
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yCollisionValues = { 0, 0, 22, 22, 11, 11 };
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
		speed = 0.9;
		knockbackMultiplier = 0.8;
	}
	
	public void update() {
		super.update();
		if (weapon == null) level.add(new SlowEnemyGun(this));
		doMovement();
	}
	
	private void doMovement() {			//To be incorporated into behaviour tree.
//		if (moveDelay > random.nextInt(20) + 90) {
//			xa = 0;
//			ya = 0;
//		}
//		if (moveDelay >= 120) {
//			moveDelay = 0;
//			if (distanceP < minDist && random.nextInt(2) == 0) {
//				xa = Math.cos(directionP + Math.PI) * speed;
//				ya = Math.sin(directionP + Math.PI) * speed;
//			}
//			else if (distanceP > maxDist && random.nextInt(2) == 0) {
//				xa = Math.cos(directionP) * speed;
//				ya = Math.sin(directionP) * speed;
//			} else {
//				double dd = ((random.nextDouble() / 2) - 0.5);		
//				if (random.nextInt(2) == 0) {
//					dd += directionP + (Math.PI / 2);
//				} else {
//					dd += directionP - (Math.PI / 2);
//				}
//				xa = Math.cos(dd) * speed;
//				ya = Math.sin(dd) * speed;
//			}
//		}
	}

	protected void attackPlayer() {
		if (time % 90 == 0 && random.nextInt(4) != 0) {
			if (distanceP < viewRange && level.isSightline((int) x + 8, (int) y + 10, directionP, level.getPlayer())) {
				if (weapon != null && health > 0 && damageDelay <= 0) {
					weapon.attack(getMidX(), getMidY(), directionP, 1);
				}
			}
		}
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		// TODO Auto-generated method stub
		
	}

}
