package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.LaserBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.FastSword;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class FastMelee extends SwordEnemy {

	private double directionP, distanceP;
	private int minDist, maxDist, dodgeDelay, tripleCounter, knockbackImmunity, attackCounter;
	private boolean tripleDashing;

	public FastMelee(int x, int y) {
		super(x, y);
		minDist = 100;
		maxDist = 160;
		leftWalking = new AnimatedSprite(Spritesheet.swordEnemy_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.swordEnemy_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.fastMeleeRightIdle, 24, 24, 4, 10);
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
		maxHealth = 48;
		health = maxHealth;
		speed = 2;
		xpAmount = 7;
	}

	public void update() {
		if (weapon == null) level.add(new FastSword(this));
		else weapon.update();
		time++;
		updateAnimSprites();
		checkIfDead();
		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
		if (damageDelay > 0) damageDelay--;
		if (moveDelay > 0) moveDelay--;
		if (dodgeDelay > 0) dodgeDelay--;
		if (tripleCounter > 0) tripleCounter--;
		if (knockbackImmunity > 0) knockbackImmunity--;
		if (attackCounter > 1) attackCounter = 0;
		double px = level.getPlayer().getX();
		double py = level.getPlayer().getY();
		double dx = px - x;
		double dy = py - y;
		directionP = Math.atan2(dy, dx);
		distanceP = Vector2i.getDistance(new Vector2i((int) px, (int) py), new Vector2i((int) x, (int) y));
		if (playerSightline() && distanceP < 400) aggro = true;
		else aggro = false;
		if (state == MobState.DYING) return;
		if (px > x) dir = Direction.RIGHT;
		if (px < x) dir = Direction.LEFT;

		updateTimedMoves(directionP);
		if (!aggro) return;
		
		if (moveDelay == 0 && !tripleDashing) {
			moveDelay = 60;
			attackCounter++;
			if (attackCounter == 2) {
				switch (random.nextInt(2)) {
				case 0: 
					if (distanceP >= minDist && distanceP <= maxDist) {
						if (playerSightline()) {
							tripleDash();
						} else sideDash();
					} else if (distanceP > maxDist) dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
					else if (distanceP < minDist) dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
					break;
				case 1: 
					if (playerSightline()) {
						if (distanceP > minDist) rangedAttack(directionP);
						else dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
					} else {
						if (random.nextInt(2) == 0) sideDash();
						else randomMove();
					}
					break;
					//add close range melee atack
				}
			} else {
				switch (random.nextInt(3)) {
				case 0:
					if (distanceP >= minDist && distanceP <= maxDist) sideDash();
					if (distanceP < minDist) dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
					if (distanceP > maxDist) dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
					break;
				case 1:
					randomMove();
					break;
				case 2:
					randomMove();
					break;
				}
			}
		}
	}

	private boolean playerSightline() {
		return level.isSightLine((int) (x + sprite.getWidth() / 2), (int) (y + sprite.getHeight() / 2), directionP, level.getPlayer());
	}

	public void tripleDash() {
		tripleDashing = true;
		tripleCounter = 90;
	}

	public void bulletIncoming(double x, double y) {
		if (state != MobState.DASHING && !tripleDashing && dodgeDelay == 0) {
			dodgeDelay = 60;
			if (random.nextInt(2) != 1) {
				sideDash();
				moveDelay = 60;
			}
		}
	}

	public void updateTimedMoves(double angle) {
		if (moveDelay <= 50 && state == MobState.DASHING && !tripleDashing) {
			state = MobState.STILL;
			xa = 0;
			ya = 0;
		}
		if (moveDelay <= 20 && state == MobState.WALKING) {
			xa = 0;
			ya = 0;
			state = MobState.STILL;
		}
		if (tripleDashing) {
			switch (tripleCounter) {
			case 80:
				xa = 0;
				ya = 0;
				dash(Math.cos(directionP + 0.5) * 0.9, Math.sin(directionP + 0.5) * 0.9);
				System.out.println("dash 1");
				break;
			case 70:
				xa = 0;
				ya = 0;
				weapon.attack(x, y, directionP + 0.5);
				break;
			case 65:
				dash(Math.cos(directionP - 0.5) * 0.9, Math.sin(directionP - 0.5) * 0.9);
				System.out.println("dash 2");
				break;
			case 55:
				xa = 0;
				ya = 0;
				weapon.attack(x, y, directionP - 0.5);
				break;
			case 50:
				dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
				System.out.println("dash 3");
				break;
			case 40:
				xa = 0;
				ya = 0;
				weapon.attack(x, y, directionP);
				break;
			case 30:
				dash(Math.cos(directionP + Math.PI) * 1.6, Math.sin(directionP + Math.PI) * 1.6);
				break;
			case 20:
				xa = 0;
				ya = 0;
				break;
			case 10:
				tripleDashing = false;
				System.out.println("tripleDashing now off");
				break;
			default:
				break;
			}
		}
		if (state == MobState.SHOOTING && moveDelay <= 50) {
			level.add(new LaserBullet(this, this.x, this.y, angle - 0.1));
			level.add(new LaserBullet(this, this.x, this.y, angle + 0.1));
			state = MobState.STILL;
		}
		if (beingKnockedBack) {
			move(knockbackX, knockbackY);
			knockbackX -= knockbackX * 0.08;
			knockbackY -= knockbackY * 0.08;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;

	}

	public void dash(double x, double y) {
		double xt = (this.x + (x * 50));
		double yt = (this.y + (y * 50));
		if (!level.getTile(((int) xt) >> 4, ((int) yt) >> 4).isSolid()) {
			xa = x * 5;
			ya = y * 5;
			state = MobState.DASHING;
		} else randomMove();
	}

	public void sideDash() {
		double dd = ((random.nextDouble() / 2) - 0.5);
		if (random.nextInt(2) == 0) {
			dd += directionP + (Math.PI / 2);
		} else {
			dd += directionP - (Math.PI / 2);
		}
		dash(Math.cos(dd), Math.sin(dd));
	}

	public void rangedAttack(double angle) {
		level.add(new LaserBullet(this, x + sprite.getWidth() / 2, y + sprite.getHeight() / 2, angle - 0.1));
		level.add(new LaserBullet(this, x + sprite.getWidth() / 2, y + sprite.getHeight() / 2, angle + 0.1));
		state = MobState.SHOOTING;
	}

	public void randomMove() {
		state = MobState.WALKING;
		double dd = ((random.nextDouble() / 2) - 0.5);
		if (distanceP >= minDist && distanceP <= maxDist) {
			if (random.nextInt(2) == 0) {
				dd += directionP + (Math.PI / 2);
			} else {
				dd += directionP - (Math.PI / 2);
			}
		} else if (distanceP < minDist) {
			dd += directionP + Math.PI;
		} else {
			dd += directionP;
		}
		xa = Math.cos(dd) * speed;
		ya = Math.sin(dd) * speed;
	}

	public void hitReceived(Projectile projectile) {
		if (health > 0) {
			health -= projectile.getDamage();
			if (health <= 0) {
				knockback(projectile.angle, 15 * knockbackMultiplier);
				damageDelay = rightDying.getTotalLength();
				level.addTrauma(0.5);
				state = MobState.DYING;
			} else {
				if (projectile instanceof Bolt) {
					((Bolt) projectile).impaled(this);
				}
				if (knockbackImmunity == 0 && !tripleDashing) knockback(projectile.angle, 2 * knockbackMultiplier);
				if (projectile.getDamage() / maxHealth > 0.2) {
					tripleDashing = false;
					tripleCounter = 0;
				}
				damageDelay = 4;
			}
			xa = 0;
			ya = 0;
			rightDamage.playOnce();
			leftDamage.playOnce();
			rightDying.playOnce();
			leftDying.playOnce();
			if (random.nextInt(3) == 0) {
				moveDelay = 0;
				knockbackImmunity = 20;
			}
		}
	}

	public void render(Screen screen) {
		if (damageDelay == 0) {
			if (dir == Direction.RIGHT) {
				if (walking) currentAnim = rightWalking;
				else currentAnim = rightIdle;
			}
			if (dir == Direction.LEFT) {
				if (walking) currentAnim = leftWalking;
				else currentAnim = leftIdle;
			}
		} else {
			if (health > 0) {
				if (dir == Direction.RIGHT) currentAnim = rightDamage;
				if (dir == Direction.LEFT) currentAnim = leftDamage;
			} else {
				if (dir == Direction.RIGHT) currentAnim = rightDying;
				if (dir == Direction.LEFT) currentAnim = leftDying;
			}
		}
		sprite = currentAnim.getSprite();

		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		screen.renderSprite((int) x, (int) y, getSprite(), true);
		if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 4) % 2);

	}

}
