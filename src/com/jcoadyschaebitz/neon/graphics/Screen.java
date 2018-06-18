package com.jcoadyschaebitz.neon.graphics;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.level.tile.Tile;

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
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				screenPixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
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
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				int newColour = calculateTranslucencyValues(screenPixels[xa + ya * width], col, alpha);
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = newColour;
			}
		}
	}

	private int calculateTranslucencyValues(int oldColour, int foreColour, double alpha) {
		int finalColour;

		int r = (foreColour >> 16) & 0x000000ff;
		int g = (foreColour >> 8) & 0x000000ff;
		int b = foreColour & 0x000000ff;

		int ro = (oldColour >> 16) & 0x000000ff;
		int go = (oldColour >> 8) & 0x000000ff;
		int bo = oldColour & 0x000000ff;

		int rn = (int) (alpha * r + (1 - alpha) * ro);
		int gn = (int) (alpha * g + (1 - alpha) * go);
		int bn = (int) (alpha * b + (1 - alpha) * bo);
		finalColour = 0xff000000 + (rn << 16) + (gn << 8) + bn;

		return finalColour;
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void drawRect(boolean filled, int xp, int yp, int width2, int height2, int colour, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width2; x++) {
			if (x < 0 || x >= this.width || yp + height2 >= this.height) continue;
			screenPixels[x + yp * this.width] = colour;
			screenPixels[x + (yp + height2) * this.width] = colour;
		}
		for (int y = yp; y <= yp + height2; y++) {
			if (xp >= this.width || y < 0 || y >= this.height) continue;
			screenPixels[xp + y * this.width] = colour;
			screenPixels[xp + width2 + y * this.width] = colour;
		}
		if (filled) {
			for (int y = 0; y < height2; y++) {
				int ya = y + yp;
				for (int x = 0; x < width2; x++) {
					int xa = x + xp;
					if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height) continue;
					screenPixels[xa + ya * this.width] = colour;
				}
			}
		}
	}
	
	public void renderCharacter(int xp, int yp, Sprite sprite, boolean fixed, int fontColour) {
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
				if (col != ALPHA_COLOUR) screenPixels[xa + ya * width] = fontColour;
			}
		}
	}

	public void renderPixel(int x, int y, int colour) {
		x -= xOffset;
		y -= yOffset;
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		screenPixels[x + y * width] = colour;
	}

	public void renderLine(int x1, int y1, int x2, int y2, int colour) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		if (dx > 0) {
			double dxy = dy / dx;
			if (dxy < 1 && dxy > -1) {
				for (double i = 0; i < dx; i++) {
					renderPixel((int) (x1 + i), (int) (dxy * i + y1), colour);
				}
			} else if (dy > 0) {
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					renderPixel((int) (dyx * i + x1), (int) (y1 + i), colour);
				}
			} else {
				dx = x1 - x2;
				dy = y1 - y2;
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					renderPixel((int) (dyx * i + x2), (int) (y2 + i), colour);
				}
			}
		} else {
			dx = x1 - x2;
			dy = y1 - y2;
			double dxy = dy / dx;
			if (dxy < 1 && dxy > -1) {
				for (double i = 0; i < dx; i++) {
					renderPixel((int) (x2 + i), (int) (dxy * i + y2), colour);
				}
			} else if (dy > 0) {
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					renderPixel((int) (dyx * i + x2), (int) (y2 + i), colour);
				}
			} else {
				dx = x2 - x1;
				dy = y2 - y1;
				double dyx = dx / dy;
				for (double i = 0; i < dy; i++) {
					renderPixel((int) (dyx * i + x1), (int) (y1 + i), colour);
				}
			}

		}
	}

}
