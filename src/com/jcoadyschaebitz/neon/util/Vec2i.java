package com.jcoadyschaebitz.neon.util;

public class Vec2i {

	public int x, y;

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

	public Vec2i add(Vec2i v) {
		return new Vec2i(this.x + v.x, this.y + v.y);
	}
	
	public Vec2i add(int x, int y) {
		return new Vec2i(this.x + x, this.y + y);
	}

	public Vec2i subtract(Vec2i v) {
		return new Vec2i(this.x - v.x, this.y - v.y);
	}

	public Vec2i normal() {
		return new Vec2i(-y, x);
	}
	
	public int dot(Vec2i v2) {
		return (x * v2.x) + (y * v2.y);
	}

	public boolean equals(Object o) {
		if (!(o instanceof Vec2i)) return false;
		Vec2i v = (Vec2i) o;
		if (v.x == x && v.y == y) return true;
		return false;
	}

	public static double getDistance(Vec2i a, Vec2i b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
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
		return "(" + x + "," + y + ")";
	}

	public void print() {
		System.out.print("(" + x + "," + y + ")");
	}

	public void println() {
		System.out.println("(" + x + "," + y + ")");
	}
}
