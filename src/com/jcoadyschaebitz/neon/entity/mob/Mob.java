package com.jcoadyschaebitz.neon.entity.mob;

import java.util.ArrayList;
import java.util.List;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.entity.weapon.Weapon;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.StairTile;

public abstract class Mob extends Entity {

	protected double xa = 0, ya = 0, speed = 1, knockbackMultiplier = 1;
	public Direction dir;
	protected int xpAmount = 0, recoilMeter = 0, damageDelay = 0;
	protected double recoilX, recoilY, knockbackX, knockbackY;
	public double maxHealth;
	protected Sprite deadSpriteRight = Sprite.blueGrass, deadSpriteLeft = Sprite.blueGrass;
	protected boolean beingKnockedBack, aggro, walking = false;
	public Weapon weapon;
	
	protected AnimatedSprite leftWalking, rightWalking, leftIdle, rightIdle;
	protected AnimatedSprite leftDamage, rightDamage, leftDying, rightDying, currentAnim;
	
	protected MobState state = MobState.STILL;
	protected enum MobState {
		STILL, WALKING, DASHING, SLASHING, SHOOTING, DYING
	}
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}
	
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(0, ya);
			move(xa, 0);
			return;
		}
		double xx = xa;
		double yy = ya;
		if (level.getTile((int) (x + 8) >> 4, (int) (y + 20) >> 4) instanceof StairTile) {
			xx = StairTile.changeXa(level, xa, ya, (int) (x + 8) >> 4, (int) (y + 20) >> 4);
			yy = StairTile.changeYa(level, xa, ya, (int) (x + 8) >> 4, (int) (y + 20) >> 4);
		}

		while (xx != 0) {
			if (Math.abs(xx) > 1) {
				if (!collision(abs(xx), yy) && !staticEntityCollision(abs(xx), yy)) {
					this.x += abs(xx);
				}
				xx -= abs(xx);
			} else {
				if (!collision(abs(xx), yy) && !staticEntityCollision(abs(xx), yy)) {
					this.x += xx;
				}
				xx = 0;
			}
		}

		while (yy != 0) {
			if (Math.abs(yy) > 1) {
				if (!collision(xx, abs(yy)) && !staticEntityCollision(xx, abs(yy))) {
					this.y += abs(yy);
				}
				yy -= abs(yy);
			} else {
				if (!collision(xx, abs(yy)) && !staticEntityCollision(xx, abs(yy))) {
					this.y += yy;
				}
				yy = 0;
			}
		}
	}
	
	public void goTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void addWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	
	public void knockback(double angle, double strength) {
		knockbackX = Math.cos(angle) * strength;
		knockbackY = Math.sin(angle) * strength;
		beingKnockedBack = true;
	}

	public void update() {
		updateAnimSprites();
		if (weapon != null) weapon.update();
	}

	protected void updateAnimSprites() {
		leftWalking.update();
		rightWalking.update();
		leftIdle.update();
		rightIdle.update();
		leftDamage.update();
		rightDamage.update();
		leftDying.update();
		rightDying.update();
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

	protected boolean collision(double xa, double ya) {
		for (int i = 0; i < corners.getXValues().length; i++) {
			double yy = (y + ya + corners.getYValues()[i]) / 16;
			double xx = (x + xa + corners.getXValues()[i]) / 16;
			if (level.getTile(xx, yy).isSolid()) return true;
		}
		return false;
	}
	
	protected boolean staticEntityCollision(double xa, double ya) {
		List<Entity> near = level.getCollisionEntitiesInRad(this, 100);
		if (near.size() == 0) return false;
		List<Entity> newNear = new ArrayList<Entity>();
		for (Entity e : near) {
			if (e instanceof CollisionEntity) newNear.add(e);
		}
		int x0 = (int) (x + xa + corners.getXValues()[0]);
		int y0 = (int) (y + ya + corners.getYValues()[0]);
		int x1 = (int) (x + xa + corners.getXValues()[1]);
		int y1 = (int) (y + ya + corners.getYValues()[2]);
		for (Entity e : newNear) {
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
		if (health > 0) health -= projectile.getDamage();
	}

	public Sprite getDeadSprite() {
		if (dir == Direction.RIGHT) return deadSpriteRight;
		else return deadSpriteLeft;
	}

}
