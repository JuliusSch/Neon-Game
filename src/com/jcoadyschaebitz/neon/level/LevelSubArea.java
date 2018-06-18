package com.jcoadyschaebitz.neon.level;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class LevelSubArea {

	private int x1, y1, x2, y2;
	private Sprite sprite;
	boolean playerInArea;
	int fadeCounter;

	public LevelSubArea(int x1, int y1, int x2, int y2) {
		this(x1, y1, x2, y2, new Sprite((x2 - x1 + 1) << 4, (y2 - y1 + 1) << 4, 0xff04070A));
	}

	public LevelSubArea(int x1, int y1, int x2, int y2, Sprite sprite) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.sprite = sprite;
	}

	public void update(int x, int y) {
		if (fadeCounter > 0) fadeCounter--;
		int xp = x >> 4;
		int yp = y >> 4;
		if (x1 <= xp && x2 >= xp) {
			if (y1 <= yp && y2 >= yp) {
				if (!playerInArea) switchArea();
			} else if (playerInArea) switchArea();
		} else if (playerInArea) switchArea();
	}
	
	private void switchArea() {
		if (playerInArea) playerInArea = false;
		else playerInArea = true;
		fadeCounter = 15;
	}

	public void render(Screen screen) {
		if (!playerInArea) {
			if (fadeCounter > 0) screen.renderTranslucentSprite(x1 << 4, y1 << 4, sprite, true, 1 - (double) fadeCounter / 15);
			else screen.renderSprite(x1 << 4, y1 << 4, sprite, true);
		} else if (fadeCounter > 0) screen.renderTranslucentSprite(x1 << 4, y1 << 4, sprite, true, (double) fadeCounter / 15);
	}

}
