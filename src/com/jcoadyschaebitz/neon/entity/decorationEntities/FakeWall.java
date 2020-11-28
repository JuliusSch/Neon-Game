package com.jcoadyschaebitz.neon.entity.decorationEntities;

import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;

public class FakeWall extends Decoration {

	private int width, height;

	public FakeWall(int x, int y, int width, int height) {
		super(x * 16, y * 16, Sprite.fakeWallTop);
		this.width = width - 1;
		this.height = height;
	}

	public void render(Screen screen) {
		screen.renderTranslucentSprite((int) x, (int) (y - (height * 16)), Sprite.fakeWallTopLeft, true);
		screen.renderTranslucentSprite((int) (x + (width * 16)), (int) (y - (height * 16)), Sprite.fakeWallTopRight, true);
		screen.renderTranslucentSprite((int) x, (int) y, Sprite.fakeWallBottomLeft, true);
		screen.renderTranslucentSprite((int) (x + (width * 16)), (int) y, Sprite.fakeWallBottomRight, true);

		for (int i = 1; i < width; i++) {
			screen.renderTranslucentSprite((int) (x + (i * 16)), (int) (y - (height * 16)), Sprite.fakeWallTop, true);
			screen.renderTranslucentSprite((int) (x + (i * 16)), (int) y, Sprite.fakeWallBottom, true);
		}
		for (int j = -height + 1; j < 0; j++) {
			screen.renderTranslucentSprite((int) x, (int) (y + (j * 16)), Sprite.fakeWallLeft, true);
			screen.renderTranslucentSprite((int) (x + (width * 16)), (int) (y + (j * 16)), Sprite.fakeWallRight, true);
		}
//		int px = level.getPlayer().getIntX();
//		int py = level.getPlayer().getIntY();
////		double cx = (Mouse.getX() - Game.getWindowWidth() / 2) - Game.getXBarsOffset();
////		double cy = Mouse.getY() - Game.getWindowHeight() / 2;
//		for (int i = 0; i <= width; i++) {
//			for (int j = -height; j <= 0; j++) {
//				double distanceP = Vector2i.getDistance(new Vector2i(px, py), new Vector2i((int) x + (i * 16), (int) y + (j * 16)));
//				distanceP = distanceP - 100 < 0 ? 0 : distanceP - 100;
//				double alphaP = distanceP / 60 > 1 ? 1 : distanceP / 60;
////				double distanceC = Vector2i.getDistance(new Vector2i(cx, cy), b)
//				screen.renderTranslucentSprite((int) (x + (i * 16)), (int) (y + (j * 16)), Sprite.wall, true, alphaP);
//			}
//		}
//
////		for (int i = 0; i <= width; i++) {
////			screen.renderSprite((int) (x + (i * 16)), (int) y - (height * 16), Sprite.tempBorder, true);
//		}
	}

}
