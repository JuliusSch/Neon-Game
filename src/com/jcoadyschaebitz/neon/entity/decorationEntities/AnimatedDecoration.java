package com.jcoadyschaebitz.neon.entity.decorationEntities;

import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Screen;

public class AnimatedDecoration extends Decoration {
	
	private AnimatedSprite animSprite;
	private double opacity;

	public AnimatedDecoration(int x, int y, double opacity, AnimatedSprite animSprite) {
		super(x, y, animSprite.getSprite());
		this.animSprite = animSprite;
		this.animSprite.playContinuous();
		this.opacity = opacity;
	}
	
	public void update() {
		animSprite.update();
		sprite = animSprite.getSprite();
	}
	
	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) y, sprite, true, opacity);
	}
	
}
