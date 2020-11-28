package com.jcoadyschaebitz.neon.util;

public class Rect {

	private int x1, y1, x2, y2, width, height;

	public Rect(int x1, int y1, int width, int height) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1 + width;
		this.y2 = y1 + height;
		this.width = width;
		this.height = height;
	}

	public int getX_L() {
		return x1;
	}

	public int getY_T() {
		return y1;
	}

	public int getX_R() {
		return x2;
	}

	public int getY_B() {
		return y2;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
