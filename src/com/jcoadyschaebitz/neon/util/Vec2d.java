package com.jcoadyschaebitz.neon.util;

public class Vec2d {
	
	private double x, y;

	public Vec2d(double x, double  y) {
		set(x, y);	
	}

	public Vec2d() {
		set(0, 0);
	}
	
	public Vec2d(Vec2d v) {
		set(v.x, v.y);
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public Vec2d setX(double x) {
		this.x = x;
		return this;
	}

	public double getY() {
		return y;
	}

	public Vec2d setY(double y) {
		this.y = y;
		return this;
	}
	
	public Vec2d add(Vec2d v) {
		this.x += v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vec2d sub(Vec2d v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vec2d)) return false;
		Vec2d v = (Vec2d) o;
		if (v.getX() == getX() && v.getY() == getY()) return true;
		else return false;
	}
	
	public static double getDistance(Vec2d a, Vec2d b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}
}
