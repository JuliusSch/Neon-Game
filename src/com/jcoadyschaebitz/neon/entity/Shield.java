package com.jcoadyschaebitz.neon.entity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.graphics.UI.skillTrees.ActionSkillDisplay;

public class Shield extends Entity implements MouseListener {

	protected double xa, ya, direction;
	protected int boundingBoxOffsetX, boundingBoxOffsetY;
	protected int health, totalHealth, resetTimer, resetDelay;
	protected boolean readyToActivate;
	protected int[] baseCollisionPointsX, baseCollisionPointsY;
	protected Player player;
	public ActionSkillDisplay actionSkillDisplay;
	public boolean active;
	protected Sprite[] sprites;

	public Shield(double x, double y, double direction, Player player) {
		xa = 16 * Math.cos(direction);
		ya = 16 * Math.sin(direction);
		this.x = x + xa;
		this.y = y + ya;
		totalHealth = 3000; //120 //30
		health = totalHealth;
		resetDelay = 180;
		readyToActivate = true;
		this.direction = direction;
		this.player = player;
		level = player.level;
		Sprite[] temp = Spritesheet.shield2.getSprites();
		sprites = new Sprite[temp.length];
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = Sprite.extendSprite(temp[i], 26, 30, 0, 0, 0xffff00ff);
		}
		baseCollisionPointsX = new int[] { 15, 19, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 15, 19 };
		baseCollisionPointsY = new int[] { 6, 6, 8, 8, 11, 11, 13, 13, 15, 15, 18, 18, 21, 21, 23, 23, 26, 26 };
		entityBounds = new RotCollisionBox(baseCollisionPointsX, baseCollisionPointsY);
		boundingBoxOffsetX = -8;
		boundingBoxOffsetY = -4;
		((RotCollisionBox) entityBounds).rotatePoints(direction, 32, 32);
		actionSkillDisplay = new ActionSkillDisplay(10, 190, Game.getUIManager());
	}

	public void update() {
		time++;
		actionSkillDisplay.updateStats(active, health, 0, totalHealth);
		level = player.level;
		if (active && time % 3 == 0) health--;
		if (health <= 0) {
			readyToActivate = false;
			deactivate();
		}
		if (resetTimer > resetDelay) {
			readyToActivate = true;
			resetTimer = 0;
			health = 1;
		}
		if (!readyToActivate) resetTimer++;
		else if (!active && health < totalHealth && time % 3 == 0) health++;
		if (active) updateRotation();
	}

	private void updateRotation() {
		direction = player.getDirection();
		xa = 16 * Math.cos(player.getDirection());
		ya = 16 * Math.sin(player.getDirection());
		this.x = player.getX() + xa;
		this.y = player.getY() + ya;
		centreX = x + 8; // half of spriteWidth
		centreY = y + 16; // half of spriteHeight
		((RotCollisionBox) entityBounds).setValues(baseCollisionPointsX.clone(), baseCollisionPointsY.clone());
		((RotCollisionBox) entityBounds).rotatePoints(player.getDirection(), 32, 32);
		((RotCollisionBox) entityBounds).offsetPoints(boundingBoxOffsetX, boundingBoxOffsetY);
	}

	public int getYAnchor() {
		return (int) y + 28;
	}

	public void changeDuration(int amount) {
		time += amount;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3 && readyToActivate && Game.getUIManager().getGame().getState() == Game.getUIManager().getGame().playState) activate();
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 3) deactivate();
	}

	private void activate() {
		active = true;
		updateRotation();
	}

	private void deactivate() {
		active = false;
	}

	@Override
	public void hitReceived(Projectile projectile) {
		if (!active) return;
		if (!projectile.isEnemyBullet()) return;
		if (player.getSkillTreeManager().shieldReflectUnlocked) {
			projectile.shieldCollision(player.getSkillTreeManager().shieldReflectChance, direction, projectile);
		} else {
			projectile.collide(projectile.getIntX(), projectile.getIntY());
		}
		health += 10;
	}
	
	public void render(Screen screen) {
		if (!active) return;
		for (int i = 0; i < sprites.length; i++) {
			Sprite s = Sprite.rotateSprite(sprites[i], direction, 64, 64);
			screen.renderTranslucentSprite((int) x - 24, (int) y - 36 + i, s, true);
		}
//		((RotCollisionBox) entityBounds).renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}
}
