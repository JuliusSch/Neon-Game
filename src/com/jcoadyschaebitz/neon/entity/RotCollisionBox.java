package com.jcoadyschaebitz.neon.entity;

import java.util.ArrayList;
import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Screen;

public class RotCollisionBox {

	private final int[] origXValues, origYValues;
	private int[] rotXValues, rotYValues;

	public RotCollisionBox(int[] x, int[] y) {
		this.origXValues = x;
		this.origYValues = y;
		rotXValues = x;
		rotYValues = y;
		if (origXValues.length != origYValues.length) System.err.println("rotCollisionBox XY array length mismatch.");
	}

	public int[] getXValues() {
		return rotXValues;
	}

	public int[] getYValues() {
		return rotYValues;
	}

	public void setValues(int[] x, int[] y) {
		rotXValues = x;
		rotYValues = y;
	}
	
	public void offsetPoints(int offsetX, int offsetY) {
		for (int i = 0; i < rotXValues.length; i++) {
			rotXValues[i] += offsetX;
			rotYValues[i] += offsetY;
		}
	}

	public void rotPoints(double angle1, int width, int height) {
		 for (int i = 0; i < origXValues.length; i++) {
		 double x0 = origXValues[i] - (width / 2);
		 double y0 = origYValues[i] - (height / 2);
		 double angle0 = Math.atan(y0 / x0);
		 double angle2 = angle0 + angle1;
		 double z = y0 / Math.sin(angle0);
		
		 double x1 = z * Math.cos(angle2);
		 double y1 = z * Math.sin(angle2);
		
		 rotXValues[i] = (int) Math.round(x1 + (width / 2));
		 rotYValues[i] = (int) Math.round(y1 + (height / 2));
		 }
	}
	
	public List<Entity> checkForCollisions(List<Entity> entities, double x, double y) {
		List<Entity> hits = new ArrayList<Entity>();
		int px0, px1, py0, py1;
		for (Entity e : entities) {
			px0 = e.entityBounds.getXValues()[0] + e.getIntX();
			px1 = e.entityBounds.getXValues()[1] + e.getIntX();
			py0 = e.entityBounds.getYValues()[0] + e.getIntY();
			py1 = e.entityBounds.getYValues()[2] + e.getIntY();
			for (int i = 0; i < rotXValues.length; i++) {
				if ((rotXValues[i] + x) > px0 && (rotXValues[i] + x) < px1) {
					if ((rotYValues[i] + y) > py0 && (rotYValues[i] + y) < py1) {
						hits.add(e);
					}
				}
			}
		}
		return hits;
	}

	public void renderBounds(Screen screen, int colour, int x, int y) {
		for (int i = 0; i < rotXValues.length; i++) {
			screen.renderPixel(rotXValues[i] + x, rotYValues[i] + y, colour);
		}
	}
}
