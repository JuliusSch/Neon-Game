package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.weapon.LaserSword;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class MeleeEnemy extends Mob {

	public MeleeEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.swordEnemyLeftWalking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.swordEnemyRightWalking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.swordEnemyLeftIdle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.swordEnemyRightIdle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.swordEnemyLeftDamage, 24, 24, 6, 3);
		rightDamage = new AnimatedSprite(Spritesheet.swordEnemyRightDamage, 24, 24, 6, 3);
		leftDying = new AnimatedSprite(Spritesheet.swordEnemyLeftDying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.swordEnemyRightDying, 24, 24, 10, 3);
		currentAnim = leftIdle;
		deadSpriteRight = Sprite.swordGuy_dead_right;
		deadSpriteLeft = Sprite.swordGuy_dead_left;
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = { 0, 16, 0, 16, 0, 16 };
		int[] yCollisionValues = { 0, 0, 22, 22, 11, 11 };
		int[] xTileCollisionValues = { 0, 16, 0, 16 };
		int[] yTileCollisionValues = { 12, 12, 22, 22 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 16;
		health = maxHealth;
		speed = 1.5 + (random.nextDouble() / 8);
		xpAmount = 4;
	}

	public void update() {
		super.update();
		if (weapon == null) level.add(new LaserSword(this));

		if (damageDelay == 0) {
			if (xa != 0 || ya != 0) {
				move(xa, ya, true);
				walking = true;
			} else {
				walking = false;
			}
		}
		if (weapon != null) {
			if (aggro) weapon.updateSprite(directionP);
			weapon.update();
		}
		if (distanceP < 250 && playerSightline()) aggro = true;
//		if (distanceP < 280 && distanceP > 20 && aggro == true) { // Incorporate into behaviour tree.
//			if (time % 30 + random.nextInt(15) == 0) {
//				if (random.nextInt(6) == 0) {
//					pursue = false;
//					xa += random.nextInt(3) - 1;
//					ya += random.nextInt(3) - 1;
//				} else {
//					pursue = true;
//				}
//			}
//			if (pursue) {
//				xa = 0;
//				ya = 0;
//				if (px < x) xa -= speed;
//				if (px > x) xa += speed;
//				if (py < y) ya -= speed;
//				if (py > y) ya += speed;
//				if (distanceP < 30 && distanceP > 20) {
//					xa = 0;
//					ya = 0;
//				}
//			}
//		}
//
//		if (distanceP < 40) {
//			if (time % 90 == 0 && damageDelay == 0) {
//				weapon.attack(x, y, directionP);
//				moveDelay = 20;
//			}
//		}
	}

	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		// TODO Auto-generated method stub
		
	}

}
