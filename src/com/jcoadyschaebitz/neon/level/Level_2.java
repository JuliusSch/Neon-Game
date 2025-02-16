package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.collisionEntities.Bin;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity.Orientation2D;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Railing;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Stall;
import com.jcoadyschaebitz.neon.entity.collisionEntities.WireFence;
import com.jcoadyschaebitz.neon.entity.decorationEntities.BackgroundDecoration;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Vent;
import com.jcoadyschaebitz.neon.entity.mob.EliteSoldier;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.entity.mob.SlowProjectileEnemy;
import com.jcoadyschaebitz.neon.entity.mob.Soldier;
import com.jcoadyschaebitz.neon.entity.spawner.RainSpawner;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.sound.SoundClip;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Level_2 extends Level {

	public Level_2(String path, String levelName, long seed) {
		super(path, "/levels/overlays/lvl_2.png", levelName, seed);
		playerSpawn = new TileCoordinate(11, 117);
	}

	public void loadLevel(String path) {
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
		add(new RainSpawner(0, 0, 0, this));
		SoundClip.rain.loop();
		SoundClip.rain.pause();
	}

	protected void addMobs() {
//		add(new SpearEnemy(34, 121));
//		add(new SpearEnemy(121, 96));
		add(new Soldier(122, 100));
		add(new SlowProjectileEnemy(125, 96));
		add(new Soldier(86, 111));
		add(new Soldier(86, 114));
		
		add(new EliteSoldier(35, 122));
	}

	protected void initTransition() {
		add(new LevelTransition(new Rect(5, 115, 0, 4), new Vec2i(170 * 16, 13 * 16), this, Level.level_1));
		add(new LevelTransition(new Rect(51, 147, 3, 2), new Vec2i(20 * 16, 58 * 16), this, Level.level_2_floor_1));
	}

	protected void addItems() {
//		add(new Crossbow(24, 125, 24));	//Testing
		
		add(new Stall(112 * 16, 108 * 16, Orientation.DOWN, 1));
		add(new WireFence(28 * 16 - 7, 119 * 16, Orientation2D.VERTICAL));
		add(new WireFence(28 * 16 - 7, 120 * 16, Orientation2D.VERTICAL));
		add(new WireFence(28 * 16 - 7, 121 * 16, Orientation2D.VERTICAL));
		add(new WireFence(28 * 16 - 7, 122 * 16, Orientation2D.VERTICAL));
		add(new WireFence(28 * 16 - 7, 123 * 16, Orientation2D.VERTICAL));
		add(new Bin(24 * 16 - 12, 118 * 16, Orientation.DOWN));
		add(new Bin(41 * 16 - 7, 126 * 16, Orientation.LEFT));
		add(new Bin(41 * 16 - 7, 128 * 16, Orientation.LEFT));
		
//		add(new BarStool(54 * 16, 126 * 16));
//		add(new BarStool(56 * 16, 126 * 16));
//		add(new BarTable(52 * 16, 230 * 16));
		
//		add(new WireFence(45 * 16, 109 * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence(46 * 16, 109 * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence(47 * 16, 109 * 16, Orientation2D.HORIZONTAL));
		
		add(new WireFence(29 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(30 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(31 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(32 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(33 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(34 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(35 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(36 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(37 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(38 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(39 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(40 * 16, 132 * 16, Orientation2D.HORIZONTAL));
		add(new WireFence(41 * 16, 132 * 16, Orientation2D.HORIZONTAL));

//		add(new HealthKit(63 * 16, 134 * 16));
		add(new BackgroundDecoration(30 * 16, 117 * 16 - 7, Sprite.bigShopfront2, false, Sprite.bigShopfront2.getHeight()));
		add(new Vent(101 * 16, 135 * 16, 0.04));
		add(new Vent(105 * 16, 135 * 16, 0.07));
//		add(new CrowdBarrier(82 * 16, 111 * 16, Orientation2D.VERTICAL));
//		add(new CrowdBarrier(85 * 16 + 1, 117 * 16, Orientation2D.HORIZONTAL));
//		add(new CrowdBarrier(88 * 16 - 1, 117 * 16, Orientation2D.HORIZONTAL));
		add(new Railing(50 * 16, 148 * 16, Orientation2D.VERTICAL, 1));
		add(new Railing(50 * 16, 149 * 16, Orientation2D.VERTICAL, 1));
		
		add(new Railing(86 * 16, 132 * 16, Orientation2D.VERTICAL, 2));
		add(new Railing(86 * 16, 133 * 16, Orientation2D.VERTICAL, 2));
		add(new Railing(86 * 16, 134 * 16, Orientation2D.VERTICAL, 2));
		add(new Railing(86 * 16, 135 * 16, Orientation2D.VERTICAL, 2));
		add(new Railing(86 * 16, 136 * 16, Orientation2D.VERTICAL, 2));
		add(new Railing(86 * 16, 137 * 16, Orientation2D.VERTICAL, 2));
	}
}
