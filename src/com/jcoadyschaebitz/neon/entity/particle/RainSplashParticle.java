package com.jcoadyschaebitz.neon.entity.particle;

import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;

public class RainSplashParticle extends Particle {
	
	private AnimatedSprite animSprite = new AnimatedSprite(Spritesheet.rain, 6, 6, 3, 2);

	public RainSplashParticle(double x, double y, int spriteW, int spriteH, Sprite sprite) {
		super(x, y, 60, spriteW, spriteH, sprite);
		life = 20 + random.nextInt(20);
		animSprite.setFrameRate(4);
	}
	
	public void update() {
		animSprite.update();
		time++;
		if (time > 20) {
			remove();
		}
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderSprite((int) x, (int) y, sprite, true);
	}
	
	

}
