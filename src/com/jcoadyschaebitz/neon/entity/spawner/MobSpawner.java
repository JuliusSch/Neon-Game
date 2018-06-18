package com.jcoadyschaebitz.neon.entity.spawner;

import com.jcoadyschaebitz.neon.level.Level;

public class MobSpawner extends Spawner {
	
	public MobSpawner(double x, double y, Level level) {
		super(x, y, level);
	}
	
	public void update() {
		if (level.isPlayerInRad(this, 20)) {
			remove();
		}
	}
}
