package com.jcoadyschaebitz.neon.graphics;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.level.tile.Tile;
import com.jcoadyschaebitz.neon.util.Util;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Screen {

	public int width, height;
	public int[] screenPixels;
	public static final int ALPHA_COLOUR = 0xffff00ff;

	public int xOffset, yOffset;

	public Screen(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		screenPixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < screenPixels.length; i++) {
			screenPixels[i] = 0;
		}
	}

	public void renderSheet(int xp, int yp, Spritesheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sheet.pixels[x + y * sheet.WIDTH];
				if (col != 0xffff00ff) screenPixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		renderSprite(xp, yp, tile.sprite, true);
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTranslucentSprite(int xp, int yp, Sprite sprite, boolean fixed, double alpha) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		if (alpha > 1) alpha = 1;
		if (alpha < 0) alpha = 0;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				int newColour = Util.calculateTranslucencyValues(screenPixels[xa + ya * width], col, alpha);
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = newColour;
			}
		}
	}

	public void renderTranslucentSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				double alphaV = (double) sprite.alphas[x + y * sprite.getWidth()] / 0xff;
				int newColour = Util.calculateTranslucencyValues(screenPixels[xa + ya * width], col, alphaV);
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = newColour;
			}
		}
	}

	public void renderAsLightSource(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				int newColour = calculateAsLightSource(screenPixels[xa + ya * width], col);
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = newColour;
			}
		}
	}

	public int calculateAsLightSource(int oldColour, int foreColour) {

		int finalColour;
		double r = (foreColour >> 16) & 0x000000ff;
		double g = (foreColour >> 8) & 0x000000ff;
		double b = foreColour & 0x000000ff;

		double alpha = (0.2126 * r + 0.7152 * g + 0.0722 * b) / 0xff;
		Math.cbrt(alpha);

		int ro = (oldColour >> 16) & 0x000000ff;
		int go = (oldColour >> 8) & 0x000000ff;
		int bo = oldColour & 0x000000ff;

		alpha *= 0.2;

		int rn = (int) (alpha * r + (1 - alpha) * ro);
		int gn = (int) (alpha * g + (1 - alpha) * go);
		int bn = (int) (alpha * b + (1 - alpha) * bo);

		alpha *= 5;
		alpha += 1;

		rn = (int) (alpha * rn);
		gn = (int) (alpha * gn);
		bn = (int) (alpha * bn);
		if (rn > 0xff) rn = 0xff;
		if (gn > 0xff) gn = 0xff;
		if (bn > 0xff) bn = 0xff;
		finalColour = 0xff000000 + (rn << 16) + (gn << 8) + bn;

		return finalColour;
	}

	public void setOffset(int xOffset, int yOffset) { //see Game.render()
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void drawRect(boolean filled, int xp, int yp, int width2, int height2, int colour, boolean fixed) {
		drawRect(true, xp, yp, width2, height2, colour, fixed, -1);
	}

	public void drawRect(boolean filled, int xp, int yp, int width2, int height2, int colour, boolean fixed, int lineWidth) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = yp; y < yp + height2; y++) {
			for (int x = xp; x < xp + width2; x++) {
				if (x < 0 || x >= this.width || y < 0 || y >= this.height) continue;
				if (!filled && y >= yp + lineWidth && y < yp + height2 - lineWidth && x >= xp + lineWidth && x < xp + width2 - lineWidth) {
					continue;
				} else screenPixels[x + y * this.width] = colour;
			}
		}
