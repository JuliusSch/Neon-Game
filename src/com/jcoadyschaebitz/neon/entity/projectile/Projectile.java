package com.jcoadyschaebitz.neon.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.RotCollisionBox;
import com.jcoadyschaebitz.neon.entity.Shield;
import com.jcoadyschaebitz.neon.entity.mob.MeleeEnemy;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.mob.ShootingEnemy;
import com.jcoadyschaebitz.neon.entity.spawner.ParticleSpawner;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;

public abstract class Projectile extends Entity {

	protected double xOrigin, yOrigin;
	public double angle;
	protected Sprite sprite, glow, particle;
	protected double nx, ny;
	protected double speed, range, damage, distanceMoved;
	protected double distance;
	protected int life;
	protected Spritesheet sheet;
	protected Entity source;
	protected boolean isEnemyBullet;
	protected AnimatedSprite bulletAnim;

	public Projectile(Entity source, double x, double y, double angle, Level level) {
		this.angle = angle;
		sprite = Sprite.pistolBullet;
		glow = Sprite.pistolBullet;
		xOrigin = x - sprite.getWidth() / 2;
		yOrigin = y - sprite.getHeight() / 2;
		this.x = xOrigin;
		this.y = yOrigin;
		int[] xCollisionValues = { 6, 10, 6, 10 };
		int[] yCollisionValues = { 6, 6, 10, 10 };
		entityBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		this.source = source;
		damage = 4;
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
		particle = Sprite.particleBlue;
		this.level = level;
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
	}

	public boolean isEnemyBullet() {
		return isEnemyBullet;
	}

	public void move(Entity source, double nx, double ny) {
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
			Entity e = entityCollision(source, (int) (x + nx3), (int) (y + ny3), entityBounds instanceof RotCollisionBox);
			if (e != null/* && time > 1 */) {
				e.hitReceived(this);
				collideEntity((int) (x + nx3), (int) (y + ny3), e);
				break;
			} else if (isHit) {
				collide((int) (x + nx3), (int) (y + ny3));
				break;
			}
			this.x += nx3;
			this.y += ny3;
			nx2 -= nx3;
			ny2 -= ny3;
		}
		distanceMoved += Math.sqrt(nx * nx + ny * ny);

		if (distance() > range)
			remove();
	}

	protected Entity entityCollision(Entity source, int x, int y, boolean isRotated) {
		List<Entity> sources = new ArrayList<Entity>();
		sources.add(this);
		sources.add(source);
		List<Entity> near = level.getValidTargetsInRad(x, y, sources, 48);
		if (near.size() > 0) {
			for (Entity e : near) {
				if (isEnemyBullet() && (e instanceof ShootingEnemy || e instanceof MeleeEnemy)) continue;
				if (e.getCollisionBounds() instanceof RotCollisionBox) {
					if (e instanceof Shield && !((Shield) e).active) continue;
					if (((RotCollisionBox) e.getCollisionBounds()).checkForCollisions(this, x, y, e, isRotated)) return e;
					continue;
				}
				if (e instanceof Player && ((Player) e).getShield().active) {
					if (((RotCollisionBox) (((Player) e).getShield().getCollisionBounds())).checkForCollisions(this, x, y, e, isRotated)) return ((Player) e).getShield();
				}
				int mx0 = e.getCollisionBounds().getXValues()[0] + e.getIntX();
				int my0 = e.getCollisionBounds().getYValues()[0] + e.getIntY();
				int mx1 = e.getCollisionBounds().getXValues()[1] + e.getIntX();
				int my1 = e.getCollisionBounds().getYValues()[2] + e.getIntY();
//				level.add(new DebugParticle(mx0, my0, 30, Sprite.smallParticleLime));
//				level.add(new DebugParticle(mx1, my0, 30, Sprite.smallParticleLime));
//				level.add(new DebugParticle(mx0, my1, 30, Sprite.smallParticleLime));
//				level.add(new DebugParticle(mx1, my1, 30, Sprite.smallParticleLime));
				for (int i = 0; i < getCollisionBounds().getXValues().length; i++) {
					int px = getCollisionBounds().getXValues()[i] + x;
					int py = getCollisionBounds().getYValues()[i] + y;
//					level.add(new DebugParticle(px, py, 30, Sprite.smallParticleCrimson));	// could add some sort of trail here
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

	public void collide(int x, int y) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, particle));
		remove();
	}

	public void collideEntity(int x, int y, Entity e) {
		level.add(new ParticleSpawner((int) x + 8, (int) y + 8, 10, 50, level, particle, -nx / 16, -ny / 16));
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
		} else
			collide((int) this.x, (int) this.y);
	}

	protected double distance() {
		return Math.sqrt((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
	}

	public double getKnockbackMultiplier() {
		return 1;
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x - 8, (int) y - 8, glow, true);
		screen.renderSprite((int) x, (int) y, sprite, true);
//		entityBounds.renderBounds(screen, 0xffaabbcc, (int) x, (int) y);
	}

	public void hitReceived(Projectile projectile) {
	}
}
