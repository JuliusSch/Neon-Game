package com.jcoadyschaebitz.neon.entity.projectile;

import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.RotCollisionBox;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.level.Level;

public class SwordSlash extends Projectile {
	
	private AnimatedSprite slashSprite;
	private CollisionBox origBounds;

	public SwordSlash(Entity source, double x, double y, double angle, double speed, double range, Level level) {
		super(source, x, y, angle, level);
		this.x = x - Spritesheet.swordFlash.getSprites()[0].getWidth() / 2;
		this.y = y - Spritesheet.swordFlash.getSprites()[0].getHeight() / 2;
		isEnemyBullet = true;
		this.range = range;
		damage = 6;
		this.speed = speed;
		slashSprite = new AnimatedSprite(Spritesheet.swordFlash, 64, 64, 3, 5);
		slashSprite.playOnce();
		this.angle = angle;

 		nx = Math.cos(angle) * speed / 100;
		ny = Math.sin(angle) * speed / 100;
		
		int[] xCollisionValues = { 24, 48, 24, 48, 24, 48, 24, 48, 24, 48 };
		int[] yCollisionValues = { 11, 11, 45, 45, 19, 19, 27, 27, 36, 36 };
		origBounds = new CollisionBox(xCollisionValues, yCollisionValues);
		entityBounds = new RotCollisionBox(xCollisionValues.clone(), yCollisionValues.clone());
		((RotCollisionBox) entityBounds).rotatePoints(angle, slashSprite.getWidth(), slashSprite.getHeight());
	}
	
	public CollisionBox getCollisionBounds() {
		return origBounds;
	}
	
	public int getSpriteW() {
		return slashSprite.getWidth();
	}
	
	public int getSpriteH() {
		return slashSprite.getHeight();
	}
	
	public void update() {
		super.update();
		slashSprite.update();
	}
	
	public void collide(int x, int y) {
		remove();
	}

	public void collideEntity(int x, int y, Entity e) {
		remove();
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, Sprite.rotateSprite(slashSprite.getSprite(), angle, slashSprite.getWidth(), slashSprite.getHeight()), true);
		entityBounds.renderBounds(screen, 0xffffffaa, (int) x, (int) y);
	}

}
