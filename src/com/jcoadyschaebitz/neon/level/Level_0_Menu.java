package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.decorationEntities.BackgroundDecoration;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Level_0_Menu extends Level {

	public Level_0_Menu(String path, String levelName, long seed) {
		super(path, "/levels/overlays/lvl_0.png", levelName, seed);
		playerSpawn = new TileCoordinate(16, 17);
	}

	@Override
	protected void initTransition() {
		add(new LevelTransition(new Rect(27, 16, 2, 0), new Vec2i(6 * 16, 23 * 16), this, Level.level_1_bar));
	}

	@Override
	protected void addMobs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addItems() {
		add(new BackgroundDecoration(18 * 16 + 8, 7 * 16 + 11, Sprite.windowFloorReflection, true, -24));
		add(new BackgroundDecoration(18 * 16 + 8, 8 * 16 + 11, Sprite.windowFloorReflection, true, -24));
		add(new BackgroundDecoration(18 * 16 + 8, 9 * 16 + 11, Sprite.windowFloorReflection, true, -24));
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
	}
}
