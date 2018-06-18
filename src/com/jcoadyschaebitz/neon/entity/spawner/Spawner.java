package com.jcoadyschaebitz.neon.entity.spawner;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.level.Level;

public class Spawner extends Entity {
	
	protected double x, y;
	
	public Spawner(double x, double y, Level level) {
		init(level);
		this.x = x;
		this.y = y;
	}

	public void update() {
	}

	public void render(Screen screen) {
	}
	
}
