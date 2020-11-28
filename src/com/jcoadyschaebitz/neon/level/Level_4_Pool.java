package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.Item.DoorKey;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarTable;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity.Or_2D;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Divider_1;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Divider_2_Glass;
import com.jcoadyschaebitz.neon.entity.collisionEntities.KitchenUnit;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Planter_1;
import com.jcoadyschaebitz.neon.entity.collisionEntities.SlidingDoor;
import com.jcoadyschaebitz.neon.entity.collisionEntities.Window1x3;
import com.jcoadyschaebitz.neon.entity.collisionEntities.WireFence;
import com.jcoadyschaebitz.neon.entity.decorationEntities.OverheadFan;
import com.jcoadyschaebitz.neon.entity.mob.Soldier;
import com.jcoadyschaebitz.neon.entity.weapon.Pistol;
import com.jcoadyschaebitz.neon.entity.weapon.Shotgun;

@SuppressWarnings("serial")
public class Level_4_Pool extends Level {

	public Level_4_Pool(String path, long seed) {
		super(path, "/levels/overlays/lvl_4.png", seed);
		playerSpawn = new TileCoordinate(101, 154);
//		playerSpawn = new TileCoordinate(22, 237);
		playerSpawn = new TileCoordinate(22, 109);	// new intro area
	}

	@Override
	protected void initTransition() {
		
	}

	@Override
	protected void addMobs() {
		add(new Soldier(106, 158));
		add(new Soldier(108, 148));
		add(new Soldier(110, 144));
		add(new Soldier(106, 144));
		add(new Soldier(108, 148));
//		add(new PoolGangster(120, 145));
		
		//intro area
		add(new Soldier(23, 115));
	}

	@Override
	protected void addItems() {
		add(new WireFence(22 * 16, 102 * 16, Or_2D.HORIZONTAL));
		add(new WireFence(23 * 16, 102 * 16, Or_2D.HORIZONTAL));
		
		add(new Pistol(101, 156, 240));
		add(new Shotgun(24, 234, 60));
		
		add(new Window1x3(15 * 16, 240 * 16));
		add(new Window1x3(16 * 16, 240 * 16));
		add(new Window1x3(17 * 16, 240 * 16));
		add(new Window1x3(18 * 16, 240 * 16));
		add(new Window1x3(19 * 16, 240 * 16));
		add(new Window1x3(20 * 16, 240 * 16));
		add(new Window1x3(21 * 16, 240 * 16));

		add(new Window1x3(25 * 16, 240 * 16));
		add(new Window1x3(26 * 16, 240 * 16));
		add(new Window1x3(27 * 16, 240 * 16));
		add(new Window1x3(28 * 16, 240 * 16));
		add(new Window1x3(29 * 16, 240 * 16));
		add(new Window1x3(30 * 16, 240 * 16));
		add(new Window1x3(31 * 16, 240 * 16));
		
		add(new Divider_1(98 * 16 + 6, 148 * 16 + 2, Or_2D.HORIZONTAL));
		add(new Divider_1(101 * 16 + 10, 148 * 16 + 2, Or_2D.HORIZONTAL));
		
		add(new Divider_1(98 * 16 + 6, 146 * 16 - 2, Or_2D.HORIZONTAL));
		add(new Divider_1(101 * 16 + 10, 146 * 16 - 2, Or_2D.HORIZONTAL));
		
		add(new Divider_1(104 * 16 + 6, 149 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 150 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 151 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 152 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 153 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 154 * 16, Or_2D.VERTICAL));
		add(new Divider_1(104 * 16 + 6, 155 * 16, Or_2D.VERTICAL));
		
		add(new Planter_1(100 * 16 - 8, 166 * 16, Or_2D.HORIZONTAL));
		add(new Planter_1(105 * 16 - 8, 140 * 16, Or_2D.HORIZONTAL));
		add(new Planter_1(99 * 16 - 4, 175 * 16 + 8, Or_2D.VERTICAL));
		
		add(new Planter_1(98 * 16 - 4, 154 * 16, Or_2D.VERTICAL));
		add(new Planter_1(98 * 16 - 4, 149 * 16, Or_2D.VERTICAL));
		add(new Planter_1(98 * 16 - 4, 159 * 16, Or_2D.VERTICAL));
		
		add(new BarTable(84 * 16, 190 * 16));
		
		add(new OverheadFan(105 * 16, 150 * 16, 0.03));
		
		add(new Pistol(26, 115, 240));
		
		add(new KitchenUnit(50 * 16 - 2, 112 * 16, Or_2D.HORIZONTAL));
		add(new KitchenUnit(52 * 16 + 5, 112 * 16, Or_2D.HORIZONTAL));
		add(new KitchenUnit(55 * 16 - 4, 112 * 16, Or_2D.HORIZONTAL));
		add(new KitchenUnit(57 * 16 + 3, 112 * 16, Or_2D.HORIZONTAL));
		
		add(new KitchenUnit(47 * 16 - 2, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(49 * 16 + 5, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(52 * 16 - 4, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(54 * 16 + 3, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(59 * 16 - 1, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(62 * 16 - 8, 118 * 16 + 8, Or_2D.HORIZONTAL));
		add(new KitchenUnit(64 * 16 + 1, 118 * 16 + 8, Or_2D.HORIZONTAL));
		
		add(new KitchenUnit(65 * 16 - 10, 104 * 16 - 8, Or_2D.VERTICAL));
		add(new KitchenUnit(65 * 16 - 10, 106 * 16, Or_2D.VERTICAL));
		add(new KitchenUnit(65 * 16 - 10, 109 * 16 - 8, Or_2D.VERTICAL));
		add(new KitchenUnit(65 * 16 - 10, 111 * 16, Or_2D.VERTICAL));
		
		SlidingDoor door1 = new SlidingDoor(68 * 16 + 8, 110 * 16, false, true, Or_2D.HORIZONTAL);
		add(door1);
		add(new DoorKey(91 * 16, 112 * 16, door1));
		add(new Divider_2_Glass(35 * 16, 117 * 16));
		add(new Divider_2_Glass(36 * 16, 117 * 16));
		add(new Divider_2_Glass(37 * 16, 117 * 16));
		add(new Divider_2_Glass(38 * 16, 117 * 16));
		
//		add(new TallPlanter(21 * 16, 230 * 16));
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
