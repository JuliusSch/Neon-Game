package com.jcoadyschaebitz.neon.entity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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
	protected boolean readyToActivate, active;
	protected RotCollisionBox bounds;
	protected int[] baseCollisionPointsX, baseCollisionPointsY;
	protected Player player;
	public ActionSkillDisplay actionSkillDisplay;
	protected Sprite[] sprites;

	public Shield(double x, double y, double direction, Player player) {
		xa = 16 * Math.cos(direction);
		ya = 16 * Math.sin(direction);
		this.x = x + xa;
		this.y = y + ya;
		totalHealth = 30; //120
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
		baseCollisionPointsX = new int[] { 13, 17, 14, 18, 15, 19, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 16, 20, 15, 19, 14, 18, 13,
				17, };
		baseCollisionPointsY = new int[] { 2, 2, 4, 4, 6, 6, 8, 8, 11, 11, 13, 13, 15, 15, 18, 18, 21, 21, 23, 23, 26, 26, 28, 28, 30, 30, };
		bounds = new RotCollisionBox(baseCollisionPointsX, baseCollisionPointsY);
		boundingBoxOffsetX = -8;
		boundingBoxOffsetY = -4;
		bounds.rotPoints(direction, 32, 32);
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
		if (active) {
			updateRotation();
			checkProjectileCollision();			
		}
	}

	private void updateRotation() {
		direction = player.getDirection();
		xa = 16 * Math.cos(player.getDirection());
		ya = 16 * Math.sin(player.getDirection());
		this.x = player.getX() + xa;
		this.y = player.getY() + ya;
		centreX = x + 8; // half of spriteWidth
		centreY = y + 16; // half of spriteHeight
		bounds.setValues(baseCollisionPointsX.clone(), baseCollisionPointsY.clone());
		bounds.rotPoints(player.getDirection(), 32, 32);
		bounds.offsetPoints(boundingBoxOffsetX, boundingBoxOffsetY);
	}

	public int getYAnchor() {
		return (int) y + 28;
	}

	public void changeDuration(int amount) {
		time += amount;
	}

	public void checkProjectileCollision() {
		List<Projectile> ps = level.getProjectilesInRad((int) centreX, (int) centreY, 50);
		List<Entity> es = new ArrayList<Entity>();
		for (Projectile p : ps) {
			es.add(p);
		}
		List<Entity> es2 = bounds.checkForCollisions(es, x, y);
		for (Entity e : es2) {
			try {
				Projectile p = (Projectile) e;
				if (!p.isEnemyBullet()) continue;
				if (player.getSkillTreeManager().shieldReflectUnlocked) {
					p.shieldCollision(player.getSkillTreeManager().shieldReflectChance, direction, p);
				} else {
					p.collide(p.getIntX(), p.getIntY());
				}
				health += 10;
			} catch (ClassCastException c) {}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 3 && readyToActivate) activate();
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

	public void render(Screen screen) {
		if (!active) return;
		for (int i = 0; i < sprites.length; i++) {
			Sprite s = Sprite.rotateSprite(sprites[i], direction, 64, 64);
			screen.renderTranslucentSprite((int) x - 24, (int) y - 36 + i, s, true);
		}
		bounds.renderBounds(screen, 0xffff00ff, (int) x, (int) y);
	}

	@Override
	public void hitReceived(Projectile projectile) { // think about moving hit functionality to here
	} // in future.

}
