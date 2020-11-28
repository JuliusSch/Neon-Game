package com.jcoadyschaebitz.neon.util;

public class Vec2i {

	protected int x, y;

	public Vec2i(int x, int y) {
		set(x, y);
	}

	public Vec2i() {
		set(0, 0);
	}

	public Vec2i(Vec2i v) {
		set(v.x, v.y);
	}

	public Vec2i(String key) {
		try {
			String sub = key.replace("(", "");
			sub = sub.replace(")", "");
			String[] vals = sub.split(",");
			set(Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
		} catch (Exception e) {
			System.err.println("Error: Incorrect key in constructor of class Vec2i.");
			set(0, 0);
		}
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int X() {
		return x;
	}

	public Vec2i setX(int x) {
		this.x = x;
		return this;
	}

	public int Y() {
		return y;
	}

	public Vec2i setY(int y) {
		this.y = y;
		return this;
	}

	public Vec2i add(Vec2i v) {
		return new Vec2i(this.x + v.x, this.y + v.y);
	}

	public Vec2i subtract(Vec2i v) {
		return new Vec2i(this.x - v.x, this.y - v.y);
	}

	public boolean equals(Object o) {
		if (!(o instanceof Vec2i)) return false;
		Vec2i v = (Vec2i) o;
		if (v.X() == X() && v.Y() == Y()) return true;
		return false;
	}

	public static double getDistance(Vec2i a, Vec2i b) {
		double dx = a.X() - b.X();
		double dy = a.Y() - b.Y();
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}

	public String getKey() {
		return "(" + X() + "," + Y() + ")";
	}

	public void print() {
		System.out.print("(" + x + "," + y + ")");
	}

	public void println() {
		System.out.println("(" + x + "," + y + ")");
	}
}
