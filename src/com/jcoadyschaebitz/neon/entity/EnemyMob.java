package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.mob.DeadMob;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;

public class EnemyMob extends Mob implements DropsItems {
	
	public EnemyMob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		int[] xCollisionValues = { 0, 24, 0, 24 };
		int[] yCollisionValues = { 0, 0, 24, 24 };
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 16;
		health = maxHealth;
	}
	
	public void hitReceived(Projectile projectile) {
		aggro = true;
		if (health > 0) {
			health -= projectile.getDamage();
			if (health <= 0) {
				knockback(projectile.angle, 5 * knockbackMultiplier);
				damageDelay = rightDying.getTotalLength();
				level.addTrauma(0.5);
				state = MobState.DYING;
			} else {
				if (projectile instanceof Bolt) {
					((Bolt) projectile).impaled(this);
				}
				knockback(projectile.angle, 2 * knockbackMultiplier);
				damageDelay = rightDamage.getTotalLength();
			}
			xa = 0;
			ya = 0;
			rightDamage.playOnce();
			leftDamage.playOnce();
			rightDying.playOnce();
			leftDying.playOnce();
		}
	}
	
	public void bulletIncoming(double x, double y) {
	}
	
	protected void checkIfDead() {
		if (damageDelay == 0 && health <= 0) {
			level.add(new DeadMob(this, (int) x, (int) y));
			for (int i = 0; i < xpAmount; i++)
				level.add(new XPCapsule((int) x, (int) y + 8));
			checkForDrops();
			level.getPlayer().getSkillTreeManager().enemyKilled();
			remove();
		}
	}

	
	public void checkForDrops() {
		double healthPercent = level.getPlayer().health / level.getPlayer().maxHealth;
		double healthDropChance = 0;
		if (healthPercent <= 0.6) healthDropChance = 0.1;
		if (healthPercent <= 0.4) healthDropChance = 0.2;
		if (healthPercent <= 0.2) healthDropChance = 0.3;
		if (random.nextDouble() < healthDropChance) level.add(new HealthKit((int) x, (int) y));
		for (int i = 0; i < level.getPlayer().slots.size(); i++) {
			double percent;
			if (level.getPlayer().slots.get(i).weapon != null) {
				percent = level.getPlayer().slots.get(i).weapon.checkAmmoPercent();
			} else return;
			if (percent >= 0.4 && percent < 0.6) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.05, x, y);
			if (percent >= 0.2 && percent < 0.4) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.1, x, y);
			if (percent < 0.2) level.getPlayer().slots.get(i).weapon.checkAmmoDrop(0.2, x, y);
		}
	}



}
