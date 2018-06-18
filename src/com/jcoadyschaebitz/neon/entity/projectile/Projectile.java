package com.jcoadyschaebitz.neon.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.EnemyMob;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.util.Vector2i;

public class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	public double angle;
	protected Sprite sprite, glow;
	protected double nx, ny;
	protected double speed, range, damage;
	protected double distance;
	protected int life;
	protected Spritesheet sheet;
	protected Entity source;
	protected boolean isEnemyBullet;
	protected AnimatedSprite bulletAnim;

	public Projectile(Entity source, double x, double y, double angle) {
		xOrigin = x;
		yOrigin = y;
		this.angle = angle;
		this.x = x;
		this.y = y;
		sprite = Sprite.pistol_bullet;
		glow = Sprite.pistol_bullet;
		int[] xCollisionValues = { 4, 10, 4, 10 };
		int[] yCollisionValues = { 6, 6, 9, 9 };
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		this.source = source;
		damage = 4;
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public double getDamage() {
		return damage;
	}

	public void update() {
		time++;
		move(source, nx, ny);

		if (time > life) remove();
	}

	public boolean isEnemyBullet() {
		return isEnemyBullet;
	}

	protected Entity entityCollision(Entity source, int x, int y) {
		List<Entity> sources = new ArrayList<Entity>();
		sources.add(this);
		sources.add(source);
		List<Entity> near = level.getValidTargetsInRad(x, y, sources, 200);
		if (near.size() > 0) {
			for (Entity e : near) {
				if (isEnemyBullet() && (e instanceof EnemyMob)) continue;
				Vector2i aa = new Vector2i(x, y);
				Vector2i bb = new Vector2i(e.getIntX(), e.getIntY());
				if (Vector2i.getDistance(aa, bb) < 80) {
					try {
						((EnemyMob) e).bulletIncoming(x, y);
					} catch (ClassCastException c) {
					}
				}
				int mx0 = e.getProjectileBounds().getXValues()[0] + e.getIntX();
				int my0 = e.getProjectileBounds().getYValues()[0] + e.getIntY();
				int mx1 = e.getProjectileBounds().getXValues()[1] + e.getIntX();
				int my1 = e.getProjectileBounds().getYValues()[2] + e.getIntY();
				for (int i = 0; i < getProjectileBounds().getXValues().length; i++) {
					int px = getProjectileBounds().getXValues()[i] + x;
					int py = getProjectileBounds().getYValues()[i] + y;
					if (px >= mx0 && px <= mx1) {
						if (py >= my0 && py <= my1) {
							return e;
						}
					}
				}
			}
		}
		return null;
	}

	protected void move(Entity source, double nx, double ny) {
		double nx2 = nx;
		double ny2 = ny;
		double nx3, ny3;
		while (nx2 != 0 || ny2 != 0) {
			if (Math.abs(nx2) > 1) {
				if (Math.abs(ny2) > 1) {
					nx3 = abs(nx2);
					ny3 = abs(ny2);
				} else {
					nx3 = abs(nx2);
					ny3 = ny2;
				}
			} else if (Math.abs(ny2) > 1) {
				nx3 = nx2;
				ny3 = abs(ny2);
			} else {
				nx3 = nx2;
				ny3 = ny2;
			}
			boolean isHit = level.tileCollision((int) (x + nx3), (int) (y + ny3), entityBounds.getXValues(), entityBounds.getYValues()).isHit;
			Entity e = entityCollision(source, (int) (x + nx3), (int) (y + ny3));
			if (isHit) {
				collide((int) (x + nx3), (int) (y + ny3));
				break;
			} else if (e != null) {
				collideEntity((int) (x + nx3), (int) (y + ny3), e);
				e.hitReceived(this);
				if (e instanceof Mob) {
					if (e.getHealth() > 0) {
						break;
					}
				}
			}
			this.x += nx3;
			this.y += ny3;
			nx2 -= nx3;
			ny2 -= ny3;
		}

		if (distance() > range) remove();
	}

	public void collide(int x, int y) {
		remove();
	}

	public void collideEntity(int x, int y, Entity e) {
		remove();
	}

	public void shieldCollision(double chance, double dir, Projectile p) {
		if (random.nextDouble() <= chance) {
			double rand = random.nextDouble() - 0.5;
			angle = dir + rand;
			nx = Math.cos(angle) * speed;
			ny = Math.sin(angle) * speed;
			isEnemyBullet = false;
			source = level.getPlayer();
		} else collide((int) this.x, (int) this.y);
	}

	protected double distance() {
		double dist = 0;
		dist = Math.sqrt((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
		return dist;
	}

	public void render(Screen screen) {
		if (time > speed / 10) {
			screen.renderTranslucentSprite((int) x - 8, (int) y - 8, glow, true, 0.1);
			screen.renderSprite((int) x, (int) y, sprite, true);
		}
	}
}
