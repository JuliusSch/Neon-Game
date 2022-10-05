package com.jcoadyschaebitz.neon.entity.mob;

import java.util.List;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.DropsItems;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.XPCapsule;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.AIBlackboard;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.BehaviourNode.NodeState;
import com.jcoadyschaebitz.neon.entity.mob.enemyAI.SelectorNode;
import com.jcoadyschaebitz.neon.entity.projectile.Bolt;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.projectile.Ray;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Font;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.DiagonalTile;
import com.jcoadyschaebitz.neon.level.tile.DiagonalTile.DiagDirection;
import com.jcoadyschaebitz.neon.level.tile.StairTile;
import com.jcoadyschaebitz.neon.level.tile.Tile;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public abstract class Mob extends Entity implements DropsItems {

	protected double xa = 0, ya = 0, knockbackMultiplier = 1;
	public double speed = 1;
	public Orientation dir;
	protected int xpAmount = 0, recoilMeter = 0;
	public int damageDelay = 0;
	protected double knockbackX, knockbackY;
	public double maxHealth;
	protected Sprite deadSpriteRight = Sprite.blueGrass, deadSpriteLeft = Sprite.blueGrass, textBubbleSprite = Sprite.oldManEye;
	protected boolean beingKnockedBack;
	public boolean walking = false;
	public Weapon weapon;
	protected double knockbackResistance = 0.8;
	protected double directionP, distanceP;
	public boolean aggro, immobilised = false;
	protected SelectorNode behaviours;
	protected AIBlackboard blackboard;
	protected double poise, maxPoise;

	protected AnimatedSprite leftWalking, rightWalking, leftIdle, rightIdle;
	protected AnimatedSprite leftDamage, rightDamage, leftDying, rightDying, currentAnim;

	public enum MobState { IDLE, WALKING, DASHING, SLASHING, DYING, TAKINGDAMAGE, ATTACKING, ANIMATING, UNAWARE, LOST_PLAYER }
	public enum Orientation { LEFT, RIGHT, UP, DOWN }

	protected MobState state = MobState.UNAWARE;
	
	public Mob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		int[] xCollisionValues = { 0, 24, 0, 24 }; // ABSTRACT method called 'initCollisionValues' that returns
		int[] yCollisionValues = { 0, 0, 24, 24 }; // values, defined in subclasses.
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		maxHealth = 16;
		health = maxHealth;
		maxPoise = 5;

		blackboard = new AIBlackboard();
		behaviours = new SelectorNode(blackboard, this);
		constructBehaviourTree(blackboard);
	}

	protected abstract void constructBehaviourTree(AIBlackboard bb);

	public void setInSceneStatus(boolean status) {
		immobilised = status;
	}

	public void update() {
		time++;
		if (!blackboard.beenInitted) blackboard.init(level);
		updateAnimSprites();
		if (!immobilised) {
			if (behaviours.getState() == NodeState.SUCCESS || behaviours.getState() == NodeState.FAILURE) behaviours.softReset();
			if (aggro) behaviours.update();
		}
		if (poise >= maxPoise) {
			behaviours.hardReset();
			poise = 0;
		}
		if (poise > 0) poise -= 0.05;
		// How to separate behaviour and animation, should both be handled by tree?
		if (damageDelay > 0) damageDelay--;
		if (beingKnockedBack) {
			move(knockbackX, knockbackY, true);
			knockbackX -= knockbackX * 0.1;
			knockbackY -= knockbackY * 0.1;
		}
		if (knockbackX < 0.1 && knockbackX > -0.1 && knockbackY < 0.1 && knockbackY > -0.1) beingKnockedBack = false;

		updatePDistance();
		updatePDirection();
		checkIfDead();
	}
	
	public Sprite getTextBubbleSprite() {
		return textBubbleSprite;
	}

	private void updatePDistance() {
		double px = level.getPlayer().getX();
		double py = level.getPlayer().getY();
		distanceP = Vec2i.getDistance(new Vec2i((int) px, (int) py), new Vec2i((int) x, (int) y));
	}

	private void updatePDirection() {
		double px = level.getPlayer().getX();
		double py = level.getPlayer().getY();
		double dx = px - x;
		double dy = py - y;
		directionP = Math.atan2(dy, dx);
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
			if (level.getPlayer().slots.get(i).getWeapon() != null) {
				percent = level.getPlayer().slots.get(i).getWeapon().checkAmmoPercent();
			} else return;
			if (percent >= 0.4) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.05, x, y);
			if (percent >= 0.2 && percent < 0.4) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.1, x, y);
			if (percent < 0.2) level.getPlayer().slots.get(i).getWeapon().checkAmmoDrop(0.25, x, y);
		}
	}
	
	public void goTo(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	protected boolean playerSightline() {
		double px = level.getPlayer().getX() + level.getPlayer().getSpriteW() / 2;
		double py = level.getPlayer().getY() + level.getPlayer().getSpriteH() / 2;
		double dx = px - (x + getSpriteW() / 2);
		double dy = py - (y + getSpriteH() / 2);
		double directionP = Math.atan2(dy, dx);
		return level.isSightline((int) (x + sprite.getWidth() / 2), (int) (y + sprite.getHeight() / 2), directionP, level.getPlayer());
	}

	public void setState(MobState state) {
		this.state = state;
	}

	public void setOrientation(Orientation dir) {
		if (dir == Orientation.LEFT || dir == Orientation.RIGHT) this.dir = dir;
	}

	public void setAnimation(AnimatedSprite anim) {
		currentAnim = anim;
	}

	public boolean move(double xa, double ya, boolean stairChange) {
		Vec2d newVs = checkDiagCollision(xa, ya);
		double xx = newVs.x;
		double yy = newVs.y;
		Tile standingTile = level.getTile((int) (x + spriteWidth / 2) >> 4, (int) (y + spriteHeight) >> 4);
		if (stairChange && standingTile.isStair() != 0) {
			xx = StairTile.changeXa(level, xa, ya, (int) (x + 8), (int) (y + 20), standingTile.isStair());
			yy = StairTile.changeYa(level, xa, ya, (int) (x + 8), (int) (y + 20), standingTile.isStair());
		}
		if (xx != 0 && yy != 0) {
			boolean moveX = move(xx, 0, false);
			boolean moveY = move(0, yy, false);
			return moveX && moveY;
		}
		boolean noCollision = true;

		while (xx != 0) {
			if (Math.abs(xx) > 1) {
				if (!collision(abs(xx), yy) && !staticEntityCollision(abs(xx), yy)) this.x += abs(xx);
				else noCollision = false;
				xx -= abs(xx);
			} else {
				if (!collision(abs(xx), yy) && !staticEntityCollision(abs(xx), yy)) this.x += xx;
				else noCollision = false;
				xx = 0;
			}
		}

		while (yy != 0) {
			if (Math.abs(yy) > 1) {
				if (!collision(xx, abs(yy)) && !staticEntityCollision(xx, abs(yy))) this.y += abs(yy);
				else noCollision = false;
				yy -= abs(yy);
			} else {
				if (!collision(xx, abs(yy)) && !staticEntityCollision(xx, abs(yy))) this.y += yy;
				else noCollision = false;
				yy = 0;
			}
		}
		return noCollision;
	}
	
	protected boolean collision(double xa, double ya) {
		for (int i = 0; i < corners.getXValues().length; i++) {
			double yy = (int) (y + ya + corners.getYValues()[i]) >> 4;
			double xx = (int) (x + xa + corners.getXValues()[i]) >> 4;
			if (!level.collides((int) xx, (int) yy)) return true;
		}
		return false;
	}

	public void updateCutscene() {
		time++;
		updateAnimSprites();
		if (xa != 0 || ya != 0) {
			move(xa, ya, true);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void addWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void stopMoving() {
		xa = 0; // marked for removal and cleanup, handle cut scene logic differently
		ya = 0;
		state = MobState.IDLE;
	}

	public void meleeHitSuccessful() { // requires further study
	}

	public void knockback(double angle, double strength) {
		knockbackX = Math.cos(angle) * strength; // incorporate this into a generic addVelocity() method
		knockbackY = Math.sin(angle) * strength;
		beingKnockedBack = true;
	}

	public void updateAnimSprites() {
		if (dir == Orientation.LEFT) {
			switch (state) {
			case IDLE:
				currentAnim = leftIdle;
				break;
			case WALKING:
				currentAnim = leftWalking;
				break;
			case TAKINGDAMAGE:
				currentAnim = leftDamage;
				break;
			case DYING:
				currentAnim = leftDying;
				break;
			case ATTACKING:
				currentAnim = leftIdle;
				break;
			default:
				currentAnim = leftIdle;
				break;
			}
		} else {
			switch (state) {
			case IDLE:
				currentAnim = rightIdle;
				break;
			case WALKING:
				currentAnim = rightWalking;
				break;
			case TAKINGDAMAGE:
				currentAnim = rightDamage;
				break;
			case DYING:
				currentAnim = rightDying;
				break;
			case ATTACKING:
				currentAnim = rightIdle;
				break;
			default:
				currentAnim = rightIdle;
				break;
			}
		}
		leftWalking.update();
		rightWalking.update();
		leftIdle.update();
		rightIdle.update();
		leftDamage.update();
		rightDamage.update();
		leftDying.update();
		rightDying.update();
	}
	
	public void resetAnimSprites() {
		leftWalking.setFrame(0);
		rightWalking.setFrame(0);
		leftIdle.setFrame(0);
		rightIdle.setFrame(0);
		leftDamage.setFrame(0);
		leftDying.setFrame(0);
		rightDying.setFrame(0);		
	}

	public Vec2d checkDiagCollision(double xa, double ya) {
		boolean result = false;
		Vec2d newVs = new Vec2d(xa, ya);
		DiagDirection dir;
		for (int i = 0; i < corners.getXValues().length; i++) {
			double yy = (y + ya + corners.getYValues()[i]) / 16; // consider usage.
			double xx = (x + xa + corners.getXValues()[i]) / 16;
			double x2 = (y + ya + corners.getYValues()[i]) % 16;
			double y2 = (x + xa + corners.getXValues()[i]) % 16;
			if (level.getTile(xx, yy) instanceof DiagonalTile) {
				dir = ((DiagonalTile) level.getTile(xx, yy)).dir;
				result = DiagonalTile.checkDiagCollision(dir, x2, y2);
				if (result) {
					newVs = DiagonalTile.updateMove(dir, xa, ya);
				}
			}
		}
		return newVs;
	}

	protected boolean staticEntityCollision(double xa, double ya) {
		List<Entity> near = level.getCollisionEntitiesInRange(this, 100);
		if (near.size() == 0) return false;
//		List<Entity> newNear = new ArrayList<Entity>(); // consider incorporating
//		for (Entity e : near) {
//			if (e instanceof CollisionEntity) newNear.add(e);
//		}
		int x0 = (int) (x + xa + corners.getXValues()[0]);
		int y0 = (int) (y + ya + corners.getYValues()[0]);
		int x1 = (int) (x + xa + corners.getXValues()[1]);
		int y1 = (int) (y + ya + corners.getYValues()[2]);
		for (Entity e : near) {
			for (int i = 0; i < e.entityBounds.getXValues().length; i++) {
				int xx = e.entityBounds.getXValues()[i] + e.getIntX();
				int yy = e.entityBounds.getYValues()[i] + e.getIntY();
				if (xx > x0 && xx < x1) {
					if (yy > y0 && yy < y1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void hitReceived(Projectile projectile) {
		if (projectile instanceof Ray) {
			blackboard.setTargeted(true);
			return;
		}
		aggro = true;
		if (health > 0) {
			health -= projectile.getDamage();
			if (health <= 0) {
				knockback(projectile.angle, 5 * knockbackMultiplier * projectile.getKnockbackMultiplier());
				damageDelay = rightDying.getTotalLength();
				level.addTrauma(0.5);
				state = MobState.DYING;
				behaviours.hardReset();
			} else {
				if (projectile instanceof Bolt) ((Bolt) projectile).impaled(this);
				state = MobState.TAKINGDAMAGE;
				addPoiseDamage(projectile.getDamage());
				knockback(projectile.angle, 2 * knockbackMultiplier * projectile.getKnockbackMultiplier());
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

	public void addPoiseDamage(double amount) {
		poise += amount;
	}

	public Sprite getDeadSprite() {
		if (dir == Orientation.RIGHT) return deadSpriteRight;
		else return deadSpriteLeft;
	}

	Font font = new Font(Font.SIZE_5x5, 0xffFF004E, 0.5);
	
	public void render(Screen screen) {
		sprite = currentAnim.getSprite();
		screen.renderTranslucentSprite((int) x - 3, (int) y + 3, Sprite.shadow, true, 0.6);
		screen.renderSprite((int) x, (int) y, getSprite(), true);
		if (weapon != null) weapon.renderOnOwner(screen, (currentAnim.getFrame() / 4) % 2);
//		font.render((int) x, (int) y, state.toString(), screen, true);
	}
}
