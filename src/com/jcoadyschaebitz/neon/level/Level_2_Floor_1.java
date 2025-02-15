package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity.Orientation2D;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Railing;
import com.jcoadyschaebitz.neon.sound.SoundClip;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Level_2_Floor_1 extends Level {

	public Level_2_Floor_1(String path, String levelName, long seed) {
		super(path, "/levels/overlays/level_2_floor_1.png", levelName, seed);
	}

	@Override
	protected void initTransition() {
		add(new LevelTransition(new Rect(20, 61, 3, 2), new Vec2i(51 * 16, 150 * 16), this, Level.level_2));
	}

	@Override
	protected void addMobs() {
	}

	@Override
	protected void addItems() {
		add(new Railing(19 * 16, 60 * 16, Orientation2D.VERTICAL, 1));
		add(new Railing(19 * 16, 61 * 16, Orientation2D.VERTICAL, 1));
		add(new Railing(19 * 16, 62 * 16, Orientation2D.VERTICAL, 1));
		add(new Railing(19 * 16, 63 * 16, Orientation2D.VERTICAL, 1));
	}

	@Override
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
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
		SoundClip.rain.pause();
	}

}
