package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class TwoLeggedRobot extends ShootingEnemy {

	public TwoLeggedRobot(int x, int y) {
		super(x, y);
//		leftWalking = new AnimatedSprite(Spritesheet.tall_robot_left_walking, 32, 32, 8, 10);
//		rightWalking = new AnimatedSprite(Spritesheet.tall_robot_right_walking, 32, 32, 8, 10);
//		leftIdle = new AnimatedSprite(Spritesheet.tall_robot_left_idle, 32, 32, 4, 10);
//		rightIdle = new AnimatedSprite(Spritesheet.tall_robot_right_idle, 32, 32, 4, 10);
//		leftDamage = new AnimatedSprite(Spritesheet.tall_robot_left_idle, 32, 32, 4, 10);
//		rightDamage = new AnimatedSprite(Spritesheet.tall_robot_right_idle, 32, 32, 4, 10);
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = {0, 32, 0, 32};
		int[] yCollisionValues = {0, 0, 32, 32};
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 16;
		health = maxHealth;
		deadSpriteRight = Sprite.blueGrass;
		deadSpriteLeft= Sprite.blueGrass;
	}

	public void update() {
		time++;
		updateAnimSprites();

		if (time % (random.nextInt(60) + 30) == 0) {
			xa = ((double) random.nextInt(2)) * 0.6;
			ya = ((double) random.nextInt(2)) * 0.6;
			if (random.nextInt(2) == 0) {
				xa = 0;
				ya = 0;
			}
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		if (health <= 0) remove();
	}

}
