package com.jcoadyschaebitz.neon.util;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class Util {

	private Util() {
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, int colour, boolean fixed) {
		
		screen.drawRect(false, x, y, width, height, colour, fixed);
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed) {
		screen.drawRect(false, x, y, width, height, 0xff0000, fixed);
	} 
	
	public static boolean contains(final int[] in, final int e) {
		for (int i : in) {
			if (i == e) return true;
		}
		return false;
	}
	
	public static double pythag(double a, double b) {
		return Math.sqrt((a * a) + (b * b));
	}
	
	public static int calculateTranslucencyValues(int oldColour, int foreColour, double alpha) {
		if (alpha == 1) return foreColour;
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
	
//	public static Sprite getLighting(Sprite sprite) {
//		int[] pxls = new int[16 * 16];
//		int[] sprPxls = sprite.pixels;
//		for (int y = 0; y < 16; y++) {
//			for (int x = 0; x < 16; x++) {
//				pxls[x + y * 16] = modifyLight(sprPxls[x + y * 16], x-8);
//			}
//		}
//	}
//	
//	private static int modifyLight(int colour, int amount) {
//		int finalColour;
//		double amnt = amount / 100;
//		finalColour = 
//	}
	
}
