package com.jcoadyschaebitz.neon.entity.decorationEntities;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Vent extends Decoration {
	
	private Sprite mainSprite, bladeSprite, rotBSprite, coverSprite;
	private double speed, direction;
	
	public Vent(int x, int y, double speed) {
		super(x, y, Sprite.biggerVent);
		this.speed = speed;
		this.mainSprite = Sprite.biggerVent;
		this.bladeSprite = Sprite.bigVentBlades;
		rotBSprite = Sprite.bigVentBlades;
		coverSprite = Sprite.bigVentCover;
	}

	public void update() {
		time++;
		direction += speed;
		rotBSprite = Sprite.rotateSprite(bladeSprite, direction, bladeSprite.getWidth(), bladeSprite.getHeight());
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, mainSprite, true);
		screen.renderSprite((int) x, (int) y, rotBSprite, true);
		screen.renderSprite((int) x, (int) y, coverSprite, true);
	}

}
