package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.collisionEntities.Stall;
import com.jcoadyschaebitz.neon.entity.mob.FastMelee;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.entity.spawner.RainSpawner;
import com.jcoadyschaebitz.neon.sound.SoundClip;

@SuppressWarnings("serial")
public class Level2 extends Level {

	public Level2(String path, long seed) {
		super(path, seed);
		playerSpawn = new TileCoordinate(11, 117);
	}
	
	public void loadLevel(String path) {
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
		addMobs();
		addItems();
		add (new RainSpawner(0, 0, 0, this));
		SoundClip.rain.loop();
	}
	
	protected void addMobs() {
		add(new FastMelee(34, 121));
	}

	protected void initTransition() {
	}

	protected void addItems() {
		add(new Stall(112 * 16, 108 * 16, Direction.DOWN, 1));
	}
}
