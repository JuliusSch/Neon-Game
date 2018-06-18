package com.jcoadyschaebitz.neon.util;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class Debug {

	private Debug() {
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, int colour, boolean fixed) {
		
		screen.drawRect(false, x, y, width, height, colour, fixed);
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed) {
		screen.drawRect(false, x, y, width, height, 0xff0000, fixed);
	} 
	
}
