package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.XPTube;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarCounter;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarDivider;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Bin;
import com.jcoadyschaebitz.neon.entity.collisionEntities.ParkedCar;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Stall;
import com.jcoadyschaebitz.neon.entity.collisionEntities.WireFence;
import com.jcoadyschaebitz.neon.entity.decorationEntities.AnimatedDecoration;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.mob.Heavy;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Direction;
import com.jcoadyschaebitz.neon.entity.mob.SlowProjectileEnemy;
import com.jcoadyschaebitz.neon.entity.mob.Soldier;
import com.jcoadyschaebitz.neon.entity.mob.SwordEnemy;
import com.jcoadyschaebitz.neon.entity.spawner.RainSpawner;
import com.jcoadyschaebitz.neon.entity.weapon.Pistol;
import com.jcoadyschaebitz.neon.entity.weapon.Shotgun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;

@SuppressWarnings("serial")
public class Level1 extends Level {

	public Level1(String path, long seed) {
		super(path, seed);
		playerSpawn = new TileCoordinate(23, 100);			//spawn at beginning	
//		playerSpawn = new TileCoordinate(91, 113);			//spawn halfway through
//		playerSpawn = new TileCoordinate(94, 15);			//spawn at end
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
		addMobs();
		addItems();
		addSubArea(new LevelSubArea(16, 92, 33, 109));
		add(new RainSpawner(0, 0, 0, this));
		SoundClip.rain.loop();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Decoration) {
				System.out.println(entities.get(i));
			}
		}
	}
	
	protected void initTransition() {
		addTransition(new LevelTransition(105, 14, 105, 17, this, Level.level2));		
	}
	
	protected void addMobs() {
		add(new Soldier(16, 111));
		add(new Soldier(10, 96));

		add(new Soldier(21, 83));
		add(new Soldier(23, 84));
		add(new Soldier(26, 84));
		
		add(new SwordEnemy(43, 85));
		add(new Soldier(42, 83));

		add(new SwordEnemy(53, 100));
		add(new SwordEnemy(53, 103));
		add(new Soldier(58, 100));
		add(new Soldier(58, 103));
		add(new Soldier(61, 100));
		add(new Soldier(61, 103));
		add(new Soldier(64, 100));
		add(new Soldier(64, 103));

		add(new Heavy(75, 118));
		
		add(new Soldier(92, 88));
		add(new Soldier(98, 88));
		add(new SwordEnemy(99, 96));
		add(new Soldier(96, 95));
		add(new Soldier(98, 95));
		
		add(new SlowProjectileEnemy(104, 72));
		add(new Soldier(100, 70));
		add(new Soldier(100, 74));
		add(new SwordEnemy(102, 70));
		add(new SwordEnemy(102, 74));
		add(new SwordEnemy(101, 72));
		
		add(new SlowProjectileEnemy(96, 58));
		add(new SlowProjectileEnemy(109, 48));
		add(new Soldier(101, 48));
		add(new Soldier(104, 48));
		add(new Soldier(101, 58));
		add(new Soldier(104, 58));
		add(new Soldier(96, 52));
		add(new Soldier(96, 54));
		add(new SwordEnemy(90, 51));
		add(new SwordEnemy(90, 53));
		add(new SwordEnemy(87, 52));
		add(new SwordEnemy(105, 35));
		add(new SwordEnemy(108, 37));
		
		add(new SlowProjectileEnemy(82, 61));
		add(new Soldier(80, 59));
		add(new Soldier(85, 59));
		
		add(new SlowProjectileEnemy(70, 50));
		add(new SlowProjectileEnemy(70, 55));
		add(new SwordEnemy(61, 50));
		add(new SwordEnemy(61, 55));
		add(new Soldier(46, 48));
		add(new Soldier(55, 48));
		add(new Soldier(47, 35));
		add(new Soldier(47, 39));
		add(new Soldier(49, 37));
		add(new SwordEnemy(42, 39));
		add(new SwordEnemy(51, 31));
		add(new SlowProjectileEnemy(57, 40));
		add(new Soldier(62, 33));
		add(new Soldier(62, 35));
		
		add(new Soldier(28, 40));
		add(new Soldier(28, 45));
		add(new Soldier(33, 39));
		add(new Soldier(37, 34));
		add(new SwordEnemy(27, 32));
		add(new SlowProjectileEnemy(24, 27));
		add(new SwordEnemy(13, 27));
		
		add(new SwordEnemy(51, 19));
		add(new Soldier(51, 17));
		
		add(new Soldier(78, 13));
		add(new Soldier(78, 15));
		add(new Soldier(78, 18));
		add(new SwordEnemy(84, 14));
		add(new SwordEnemy(84, 17));
		add(new SlowProjectileEnemy(74, 12));
		add(new SlowProjectileEnemy(74, 19));
		add(new Heavy(81, 11));
		add(new Heavy(81, 19));
	}
	
	protected void addItems() {
		add(new XPTube(55 * 16, 83 * 16));
		add(new XPTube(42 * 16, 115 * 16));
		add(new XPTube(66 * 16, 97 * 16));
		add(new XPTube(104 * 16, 32 * 16));
		add(new XPTube(109 * 16, 32 * 16));
		add(new XPTube(99 * 16, 54 * 16));
		
		add(new HealthKit(13 * 16, 22 * 16));
		add(new XPTube(9 * 16, 19 * 16));
		add(new XPTube(8 * 16, 20 * 16));
		add(new XPTube(8 * 16, 21 * 16));
		
		add(new HealthKit(81 * 16, 6 * 16));
		add(new HealthKit(81 * 16, 25 * 16));
		add(new HealthKit(101 * 16, 14 * 16));

//		add(new Shotgun(19, 100, 48));//beginning
//		add(new Crossbow(18, 100, 48));
		
		add(new Pistol(97, 15, 48));
		add(new Shotgun(98, 15, 48));//end
		
		add(new Pistol(20, 100, 180));//normal
		add(new Shotgun(88, 107, 48));
		
		add(new HealthKit(67 * 16, 97 * 16));
		add(new HealthKit(42 * 16, 116 * 16));
		add(new HealthKit(87 * 16, 109 * 16));
		add(new HealthKit(64 * 16, 34 * 16));
		
		add(new Bin(34 * 16 + 12, 81 * 16 + 2, Direction.DOWN));
		add(new Bin(37 * 16 + 12, 81 * 16 + 2, Direction.DOWN));
		add(new Bin(7 * 16 + 12, 81 * 16 + 2, Direction.DOWN));
		add(new Bin(16 * 16 + 12, 81 * 16 + 2, Direction.DOWN));
		add(new Bin(66 * 16 + 4, 95 * 16 + 2, Direction.DOWN));
		add(new Bin(70 * 16 + 4, 111 * 16 + 2, Direction.DOWN));
		add(new Bin(75 * 16 + 2, 114 * 16 + 2, Direction.DOWN));
		add(new Bin(86 * 16 + 4, 103 * 16 + 2, Direction.DOWN));
		add(new Bin(53 * 16, 81 * 16 + 2, Direction.DOWN));
		add(new Bin(96 * 16, 85 * 16 + 2, Direction.DOWN));
		add(new Bin(101 * 16 + 2, 68 * 16 + 2, Direction.DOWN));
		add(new Bin(48 * 16 - 7, 89 * 16, Direction.LEFT));
		add(new Bin(48 * 16 - 7, 92 * 16, Direction.LEFT));
		add(new Bin(111 * 16 - 7, 66 * 16, Direction.LEFT));
		add(new Bin(91 * 16 - 1, 91 * 16, Direction.RIGHT));
		add(new Bin(41 * 16 - 1, 97 * 16, Direction.RIGHT));
		add(new Bin(41 * 16 - 1, 102 * 16, Direction.RIGHT));
		add(new Bin(36 * 16 - 1, 86 * 16, Direction.RIGHT));
		
		add(new AnimatedDecoration(89 * 16, 100 * 16, 1, new AnimatedSprite(Spritesheet.bigVent, 48, 48, 3, 10)));
		add(new AnimatedDecoration(13 * 16 + 4, 108 * 16, 0.2, new AnimatedSprite(Spritesheet.ventSteam, 32, 32, 6, 10)));
		add(new AnimatedDecoration(15 * 16 + 4, 109 * 16, 0.2, new AnimatedSprite(Spritesheet.ventSteam, 32, 32, 6, 10)));
		
		add(new ParkedCar(5 * 16, 92 * 16, Direction.DOWN));
		add(new ParkedCar(5 * 16, 104 * 16, Direction.DOWN));
		add(new ParkedCar(11 * 16, 107 * 16, Direction.DOWN));
		add(new Stall((10 * 16) - 8, 100 * 16, Direction.DOWN, 1));
		add(new Stall(5 * 16, 96 * 16, Direction.DOWN, 1));
		add(new WireFence(5 * 16, 115 * 16));
		add(new WireFence(6 * 16, 115 * 16));
		add(new WireFence(7 * 16, 115 * 16));
		add(new WireFence(8 * 16, 115 * 16));
		add(new WireFence(9 * 16, 115 * 16));
		add(new WireFence(10 * 16, 115 * 16));	
		add(new WireFence(11 * 16, 115 * 16));
		add(new WireFence(12 * 16, 115 * 16));
		add(new WireFence(13 * 16, 115 * 16));
		add(new WireFence(66 * 16, 93 * 16));
		add(new WireFence(67 * 16, 93 * 16));
		add(new WireFence(68 * 16, 93 * 16));
		add(new Decoration(13 * 16, 92 * 16, Sprite.canopy_right));
		add(new Decoration(18 * 16, 94 * 16 + 3, Sprite.barWall));
		add(new BarCounter(18 * 16, 96 * 16));
		add(new Decoration(18 * 16, 99 * 16, Sprite.barStool));
		add(new Decoration(20 * 16, 99 * 16, Sprite.barStool));
		add(new Decoration(24 * 16, 99 * 16, Sprite.barStool));
		add(new Decoration(26 * 16, 98 * 16, Sprite.barStool));
		add(new BarDivider(18 * 16 + 4, 101 * 16));
		add(new BarDivider(22 * 16, 101 * 16));		
	}

}
