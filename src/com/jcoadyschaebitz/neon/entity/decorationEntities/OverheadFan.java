package com.jcoadyschaebitz.neon.entity.decorationEntities;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class OverheadFan extends Decoration {
	
	private double speed, direction;
	private Sprite rotSprite;

	public OverheadFan(int x, int y, double speed) {
		super(x, y, Sprite.overheadFan);
		this.speed = speed;
	}
	
	public void update() {
		direction += speed;
		rotSprite = Sprite.rotateSprite(sprite, direction, sprite.getWidth(), sprite.getHeight());
	}
	
	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, rotSprite, true);
	}

}
