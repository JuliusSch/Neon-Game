package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.EnemyMob;
import com.jcoadyschaebitz.neon.entity.weapon.LaserSword;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class SwordEnemy extends EnemyMob {

	protected int moveDelay;
	private boolean pursue = false;

	public SwordEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.swordEnemy_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.swordEnemy_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.swordEnemy_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.swordEnemy_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.swordEnemy_left_damage, 24, 24, 6, 3);
		rightDamage = new AnimatedSprite(Spritesheet.swordEnemy_right_damage, 24, 24, 6, 3);
		leftDying = new AnimatedSprite(Spritesheet.swordEnemy_left_dying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.swordEnemy_right_dying, 24, 24, 10, 3);
		currentAnim = leftWalking;
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
		if (weapon == null) level.add(new LaserSword(this));
		if (damageDelay > 0) damageDelay--;
		if (moveDelay > 0) moveDelay--;

		if (aggro) {
			if (level.getPlayer().getX() > x) dir = Direction.RIGHT;
			if (level.getPlayer().getX() < x) dir = Direction.LEFT;
		}

		time++;

		if (moveDelay == 0 && damageDelay == 0) {
			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
			}
		}
		double px = level.getPlayer().getX();
		double py = level.getPlayer().getY();
		double dx = px - x;
		double dy = py - y;
		double direction = Math.atan2(dy, dx);

		if (weapon != null) {
			if (aggro) weapon.updateSprite(direction);
			weapon.update();
		}

		double distanceToPlayer = Vector2i.getDistance(new Vector2i((int) px, (int) py), new Vector2i((int) x, (int) y));
		if (distanceToPlayer < 250) aggro = true;

		if (distanceToPlayer < 280 && distanceToPlayer > 20 && aggro == true) {
			if (time % 30 + random.nextInt(15) == 0) {
				if (random.nextInt(6) == 0) {
					pursue = false;
					xa += random.nextInt(3) - 1;
					ya += random.nextInt(3) - 1;
				} else {
					pursue = true;
				}
			}
			if (pursue) {
				xa = 0;
				ya = 0;
				if (px <= x) xa -= speed;
				if (px >= x) xa += speed;
				if (py <= y) ya -= speed;
				if (py >= y) ya += speed;
			}
		}

		if (Vector2i.getDistance(new Vector2i((int) px, (int) py), new Vector2i((int) x, (int) y)) < 40) {
			if (time % 90 == 0 && damageDelay == 0) {
				weapon.attack(x, y, direction);
				moveDelay = 20;
			}
		}
		if (beingKnockedBack) {
			pursue = true;
			move(knockbackX, knockbackY);
			knockbackX -= knockbackX * 0.08;
			knockbackY -= knockbackY * 0.08;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;
		updateAnimSprites();
		checkIfDead();

	}

}
