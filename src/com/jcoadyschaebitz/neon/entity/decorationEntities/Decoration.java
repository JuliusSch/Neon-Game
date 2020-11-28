package com.jcoadyschaebitz.neon.entity.decorationEntities;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class Decoration extends Entity {

	private boolean translucent;
	private List<Sprite> tSprites = new ArrayList<Sprite>();
	private List<Double> tValues = new ArrayList<Double>();
	private List<Integer> tOffsetX = new ArrayList<Integer>();
	private List<Integer> tOffsetY = new ArrayList<Integer>();
	
	public Decoration(int x, int y, Sprite sprite) {
		this(x, y, sprite, false);
	}
	
	public Decoration(int x, int y, Sprite sprite, boolean translucent) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.translucent = translucent;
	}

	public void addTranslucentSprite(Sprite sprite, double tValue, int x, int y) {
		tSprites.add(sprite);
		tValues.add(tValue);
		tOffsetX.add(x);
		tOffsetY.add(y);
	}
	
	public void update() {
	}

	public void render(Screen screen) {
		if (translucent) screen.renderTranslucentSprite((int) x, (int) y, sprite, true);
		else screen.renderSprite((int) x, (int) y, sprite, true);
		for (int i = 0; i < tSprites.size(); i++) {
			screen.renderTranslucentSprite((int) x + tOffsetX.get(i), (int) y + tOffsetY.get(i), tSprites.get(i), true, tValues.get(i));
		}
	}

	@Override
	public void hitReceived(Projectile projectile) {
	}

}
