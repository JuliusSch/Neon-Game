package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.entity.mob.Player;

public class StationaryShield extends Shield {
	
//	private double slowingAmount = 0.4;
	
	public StationaryShield(double x, double y, double direction, Player player) {
		super(x, y, direction, player);
		xa = 20 * Math.cos(direction);
		ya = 20 * Math.sin(direction);
		this.x = x + xa;
		this.y = y + ya;
//		player.movementSpeedMultiplier = player.movementSpeedMultiplier * slowingAmount;
	}
	
	public void update() {
//		super.update();
	}
}
