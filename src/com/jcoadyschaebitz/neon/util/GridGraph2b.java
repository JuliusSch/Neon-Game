package com.jcoadyschaebitz.neon.util;

import java.util.HashMap;
import java.util.Map;

public class GridGraph2b {

	private boolean[] nodes;
	private int width, height;

	public GridGraph2b(boolean[] nodes, int width, int height) {
		this.nodes = nodes;
		this.width = width;
		this.height = height;
	}

	public Map<Vec2i, Double> getNeighbours(Vec2i point) {
		Map<Vec2i, Double> neighbours = new HashMap<Vec2i, Double>();
		int x = point.X();
		int y = point.Y();
//		if (!nodes[x + width * y]) return neighbours;
//		else 
		for (int j = -1; j < 2; j++) {
			for (int i = -1; i < 2; i++) {
				if (i == 0 && j == 0) continue;
				if (x + i < 0 || x + i >= width || y + j < 0 || y + j >= height) continue;
				if (i * j != 0 && (!nodes[(x + i) + width * (y)] || !nodes[x + width * (y + j)])) continue;
				if (nodes[(x + i) + width * (y + j)]) neighbours.put(new Vec2i(x + i, y + j), Util.pythag(i, j));
			}
		}
		return neighbours;
	}

	public boolean[] getNodes() {
		return nodes;
	}

}
