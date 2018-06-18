package com.jcoadyschaebitz.neon.entity.decorationEntities;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Decoration extends Entity {
	
	private List<Sprite> tSprites = new ArrayList<Sprite>();
	private List<Double> tValues = new ArrayList<Double>();
	
	public Decoration(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void addTranslucentSprite(Sprite sprite, double tValue) {
		tSprites.add(sprite);
		tValues.add(tValue);
	}
	
	public void update() {
	}

	public void render(Screen screen) {
		screen.renderSprite((int) x, (int) y, sprite, true);
		for (int i = 0; i < tSprites.size(); i++) {
			screen.renderTranslucentSprite((int) x, (int) y, tSprites.get(i), true, tValues.get(i));
		}
	}

}
