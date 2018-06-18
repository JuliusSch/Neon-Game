package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.EnemyMob;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class ShootingEnemy extends EnemyMob {

	protected int viewRange = 200;
	protected double directionToPlayer;

	public ShootingEnemy(int x, int y) {
		super(x, y);
	}

	public void update() {

		if (level.getPlayer().getX() > x) dir = Direction.RIGHT;
		if (level.getPlayer().getX() < x) dir = Direction.LEFT;

		time++;
		if (damageDelay > 0) damageDelay--;

		updateAnimSprites();

		double px = level.getPlayer().getX();
		double py = level.getPlayer().getY();
		double dx = px - x;
		double dy = py - y;
		double direction = Math.atan2(dy, dx);
		directionToPlayer = direction;
		double distanceToPlayer = Vector2i.getDistance(new Vector2i((int) px, (int) py), new Vector2i((int) x, (int) y));

		if (weapon != null) {
			weapon.updateSprite(direction);
			weapon.update();
		}

		if (damageDelay <= 0 && distanceToPlayer < viewRange) {
			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
			}
		}

		if (aggro) attemptShoot(direction, distanceToPlayer);

		if (distanceToPlayer < viewRange) aggro = true;
		doSpecificUpdates();
		checkIfDead();
	}

	protected void doSpecificUpdates() {
	}

	protected void attemptShoot(double direction, double distance) {
		if (time % 120 == 0 && random.nextInt(2) == 0) {
			if (distance < viewRange) {
				if (weapon != null && health > 0 && damageDelay <= 0) {
					weapon.attack(x, y, direction);
				}
			}
		}
	}

}
