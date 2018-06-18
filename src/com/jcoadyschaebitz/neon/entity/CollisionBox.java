package com.jcoadyschaebitz.neon.entity;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class CollisionBox {

	private int[] origXValues, origYValues;

	public CollisionBox(int[] xV, int[] yV) {
		origXValues = xV;
		origYValues = yV;
		if (origXValues.length != origYValues.length) System.err.println("CollisionBox XY array length mismatch.");
	}
	
	public int[] getXValues() {
		return origXValues;
	}

	public int[] getYValues() {
		return origYValues;
	}

	public void renderBounds(Screen screen, int colour, int x, int y) {
		for (int i = 0; i < origXValues.length; i++) {
			screen.renderPixel(x + origXValues[i], y + origYValues[i], colour);
		}
	}

}
