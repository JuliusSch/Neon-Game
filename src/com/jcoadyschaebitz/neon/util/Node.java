package com.jcoadyschaebitz.neon.util;

public class Node extends Vec2i {

	private Node link;
	private double heurCost, sumCost;
	public double costFromStart;
	
	public Node(Vec2i vec, Node link, double nCost, double hCost) {
		this(vec.x, vec.y, link, nCost, hCost);
	}
	
	public Node(int x, int y, Node link, double nCost, double hCost) {
		this.x = x;
		this.y = y;
		this.link = link;
		this.costFromStart = nCost;
		this.heurCost = hCost;
		this.sumCost = this.costFromStart + this.heurCost;
	}
	
	public Node add(Vec2i v) {
		return new Node(this.x + v.x, this.y + v.y, link, costFromStart, heurCost);
	}
	
	public Node subtract(Vec2i v) {
		return new Node(this.x - v.x, this.y - v.y, link, costFromStart, heurCost);
	}
	
	public Node getLink() {
		return link;
	}
	
	public double getCost() {
		return sumCost;
	}
	
}
