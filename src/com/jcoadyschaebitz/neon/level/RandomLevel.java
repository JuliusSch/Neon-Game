package com.jcoadyschaebitz.neon.level;

import java.util.Random;

@SuppressWarnings("serial")
public class RandomLevel extends Level {
	
	private static final Random RANDOM = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	protected void generateLevel() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tilesInt[x + y * width] = RANDOM.nextInt(4);
			}
		}
	}

	protected void loadLevel(String path) {
		
	}
	
	protected void initTransition() {
		
	}

	@Override
	protected void addMobs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addItems() {
		// TODO Auto-generated method stub
		
	}
	 
}
