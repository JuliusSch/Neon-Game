package com.jcoadyschaebitz.neon.level;

import com.jcoadyschaebitz.neon.util.Vec2i;

public class Node {

	public Vec2i tile;
	public Node parent;
	public double fCost, gCost, hCost;
	
	public Node (Vec2i v, Node n, double g, double h) {
		tile = v;
		parent = n;
		gCost = g;
		hCost = h;
		fCost = g + h;
	}
	
}
