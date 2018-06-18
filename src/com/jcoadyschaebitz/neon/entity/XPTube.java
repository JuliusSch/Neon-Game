package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class XPTube extends CollisionEntity {

	private int totalHealth, amount;

	public XPTube(int x, int y, int amount) {
		super(x, y);
		sprite = Sprite.xpTube;
		shadowSprite = Sprite.xpTubeShadow;
		this.amount = amount;
		totalHealth = 8;
		health = totalHealth;
		int[] xCollPoints = { 4, 11, 4, 11 };
		int[] yCollPoints = { 5, 5, 15, 15 };
		entityBounds = new CollisionBox(xCollPoints, yCollPoints);
	}

	public XPTube(int x, int y) {
		this(x, y, 8);
	}

	public void update() {
		time++;
		if (health <= 0) {
			for (int i = 0; i < amount; i++)
				level.add(new XPCapsule(getIntX() + 8, getIntY() + 8));
			remove();
		}
	}
	
	public void hitReceived(Projectile p) {
		p.collide(p.getIntX(), p.getIntY());
		health -= p.getDamage();
	}

}