//		for (int x = xp; x < xp + width2; x++) {
//			if (x < 0 || x >= this.width || yp + height2 >= this.height) continue;
//			screenPixels[x + yp * this.width] = colour;
//			screenPixels[x + (yp + height2) * this.width] = colour;
//		}
//		for (int y = yp; y < yp + height2; y++) {
//			if (xp >= this.width || y < 0 || y >= this.height) continue;
//			screenPixels[xp + y * this.width] = colour;
//			screenPixels[xp + width2 + y * this.width] = colour;
//		}
//		if (filled) {
//			for (int y = 0; y < height2; y++) {
//				int ya = y + yp;
//				for (int x = 0; x < width2; x++) {
//					int xa = x + xp;
//					if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height) continue;
//					screenPixels[xa + ya * this.width] = colour;
//				}
//			}
//		}
	}

	public void drawCircleSegment(boolean filled, Vec2i xy, int rad, double arcStart, double arcEnd, int colour, boolean fixed, double alpha) {
		if (fixed) xy.set(xy.x - xOffset, xy.y - yOffset);
		for (int ya = xy.y - rad; ya < xy.y + rad; ya++) {
			for (int xa = xy.x - rad; xa < xy.x + rad; xa++) {
				if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height) continue;
				double pointAngle = Math.atan2(ya - xy.y, xa - xy.x) + Math.PI;
				double dist = Vec2i.getDistance(xy, new Vec2i(xa, ya));
				if (arcStart < pointAngle && pointAngle < arcEnd && dist < rad) {
					screenPixels[xa + ya * this.width] = colour;
				}
			}
		}
	}

	public void renderChar(int xp, int yp, Sprite sprite, boolean fixed, int fontColour, double alpha) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				int newColour = fontColour;
				if (alpha != 1) newColour = Util.calculateTranslucencyValues(screenPixels[xa + ya * width], fontColour, alpha);
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = newColour;
			}
		}
	}
	
	public void renderTranslucentPixel(int x, int y, int colour, double alpha) {
		x -= xOffset;
		y -= yOffset;
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		int newColour = Util.calculateTranslucencyValues(screenPixels[x + y * width], colour, alpha);
		screenPixels[x + y * width] = newColour;
	}

	public void renderPixel(int x, int y, int colour) {
		x -= xOffset;
		y -= yOffset;
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		screenPixels[x + y * width] = colour;
	}
	
	public void renderTriangle(Vec2i a, Vec2i b, Vec2i c, int colour, double alpha) {
		
	}

	public void renderLine(int x1, int y1, int x2, int y2, int colour, double alpha) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		if (dx > 0) {
			double dxy = dy / dx;
			if (dxy < 1 && dxy > -1) {
				for (double i = 0; i < dx; i++) {
					if (alpha == 1) renderPixel((int) (x1 + i), (int) (dxy * i + y1), colour);
					else renderTranslucentPixel((int) (x1 + i), (int) (dxy * i + y1), colour, alpha);
				}
			} else if (dy > 0) {
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					if (alpha == 1) renderPixel((int) (dyx * i + x1), (int) (y1 + i), colour);
					else renderTranslucentPixel((int) (dyx * i + x1), (int) (y1 + i), colour, alpha);
				}
			} else {
				dx = x1 - x2;
				dy = y1 - y2;
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					if (alpha == 1) renderPixel((int) (dyx * i + x2), (int) (y2 + i), colour);
					else renderTranslucentPixel((int) (dyx * i + x2), (int) (y2 + i), colour, alpha);
				}
			}
		} else {
			dx = x1 - x2;
			dy = y1 - y2;
			double dxy = dy / dx;
			if (dxy < 1 && dxy > -1) {
				for (double i = 0; i < dx; i++) {
					if (alpha == 1) renderPixel((int) (x2 + i), (int) (dxy * i + y2), colour);
					else renderTranslucentPixel((int) (x2 + i), (int) (dxy * i + y2), colour, alpha);
				}
			} else if (dy > 0) {
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					if (alpha == 1) renderPixel((int) (dyx * i + x2), (int) (y2 + i), colour);
					else renderTranslucentPixel((int) (dyx * i + x2), (int) (y2 + i), colour, alpha);
				}
			} else {
				dx = x2 - x1;
				dy = y2 - y1;
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					if (alpha == 1) renderPixel((int) (dyx * i + x1), (int) (y1 + i), colour);
					else renderTranslucentPixel((int) (dyx * i + x1), (int) (y1 + i), colour, alpha);
				}
			}
		}
	}

}
