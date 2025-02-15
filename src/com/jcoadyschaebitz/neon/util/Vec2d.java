package com.jcoadyschaebitz.neon.util;

public class Vec2d {
	
	public double x, y;

	public Vec2d(double x, double  y) {
		set(x, y);	
	}

	public Vec2d() {
		set(0, 0);
	}
	
	public Vec2d(Vec2d v) {
		set(v.x, v.y);
	}
	
	public Vec2d(Vec2i v) {
		set(v.x, v.y);
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2d add(Vec2d v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vec2d subtract(Vec2d v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vec2d scale(double factor) {
		return new Vec2d(x * factor, y * factor);
	}
	
	public Vec2d sub(Vec2d v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vec2d invert() {
		return new Vec2d(-x, -y);
	}
	
	public Vec2d normal() {
		return new Vec2d(y, -x);
	}
	
	public Vec2d normalise() {
		double magnitude = magnitude();
		if (magnitude == 0) return new Vec2d(0, 0);
		else return new Vec2d(x / magnitude, y / magnitude);
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	public double dot(Vec2d v2) {
		return (x * v2.x) + (y * v2.y);
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vec2d)) return false;
		Vec2d v = (Vec2d) o;
		if (v.x == x && v.y == y) return true;
		else return false;
	}
	
	public static double getDistance(Vec2d a, Vec2d b) {
		double dx = a.x - b.x;
		double dy = a.y - b.y;
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		return distance;
	}
	
	public void print() {
		System.out.print("(" + x + "," + y + ")");
	}

	public void println() {
		System.out.println("(" + x + "," + y + ")");
	}

}
