package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class MuzzleFlash extends Particle {
	
	Sprite glow;

	public MuzzleFlash(double x, double y, int maxLife, Sprite sprite, Sprite glow, double angle) {
		super(x, y, maxLife, sprite.getWidth(), sprite.getHeight(), sprite);
		life = maxLife;
		this.sprite = Sprite.rotateSprite(sprite, angle, sprite.getWidth(), sprite.getHeight());
		this.glow = Sprite.rotateSprite(glow, angle, glow.getWidth(), glow.getHeight());
	}

	public void update() {
		time++;
		if (time > life) remove();
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, glow, true, 0.1);
		screen.renderSprite((int) x, (int) y, sprite, true);
	}
}
