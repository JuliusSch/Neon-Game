package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.graphics.Screen;

public abstract class NonMobLevelEntity extends Entity {

	public void update() {
		
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
	}

}
