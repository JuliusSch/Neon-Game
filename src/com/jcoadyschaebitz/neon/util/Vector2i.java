package com.jcoadyschaebitz.neon.util;

public class Vector2i {

	private int x, y;

	public Vector2i(int x, int y) {
		set(x, y);	}

	public Vector2i() {
		set(0, 0);
	}
	
	public Vector2i(Vector2i v) {
		set(v.x, v.y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Vector2i setY(int y) {
		this.y = y;
		return this;
	}
	
	public Vector2i add(Vector2i v) {
		this.x += v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector2i sub(Vector2i v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vector2i)) return false;
		Vector2i v = (Vector2i) o;
		if (v.getX() == getX() && v.getY() == getY()) return true;
		else return false;
	}
	
	public static double getDistance(Vector2i a, Vector2i b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}
	public static double getDistance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}
}
