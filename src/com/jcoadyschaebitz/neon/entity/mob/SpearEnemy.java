package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AttackBehaviour;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckAggro;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.CheckDistanceToPlayer;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.InverterNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SequencerNode;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.LaserBullet;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.EnemySpear;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class SpearEnemy extends MeleeEnemy {

	private int dodgeDelay, tripleCounter, knockbackImmunity, attackCounter;
	private boolean tripleDashing;
	private AnimatedSprite leftWindUp, rightWindUp;

	public SpearEnemy(int x, int y) {
		super(x, y);
		leftWalking = new AnimatedSprite(Spritesheet.fastMeleeLeftWalking, 64, 64, 4, 3);
		rightWalking = new AnimatedSprite(Spritesheet.fastMeleeRightWalking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.fastMeleeLeftIdle, 64, 64, 4, 12);
		rightIdle = new AnimatedSprite(Spritesheet.fastMeleeRightIdle, 64, 64, 4, 12);
		leftDamage = new AnimatedSprite(Spritesheet.fastMeleeDamageLeft, 64, 64, 4, 3);
		rightDamage = new AnimatedSprite(Spritesheet.fastMeleeDamageRight, 64, 64, 4, 3);
		leftDying = new AnimatedSprite(Spritesheet.swordEnemyLeftDying, 24, 24, 10, 3);
		rightDying = new AnimatedSprite(Spritesheet.swordEnemyRightDying, 24, 24, 10, 3);
		leftWindUp = new AnimatedSprite(Spritesheet.fastMeleeWindUpLeft, 64, 64, 10, 4);
		rightWindUp = new AnimatedSprite(Spritesheet.fastMeleeWindUpLeft, 64, 64, 10, 4);
		currentAnim = leftWalking;
		deadSpriteRight = Sprite.swordGuy_dead_right;
		deadSpriteLeft = Sprite.swordGuy_dead_left;
		sprite = rightWalking.getSprite();
		int[] xCollisionValues = { 23, 41, 23, 41, 23, 41, 23, 41 };
		int[] yCollisionValues = { 17, 17, 47, 47, 27, 27, 37, 37 };
		int[] xTileCollisionValues = { 23, 41, 23, 41 };
		int[] yTileCollisionValues = { 36, 36, 47, 47 };
		corners = new CollisionBox(xTileCollisionValues, yTileCollisionValues);
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 72;
		health = maxHealth;
		speed = 2;
		xpAmount = 7;
	}

	public void update() {
		if (weapon == null) level.add(new EnemySpear(this));		//add spear weapon with requisite attacks
		else weapon.update();
		time++;
		updateAnimSprites();
		checkIfDead();
		if (xa != 0 || ya != 0) {
			move(xa, ya, true);
		}
		if (damageDelay > 0) damageDelay--;
		if (dodgeDelay > 0) dodgeDelay--;
		if (tripleCounter > 0) tripleCounter--;
		if (knockbackImmunity > 0) knockbackImmunity--;
		if (attackCounter > 1) attackCounter = 0;
		double px = level.getPlayer().getX() + level.getPlayer().getSpriteW() / 2;
		double py = level.getPlayer().getY() + level.getPlayer().getSpriteH() / 2;
		double dx = px - (x + getSpriteW() / 2);
		double dy = py - (y + getSpriteH() / 2);
		directionP = Math.atan2(dy, dx);
		distanceP = Vec2i.getDistance(new Vec2i((int) px, (int) py), new Vec2i((int) x, (int) y));
		if (playerSightline() && distanceP < 400) aggro = true;
		if (state == MobState.DYING) return;
		if (px + level.getPlayer().getSpriteW() / 2 > x + sprite.getWidth() / 2) dir = Orientation.RIGHT;
		if (px + level.getPlayer().getSpriteW() / 2 < x + sprite.getWidth() / 2) dir = Orientation.LEFT;

//		updateTimedMoves(directionP);
		if (!aggro) return;
		
//		if (moveDelay == 0 && !tripleDashing) {
//			moveDelay = random.nextInt(10) + 60;
//			attackCounter++;
//			if (distanceP > 200) {
//				dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
//				return;
//			}
//			if (attackCounter == 2) {
//				switch (random.nextInt(2)) {
//				case 0: 
//					if (distanceP >= minDist && distanceP <= maxDist) {
//						if (playerSightline()) {
//							tripleDash();
//						} else sideDash();
//					} else if (distanceP > maxDist) dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
//					else if (distanceP < minDist) dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
//					break;
//				case 1: 
//					if (playerSightline()) {
//						if (distanceP > (minDist + maxDist) / 2) rangedAttack(directionP);
//						else dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
//					} else {
//						if (random.nextInt(2) == 0) sideDash();
//						else randomMove();
//					}
//					break;
//					//add close range melee atack
//				}
//			} else {
//				switch (random.nextInt(3)) {
//				case 0:
//					if (distanceP >= minDist - 20 && distanceP <= maxDist + 20) sideDash();
//					if (distanceP < minDist - 20) dash(Math.cos(directionP + Math.PI), Math.sin(directionP + Math.PI));
//					if (distanceP > maxDist + 20) dash(Math.cos(directionP) * 1.2, Math.sin(directionP) * 1.2);
//					break;
//				case 1:
//					randomMove();
//					break;
//				case 2:
//					randomMove();
//					break;
//				}
//			}
//		}
	}
	
	@Override
	protected void constructBehaviourTree(AIBlackboard bb) {
		behaviours.addNode(new InverterNode(new CheckAggro(bb, this)));
		SelectorNode aggroBehaviours = new SelectorNode(bb, this);
		SequencerNode simpleMeleeAttack = new SequencerNode(bb, this);
		simpleMeleeAttack.addNode(new CheckDistanceToPlayer(bb, this, 60));
		simpleMeleeAttack.addNode(new AttackBehaviour(bb, this));
		aggroBehaviours.addNode(simpleMeleeAttack);
		behaviours.addNode(aggroBehaviours);
	}
	
	public void updateAnimSprites() {
		super.updateAnimSprites();
		leftWindUp.update();
		rightWindUp.update();
	}
	
	public void meleeHitSuccessful() {
		tripleDashing = false;
		tripleCounter = 10;
	}

	public void tripleDash() {
		tripleDashing = true;
		tripleCounter = 110;
		leftWindUp.playOnce();
		rightWindUp.playOnce();
	}

	public void bulletIncoming(double x, double y) {
		if (state != MobState.DASHING && !tripleDashing && dodgeDelay == 0) {
			dodgeDelay = 60;
			if (random.nextInt(2) != 1) {
				sideDash();
			}
		}
	}

//	public void updateTimedMoves(double angle) {
//		if (moveDelay <= 50 && state == MobState.DASHING && !tripleDashing) {
//			state = MobState.STILL;
//			xa = 0;
//			ya = 0;
//		}
//		if (moveDelay <= 20 && state == MobState.WALKING) {
//			xa = 0;
//			ya = 0;
//			state = MobState.STILL;
//		}
//		if (tripleDashing) {
//			switch (tripleCounter) {
//			case 80:
//				meleeAttackDir = directionP;
//				xa = 0;
//				ya = 0;
//				dash(Math.cos(meleeAttackDir + 0.5) * 0.9, Math.sin(meleeAttackDir + 0.5) * 0.9);
//				System.out.println("dash 1");
//				break;
//			case 70:
//				xa = 0;
//				ya = 0;
//				weapon.attack(x, y, meleeAttackDir + 0.4);
//				meleeAttackDir = directionP;
//				break;
//			case 65:
//				dash(Math.cos(meleeAttackDir - 0.5) * 0.9, Math.sin(meleeAttackDir - 0.5) * 0.9);
//				System.out.println("dash 2");
//				break;
//			case 55:
//				xa = 0;
//				ya = 0;
//				weapon.attack(x, y, meleeAttackDir - 0.4);
//				meleeAttackDir = directionP;
//				break;
//			case 50:
//				dash(Math.cos(meleeAttackDir) * 1.2, Math.sin(meleeAttackDir) * 1.2);
//				System.out.println("dash 3");
//				break;
//			case 40:
//				xa = 0;
//				ya = 0;
//				weapon.attack(x, y, meleeAttackDir);
//				meleeAttackDir = directionP;
//				break;
//			case 30:
//				dash(Math.cos(meleeAttackDir + Math.PI) * 1.6, Math.sin(meleeAttackDir + Math.PI) * 1.6);
//				break;
//			case 20:
//				xa = 0;
//				ya = 0;
//				break;
//			case 10:
//				tripleDashing = false;
//				System.out.println("tripleDashing now off");
//				break;
//			default:
//				break;
//			}
//		}
//		if (state == MobState.SHOOTING && moveDelay <= 50) {
//			rangedAttack(directionP);
//			state = MobState.STILL;
//		}
//		if (beingKnockedBack) {
//			move(knockbackX, knockbackY, true);
//			knockbackX -= knockbackX * 0.08;
//			knockbackY -= knockbackY * 0.08;
//		}
//		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;
//
//	}

	public void dash(double x, double y) {
//		double xt = (this.x + (x * 50));
//		double yt = (this.y + (y * 50));
//		if (!level.getTile(((int) xt) >> 4, ((int) yt) >> 4).blockAIMovement) {
//			xa = x * 5;
//			ya = y * 5;
//			state = MobState.DASHING;
//		} else randomMove();
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
		level.add(new LaserBullet(this, x + 16, y + 8, angle - 0.1));
		level.add(new LaserBullet(this, x + 16, y + 8, angle + 0.1));
	}

	public void randomMove() {
//		state = MobState.WALKING;
//		double dd = ((random.nextDouble() / 2) - 0.5);
//		if (distanceP >= minDist && distanceP <= maxDist) {
//			if (random.nextInt(2) == 0) {
//				dd += directionP + (Math.PI / 2);
//			} else {
//				dd += directionP - (Math.PI / 2);
//			}
//		} else if (distanceP < minDist) {
//			dd += directionP + Math.PI;
//		} else {
//			dd += directionP;
//		}
//		xa = Math.cos(dd) * speed;
//		ya = Math.sin(dd) * speed;
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
//				moveDelay = 0;
				knockbackImmunity = 20;
			}
		}
	}

	public void render(Screen screen) {
		if (damageDelay == 0) {
			if (dir == Orientation.RIGHT) {
				if (walking) currentAnim = rightWalking;
				else currentAnim = rightIdle;
				if (tripleCounter <= 110 && tripleCounter > 60) currentAnim = leftWindUp;
			}
			if (dir == Orientation.LEFT) {
				if (walking) currentAnim = leftWalking;
				else currentAnim = leftIdle;
				if (tripleCounter <= 110 && tripleCounter > 60) currentAnim = leftWindUp;
			}
		} else {
			if (health > 0) {
				if (dir == Orientation.RIGHT) currentAnim = rightDamage;
				if (dir == Orientation.LEFT) currentAnim = leftDamage;
			} else {
				if (dir == Orientation.RIGHT) currentAnim = rightDying;
				if (dir == Orientation.LEFT) currentAnim = leftDying;
			}
		}
		sprite = currentAnim.getSprite();

		screen.renderTranslucentSprite((int) x + 20, (int) y + 28, Sprite.shadowX32, true, 0.6);
		screen.renderSprite((int) x, (int) y, getSprite(), true);
		if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 4) % 2);
		entityBounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y); 
	}

}
