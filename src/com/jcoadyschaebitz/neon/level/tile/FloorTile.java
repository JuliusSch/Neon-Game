package com.jcoadyschaebitz.neon.level.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jcoadyschaebitz.neon.graphics.Sprite;

public class FloorTile extends Tile {
	
	public FloorTile(Sprite sprite, int colour, List<Renderer> renderers) {
		super(sprite, colour, 0, addRenderer(renderers, Tile.edgeShadows));
//		blocksSightline = false;
		blocksProjectiles = false;
		castsShadow = false;
	}
	
	public static List<Renderer> addRenderer(List<Renderer> renderers, Renderer renderer) {
		if (renderers != null) renderers.add(renderer);
		else renderers = new ArrayList<Renderer>(Arrays.<Renderer>asList(renderer));
		return renderers;
	}

}
