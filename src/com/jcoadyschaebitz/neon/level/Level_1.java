package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.Scene_0_Train;
import com.jcoadyschaebitz.neon.entity.Item.HealthKit;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Bin;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity.Orientation2D;
import com.jcoadyschaebitz.neon.entity.collisionEntities.ConcreteBarrier;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CrowdBarrier;
import com.jcoadyschaebitz.neon.entity.collisionEntities.ParkedCar;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Shop;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Stall;
import com.jcoadyschaebitz.neon.entity.collisionEntities.WireFence;
import com.jcoadyschaebitz.neon.entity.collisionEntities.XPTube;
import com.jcoadyschaebitz.neon.entity.decorationEntities.AnimatedDecoration;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Vent;
import com.jcoadyschaebitz.neon.entity.mob.Heavy;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.entity.mob.OpeningTrain;
import com.jcoadyschaebitz.neon.entity.mob.SlowProjectileEnemy;
import com.jcoadyschaebitz.neon.entity.mob.Soldier;
import com.jcoadyschaebitz.neon.entity.spawner.RainSpawner;
import com.jcoadyschaebitz.neon.entity.weapon.AssaultRifle;
import com.jcoadyschaebitz.neon.entity.weapon.Shotgun;
import com.jcoadyschaebitz.neon.graphics.AnimatedSprite;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.graphics.Spritesheet;
import com.jcoadyschaebitz.neon.sound.SoundClip;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Level_1 extends Level {

	private int x, y;
	
	public Level_1(String path, String levelName, long seed) {
		super(path, "/levels/overlays/lvl_1.png", levelName, seed);
		playerSpawn = new TileCoordinate(x - 16, y + 136);
//		this.playerSpawn = new TileCoordinate(91, 113);			//spawn halfway through
//		this.playerSpawn = new TileCoordinate(94, 15);			//spawn at end		
	}

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
		x = 64;
		addMobs();
		addItems();
		add(new RainSpawner(0, 0, 0, this));
		SoundClip.rain.loop();
		SoundClip.rain.pause();
	}

	protected void initTransition() {
		add(new LevelTransition(new Rect(28 + x, 117 + y, 1, 0), new Vec2i(19 * 16, 31 * 16), this, Level.level_1_bar));
		add(new LevelTransition(new Rect(108 + x, 14 + y, 0, 3), new Vec2i(11 * 16, 117 * 16), this, Level.level_2));
	}

	protected void addMobs() {
		Mob[] sceneActors = new Mob[1];
		sceneActors[0] = player;
		
//		add(new Soldier(16 + x, 116 + y));
//		add(new Soldier(10 + x, 96 + y));
		add(new Soldier(7 + x, 88 + y));
		Mob s1 = new Soldier(10 + x, 87 + y);
		s1.setOrientation(Orientation.LEFT);
		add(s1);

		add(new Soldier(21 + x, 82 + y));
		add(new Soldier(6 + x, 73 + y));
		add(new Soldier(5 + x, 77 + y));
		add(new Soldier(4 + x, 74 + y));
		

//		add(new MeleeEnemy(43 + x, 85 + y));
		add(new Soldier(42 + x, 83 + y));

//		add(new MeleeEnemy(53 + x, 100 + y));
//		add(new MeleeEnemy(53 + x, 103 + y));
//		add(new Soldier(58 + x, 100 + y));
//		add(new Soldier(58 + x, 103 + y));
//		add(new Soldier(61 + x, 100 + y));
//		add(new Soldier(61 + x, 103 + y));
//		add(new Soldier(64 + x, 100 + y));
//		add(new Soldier(64 + x, 103 + y));

		add(new Heavy(85 + x, 127 + y));
		add(new Soldier(86 + x, 124 + y));
		add(new Soldier(81 + x, 130 + y));

		add(new Soldier(92 + x, 86 + y));
		add(new Soldier(98 + x, 88 + y));
//		add(new MeleeEnemy(99 + x, 96 + y));
		add(new Soldier(96 + x, 93 + y));
		add(new Soldier(98 + x, 95 + y));

		add(new SlowProjectileEnemy(104 + x, 72 + y));
		add(new Soldier(100 + x, 70 + y));
		add(new Soldier(100 + x, 74 + y));
//		add(new MeleeEnemy(102 + x, 70 + y));
//		add(new MeleeEnemy(102 + x, 74 + y));
//		add(new MeleeEnemy(101 + x, 72 + y));

		add(new SlowProjectileEnemy(96 + x, 58 + y));
		add(new SlowProjectileEnemy(109 + x, 48 + y));
		add(new Soldier(101 + x, 48 + y));
		add(new Soldier(104 + x, 48 + y));
		add(new Soldier(101 + x, 58 + y));
		add(new Soldier(104 + x, 58 + y));
		add(new Soldier(96 + x, 52 + y));
		add(new Soldier(96 + x, 54 + y));
//		add(new MeleeEnemy(90 + x, 51 + y));
//		add(new MeleeEnemy(90 + x, 53 + y));
//		add(new MeleeEnemy(87 + x, 52 + y));
//		add(new MeleeEnemy(105 + x, 35 + y));
//		add(new MeleeEnemy(108 + x, 37 + y));

		add(new SlowProjectileEnemy(82 + x, 61 + y));
		add(new Soldier(80 + x, 59 + y));
		add(new Soldier(85 + x, 59 + y));

		add(new SlowProjectileEnemy(70 + x, 50 + y));
		add(new SlowProjectileEnemy(70 + x, 55 + y));
//		add(new MeleeEnemy(61 + x, 50 + y));
//		add(new MeleeEnemy(61 + x, 55 + y));
		add(new Soldier(46 + x, 48 + y));
		add(new Soldier(55 + x, 48 + y));
		add(new Soldier(47 + x, 35 + y));
		add(new Soldier(47 + x, 39 + y));
		add(new Soldier(49 + x, 37 + y));
//		add(new MeleeEnemy(42 + x, 39 + y));
//		add(new MeleeEnemy(51 + x, 31 + y));
		add(new SlowProjectileEnemy(57 + x, 40 + y));
		add(new Soldier(62 + x, 33 + y));
		add(new Soldier(62 + x, 35 + y));

		add(new Soldier(28 + x, 40 + y));
		add(new Soldier(28 + x, 45 + y));
		add(new Soldier(33 + x, 39 + y));
		add(new Soldier(37 + x, 34 + y));
//		add(new MeleeEnemy(27 + x, 32 + y));
		add(new SlowProjectileEnemy(24 + x, 27 + y));
//		add(new MeleeEnemy(13 + x, 27 + y));

//		add(new MeleeEnemy(51 + x, 19 + y));
		add(new Soldier(51 + x, 17 + y));

		add(new Soldier(78 + x, 13 + y));
		add(new Soldier(78 + x, 15 + y));
		add(new Soldier(78 + x, 18 + y));
//		add(new MeleeEnemy(84 + x, 14 + y));
//		add(new MeleeEnemy(84 + x, 17 + y));
		add(new SlowProjectileEnemy(74 + x, 12 + y));
		add(new SlowProjectileEnemy(74 + x, 19 + y));
		add(new Heavy(81 + x, 11 + y));
		add(new Heavy(81 + x, 19 + y));
		add(new Scene_0_Train(this, sceneActors, Game.getKeyboard()));
		add(new OpeningTrain(37, 135));
	}

	protected void addItems() {
//		add(new HealthCase((27 + x) * 16 - 7, (98 + y) * 16 - 2, Orientation.DOWN));
		add(new XPTube((55 + x) * 16, (83 + y) * 16));
		add(new XPTube((42 + x) * 16, (115 + y) * 16));
		add(new XPTube((66 + x) * 16, (97 + y) * 16));
		add(new XPTube((104 + x) * 16, (32 + y) * 16));
		add(new XPTube((109 + x) * 16, (32 + y) * 16));
		add(new XPTube((99 + x) * 16, (54 + y) * 16));

		add(new HealthKit((13 + x) * 16, (22 + y) * 16));
		add(new XPTube((9 + x) * 16, (19 + y) * 16));
		add(new XPTube((8 + x) * 16, (20 + y) * 16));
		add(new XPTube((8 + x) * 16, (21 + y) * 16));

		add(new HealthKit((81 + x) * 16, (6 + y) * 16));
		add(new HealthKit((81 + x) * 16, (25 + y) * 16));
		add(new HealthKit((101 + x) * 16, (14 + y) * 16));

//		add(new Shotgun(19 + x, 100 + y, 48));//beginning
//		add(new Crossbow(18 + x, 100 + y, 48));

//		add(new Pistol(97 + x, 15 + y, 180));
//		add(new Shotgun(98 + x, 15 + y, 48));// end
//		add(new AssaultRifle(99 + x, 15 + y, 240));
//		add(new Crossbow(99 + x, 17 + y, 48));

//		add(new Pistol(20 + x, 102 + y, 180));// normal
		add(new Shotgun(104 + x, 107 + y, 48));
		add(new AssaultRifle(51 + x, 31 + y, 240));

		add(new HealthKit((67 + x) * 16, (97 + y) * 16));
		add(new HealthKit((42 + x) * 16, (116 + y) * 16));
		add(new HealthKit((87 + x) * 16, (109 + y) * 16));
		add(new HealthKit((64 + x) * 16, (34 + y) * 16));

		add(new Bin((34 + x) * 16 + 12, (81 + y) * 16 + 2, Orientation.DOWN));
		add(new Bin((37 + x) * 16 + 12, (81 + y) * 16 + 2, Orientation.DOWN));
		add(new Bin((1 + x) * 16 + 12, (81 + y) * 16 + 2, Orientation.RIGHT));
		add(new Bin((16 + x) * 16 + 12, (81 + y) * 16 + 2, Orientation.DOWN));
//		add(new Bin((66 + x) * 16 + 4, (95 + y) * 16 + 2, Orientation.DOWN));
		
		add(new Bin((80 + x) * 16 + 4, (115 + y) * 16 + 2, Orientation.DOWN));
		add(new Bin((85 + x) * 16 + 2, (118 + y) * 16 + 2, Orientation.DOWN));
		add(new ParkedCar((85 + x) * 16, (116 + y) * 16, Orientation.LEFT));
		
		add(new Bin((102 + x) * 16 + 4, (103 + y) * 16 + 2, Orientation.DOWN));
//		add(new Bin((53 + x) * 16, (81 + y) * 16 + 2, Orientation.DOWN));
		add(new Bin((96 + x) * 16, (85 + y) * 16 + 2, Orientation.DOWN));
		add(new Bin((101 + x) * 16 + 2, (68 + y) * 16 + 2, Orientation.DOWN));
//		add(new Bin((48 + x) * 16 - 7, (89 + y) * 16, Orientation.LEFT));
//		add(new Bin((48 + x) * 16 - 7, (92 + y) * 16, Orientation.LEFT));
		add(new Bin((111 + x) * 16 - 7, (66 + y) * 16, Orientation.LEFT));
		add(new Bin((91 + x) * 16 - 8, (80 + y) * 16 - 8, Orientation.RIGHT));
//		add(new Bin((41 + x) * 16 - 1, (97 + y) * 16, Orientation.RIGHT));
//		add(new Bin((41 + x) * 16 - 1, (102 + y) * 16, Orientation.RIGHT));
		add(new Bin((35 + x) * 16 - 1, (85 + y) * 16, Orientation.RIGHT));
		
		add(new Bin((23 + x) * 16, (118 + y) * 16, Orientation.DOWN));	//bar alley bin

		add(new Vent((105 + x) * 16, (100 + y) * 16, 0.02));
		add(new AnimatedDecoration((18 + x) * 16 + 4, (78 + y) * 16, 0.5, new AnimatedSprite(Spritesheet.ventSteam, 32, 32, 6, 10)));
		add(new AnimatedDecoration((20 + x) * 16 + 4, (117 + y) * 16, 0.5, new AnimatedSprite(Spritesheet.ventSteam, 32, 32, 6, 10)));

		add(new ParkedCar((5 + x) * 16, (92 + y) * 16, Orientation.DOWN));
		add(new ParkedCar((11 + x) * 16, (107 + y) * 16, Orientation.DOWN));
//		add(new Stall(((11 + x) * 16) - 8, (84 + y) * 16, Orientation.DOWN, 1));
//		add(new Stall((4 + x) * 16, (84 + y) * 16, Orientation.DOWN, 1));
		add(new ConcreteBarrier((10 + x) * 16, (85 + y) * 16, Orientation2D.HORIZONTAL));
		add(new ConcreteBarrier((5 + x) * 16, (85 + y) * 16, Orientation2D.HORIZONTAL));
		add(new Stall((5 + x) * 16, (111 + y) * 16, Orientation.LEFT, 1));
		add(new WireFence((5 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
		add(new WireFence((6 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((7 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((8 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((9 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((10 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((11 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
		add(new WireFence((12 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));
		add(new WireFence((13 + x) * 16, (119 + y) * 16, Orientation2D.HORIZONTAL));

//		add(new WireFence((66 + x) * 16, (93 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((67 + x) * 16, (93 + y) * 16, Orientation2D.HORIZONTAL));
//		add(new WireFence((68 + x) * 16, (93 + y) * 16, Orientation2D.HORIZONTAL));

		add(new WireFence((90 + x) * 16, (78 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((90 + x) * 16, (79 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((90 + x) * 16, (80 + y) * 16, Orientation2D.VERTICAL));

		add(new WireFence((49 + x) * 16, (84 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((49 + x) * 16, (85 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((49 + x) * 16, (86 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((49 + x) * 16, (87 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((49 + x) * 16, (88 + y) * 16, Orientation2D.VERTICAL));
		
		add(new WireFence((31 + x) * 16, (117 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((31 + x) * 16, (118 + y) * 16, Orientation2D.VERTICAL));	//bar alley fence

		add(new Decoration((13 + x) * 16, (89 + y) * 16, Sprite.canopyRight));
		add(new Decoration((5 + x) * 16, (99 + y) * 16, Sprite.canopy2Left));
		add(new Decoration((13 + x) * 16, (103 + y) * 16, Sprite.silhouetteRight1));
		add(new Decoration((13 + x) * 16, (95 + y) * 16, Sprite.silhouetteRight2));
		add(new Decoration((11 + x) * 16, (110 + y) * 16 + 8, Sprite.signRight1));
//		add(new Decoration((5 + x) * 16, (100 + y) * 16, Sprite.doorGlowLeft));
		add(new Decoration((5 + x) * 16 - 5, (108 + y) * 16, Sprite.signLeft1));
		Decoration d1 = new Decoration((11 + x) * 16, (88 + y) * 16, Sprite.signRight2);
		d1.addTranslucentSprite(Sprite.signRight2Glow, 0.1, 0, 0);
		add(d1);
		add(new Decoration((5 + x) * 16, (94 + y) * 16, Sprite.cable1));
		add(new Decoration((5 + x) * 16, (86 + y) * 16, Sprite.cable2));
		add(new Decoration((5 + x) * 16, (86 + y) * 16, Sprite.cable3));
		add(new Decoration((5 + x) * 16, (108 + y) * 16, Sprite.cable2));
		
		add(new ParkedCar((84 + x) * 16, (12 + y) * 16, Orientation.LEFT));
		add(new Bin((77 + x) * 16 - 7, (73 + y) * 16, Orientation.LEFT));
		add(new Bin((77 + x) * 16 - 7, (75 + y) * 16, Orientation.LEFT));
		
		add(new CrowdBarrier((104 + x) * 16, (118 + y) * 16, Orientation2D.VERTICAL));
		add(new CrowdBarrier((104 + x) * 16 - 2, (120 + y) * 16, Orientation2D.VERTICAL));
		add(new CrowdBarrier((104 + x) * 16 - 4, (122 + y) * 16, Orientation2D.VERTICAL));
		
		add(new WireFence((1 + x) * 16, (80 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((1 + x) * 16, (81 + y) * 16, Orientation2D.VERTICAL));
		
		add(new WireFence((32 + x) * 16, (80 + y) * 16, Orientation2D.VERTICAL));
		add(new WireFence((32 + x) * 16, (81 + y) * 16, Orientation2D.VERTICAL));
		
		add(new Shop((109 + x) * 16, (109 + y) * 16));
	}

}
