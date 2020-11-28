package com.jcoadyschaebitz.neon.cutscene;

import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class Actor {
	
	private Mob m; 

	public Actor(Mob m) {
		this.m = m;
	}
	
	public Mob getMob() {
		return m;
	}
	
	public void move(double xa, double ya) {
		m.move(xa, ya, true);
		m.walking = true;
	}
	
	public void update() {
	}
	
	public void render(Screen screen) {
		m.render(screen);
	}
	
}
