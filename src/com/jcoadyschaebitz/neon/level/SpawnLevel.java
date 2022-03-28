package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.spawner.RainSpawner;
import com.jcoadyschaebitz.neon.entity.weapon.Shotgun;

public class SpawnLevel extends Level {

	public SpawnLevel(String path, String levelName, long seed) {
		super(path, "/levels/overlays/lvl_4.png", levelName, seed);
		playerSpawn = new TileCoordinate(5, 5);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tileCols = new int[w * h];
			image.getRGB(0, 0, w, h, tileCols, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: Could not load level file.");
		}
//		for (int i = 0; i < 4; i++) {
//			add(new ShootingEnemy(4 + 2 * i, 6));
//		}
//		add(new ChaserEnemy(14, 12, 10, 13));
		add(new Shotgun(3, 12, 48));
		add(new RainSpawner(0, 0, 0, this));
	}
	
	protected void initTransition() {
		
	}

	protected void generateLevel() {
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
