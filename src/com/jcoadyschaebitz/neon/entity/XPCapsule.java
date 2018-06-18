package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class XPCapsule extends Entity {

	protected double speed, moveDirection, spriteDirection, nx, ny;
	protected int timeLimit;
	protected Sprite rotSprite, rotGlow, shadowSprite;

	public XPCapsule(int x, int y) {
		this.x = x;
		this.y = y;
		spriteWidth = 16;
		spriteHeight = 16;
		speed = 1.2 - (Math.pow(random.nextDouble(), 2));
		timeLimit = random.nextInt(10) + 10;
		moveDirection = Math.abs(random.nextDouble() * 6);
		spriteDirection = Math.abs(random.nextDouble() * 3);
		rotSprite = Sprite.rotateSprite(Sprite.xp, spriteDirection, spriteWidth, spriteHeight);
		rotGlow = Sprite.rotateSprite(Sprite.xpGlow, spriteDirection, spriteWidth, spriteHeight);
		shadowSprite = Sprite.xpShadow;
		nx = Math.cos(moveDirection) * speed;
		ny = Math.sin(moveDirection) * speed;
	}

	public void update() {
		if (speed != 0) spriteDirection += 0.1;
		time++;
		rotSprite = Sprite.rotateSprite(Sprite.xp, spriteDirection, spriteWidth, spriteHeight);
		rotGlow = Sprite.rotateSprite(Sprite.xpGlow, spriteDirection, spriteWidth, spriteHeight);
		if (time > timeLimit) speed = 0;
		if (level.isPlayerInRad(this, 50) && time > 20) {
			double px = level.getPlayer().getX();
			double py = level.getPlayer().getY();
			double dx = px - x;
			double dy = py - y;
			moveDirection = Math.atan2(dy, dx);
			speed = 2;
			nx = Math.cos(moveDirection) * speed;
			ny = Math.sin(moveDirection) * speed;
		}
		move();
		if (level.isPlayerInRad(this, 10) && time > 20) {
			level.getPlayer().addXP(1);
			remove();
		}
	}

	public void move() {
		if (level.getTile((x + 8 + nx) / 16, (y + 8 + ny) / 16).isSolid()) return;
		else {
			x += nx * speed;
			y += ny * speed;
		}
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, shadowSprite, true, 0.5);
		screen.renderTranslucentSprite((int) x, (int) y, rotGlow, true, 0.1);
		screen.renderSprite((int) x, (int) y, rotSprite, true);
	}
	
	public int getSpriteH() {
		return 16;
	}

}
