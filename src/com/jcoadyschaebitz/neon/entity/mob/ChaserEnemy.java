package com.jcoadyschaebitz.neon.entity.mob;

import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class ChaserEnemy extends ShootingEnemy {

//	private List<Node> path;
//	private int time = 0;

	public ChaserEnemy(int x, int y) {
		super(x, y);
		this.x = x << 4;
		this.y = y << 4;
		leftWalking = new AnimatedSprite(Spritesheet.soldier_left_walking, 24, 24, 8, 3);
		rightWalking = new AnimatedSprite(Spritesheet.soldier_right_walking, 24, 24, 8, 3);
		leftIdle = new AnimatedSprite(Spritesheet.soldier_left_idle, 24, 24, 4, 10);
		rightIdle = new AnimatedSprite(Spritesheet.soldier_right_idle, 24, 24, 4, 10);
		leftDamage = new AnimatedSprite(Spritesheet.soldier_left_damage, 24, 24, 2, 10);
		rightDamage = new AnimatedSprite(Spritesheet.soldier_right_damage, 24, 24, 2, 10);
		leftDying = new AnimatedSprite(Spritesheet.soldier_left_dying, 24, 24, 4, 10);
		rightDying = new AnimatedSprite(Spritesheet.soldier_right_dying, 24, 24, 4, 10);
		sprite = rightWalking.getSprite();
		maxHealth = 6;
		health = maxHealth;
		deadSpriteRight = Sprite.soldier_dead_right;
		deadSpriteLeft = Sprite.soldier_dead_left;
	}

	private void move() {

		xa = 0;
		ya = 0;
//		int px = (int) level.getPlayer().getX();
//		int py = (int) level.getPlayer().getY();
//		Vector2i start = new Vector2i((int) x >> 4, (int) y >> 4);
//		Vector2i goal = new Vector2i(px >> 4, py >> 4);
//		if (Vector2i.getDistance(start, goal) < 10 && time % 3 == 0) path = level.findPath(start, goal);
//		if (path != null) {
//			if (path.size() > 0) {
//				Vector2i v = path.get(path.size() - 1).tile;
//				if (x < v.getX() << 4) xa += 0.8;
//				if (x > v.getX() << 4) xa -= 0.8;
//				if (y < v.getY() << 4) ya += 0.8;
//				if (y > v.getY() << 4) ya -= 0.8;
//			}
//		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {

		if (level.getPlayer().getX() < x) dir = Direction.LEFT;
		if (level.getPlayer().getX() > x) dir = Direction.RIGHT;

		time++;
		move();
		updateAnimSprites();
	}

}
