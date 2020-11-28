package com.jcoadyschaebitz.neon.level;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Rect;

public class LevelSubArea {

	private Rect borderArea, shadedArea;
	private Sprite sprite, sprite2;
	private int sprite2X, sprite2Y;
	boolean playerInArea;
	int fadeCounter;

	public LevelSubArea(Rect border, Rect shaded, Sprite sprite2, int x, int y) {
		borderArea = border;
		shadedArea = shaded;
		this.sprite2 = sprite2;
		sprite = new Sprite((shaded.getWidth()) << 4, (shaded.getHeight()) << 4, 0xff000005);
		sprite2X = x;
		sprite2Y = y;
	}

	public void update(int x, int y) {
		if (fadeCounter > 0) fadeCounter--;
		int xp = x >> 4;
		int yp = y >> 4;
		if (borderArea.getX_L() <= xp && borderArea.getX_R() >= xp) {
			if (borderArea.getY_T() <= yp && borderArea.getY_B() >= yp) {
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
			if (fadeCounter > 0) {
				screen.renderTranslucentSprite(shadedArea.getX_L() << 4, shadedArea.getY_T() << 4, sprite, true, 1 - (double) fadeCounter / 15);
				screen.renderTranslucentSprite(sprite2X << 4, sprite2Y << 4, sprite2, true, 1 - (double) fadeCounter / 15);
			}
			else {
				screen.renderSprite(shadedArea.getX_L() << 4, shadedArea.getY_T() << 4, sprite, true);
				screen.renderSprite(sprite2X << 4, sprite2Y << 4, sprite2, true);
			}
		} else if (fadeCounter > 0) {
			screen.renderTranslucentSprite(shadedArea.getX_L() << 4, shadedArea.getY_T() << 4, sprite, true, (double) fadeCounter / 15);
			screen.renderTranslucentSprite(sprite2X << 4, sprite2Y << 4, sprite2, true, (double) fadeCounter / 15);
		}
	}

}
