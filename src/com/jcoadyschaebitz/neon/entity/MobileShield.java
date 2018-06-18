package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class MobileShield extends Shield {
	
	private double slowingAmount = 0.4;
	
	public MobileShield(double x, double y, double direction, int duration, Player player) {
		super(x, y, direction, duration, player);
		xa = 20 * Math.cos(direction);
		ya = 20 * Math.sin(direction);
		this.x = x + xa - 8;
		this.y = y + ya - 4;
//		player.movementSpeedMultiplier = player.movementSpeedMultiplier * slowingAmount;
	}
	
	public void update() {
		super.update();
		xa = 20 * Math.cos(player.getDirection());
		ya = 20 * Math.sin(player.getDirection());
		this.x = player.getX() + xa - 8;
		this.y = player.getY() + ya - 4;
		int[] xCollPoints = { 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18, 14, 18 };
		int[] yCollPoints = { 2, 2, 4, 4, 6, 6, 8, 8, 11, 11, 13, 13, 15, 15, 18, 18, 21, 21, 23, 23, 26, 26, 28, 28, 31, 31 };
		bounds = new RotCollisionBox(xCollPoints, yCollPoints);
		bounds.rotPoints(player.getDirection(), 32, 32);
		if (firstAnim.active) {
			sprite = Sprite.rotateSprite(firstAnim.getSprite(), player.getDirection(), 32, 32);
			glow = Sprite.rotateSprite(firstAnimGlow.getSprite(), player.getDirection(), 32, 32);
		} else {
			sprite = Sprite.rotateSprite(mainAnim.getSprite(), player.getDirection(), 32, 32);
			glow = Sprite.rotateSprite(mainAnimGlow.getSprite(), player.getDirection(), 32, 32);
		}
	}
	
	public void readyRemove() {
		super.readyRemove();
//		player.movementSpeedMultiplier = player.movementSpeedMultiplier * (1 / slowingAmount);
	}

}
