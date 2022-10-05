package com.jcoadyschaebitz.neon.entity.decorationEntities;

import com.jcoadyschaebitz.neon.graphics.Sprite;

public class BackgroundDecoration extends Decoration {

	private int yOffset;
	
	public BackgroundDecoration(int x, int y, Sprite sprite) {
		this(x, y, sprite, false);
	}
	
	public BackgroundDecoration(int x, int y, Sprite sprite, boolean transparent) {
		super(x, y, sprite, transparent);
		yOffset = 0;
	}
	
	public BackgroundDecoration(int x, int y, Sprite sprite, boolean transparent, int yOffset) {
		this(x, y, sprite, transparent);
		this.yOffset = yOffset;
	}
	
	public int getYAnchor() {
		return (int) y + yOffset;
	}

}
