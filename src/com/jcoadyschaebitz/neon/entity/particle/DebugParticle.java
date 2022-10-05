package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class DebugParticle extends Particle {

	public DebugParticle(double x, double y, int maxLife, Sprite sprite) {
		super(x, y, maxLife, sprite.getWidth(), sprite.getHeight(), sprite);
		zIndex = 2;
	}

	public void update() {
		time++;
		if (time > life) remove();
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx, (int) yy, sprite, true);
	}
}
