package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.Game;
import com.jcoadyschaebitz.neon.cutscene.Scene_1_Bar;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarCounter;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarDivider;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarStool;
import com.jcoadyschaebitz.neon.entity.collisionEntities.BarTable;
import com.jcoadyschaebitz.neon.entity.collisionEntities.HealthCase;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Mob.Orientation;
import com.jcoadyschaebitz.neon.entity.mob.Soldier;
import com.jcoadyschaebitz.neon.entity.weapon.Pistol;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.util.Rect;
import com.jcoadyschaebitz.neon.util.Vec2i;

public class Level_1_Bar extends Level {

	public Level_1_Bar(String path, String levelName, long seed) {
		super(path, "/levels/overlays/lvl_1_bar.png", levelName, seed);
		playerSpawn = new TileCoordinate(17, 12);
	}

	@Override
	protected void initTransition() {
		add(new LevelTransition(new Rect(19, 32, 1, 1), new Vec2i(28 * 16, 118 * 16), this, Level.level_1));
		add(new LevelTransition(new Rect(6, 22, 1, 0), new Vec2i(27 * 16, 15 * 16), this, Level.level_0_menu));
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
		//play soundclip: muted rain
	}
	
	@Override
	protected void addMobs() {
		Mob[] members = new Mob[2];
		Soldier s1 = new Soldier(11, 15);
		add(s1);
		members[0] = s1;
		Soldier s2 = new Soldier(12, 15);
		add(s2);
		members[1] = s2;
		add(new Scene_1_Bar(this, members, Game.getUIManager().getGame().getKeyboard()));
	}

	@Override
	protected void addItems() {
		add(new Pistol(11, 13, 180));// normal			-9, -89 difference from level_1
		add(new HealthCase(19 * 16 - 7, 9 * 16 + 1, Orientation.DOWN));
		
		add(new BarCounter(9 * 16, 9 * 16));
		
		add(new BarStool(9 * 16 + 4, 12 * 16 + 3));
		add(new BarStool(11 * 16 + 4, 12 * 16 + 4));
		add(new BarStool(14 * 16 - 3, 12 * 16 + 6));
		add(new BarStool(15 * 16 + 4, 12 * 16 + 4));
		add(new BarStool(17 * 16 + 8, 10 * 16 + 6));
		
		add(new BarDivider(12 * 16, 20 * 16 - 1));
		add(new BarDivider(16 * 16 + 8, 20 * 16 - 1));
		
		add(new BarTable(23 * 16 + 3, 7 * 16));
		add(new BarTable(20 * 16, 5 * 16 - 5));
		add(new BarTable(9 * 16, 13 * 16 - 5));
		add(new BarTable(14 * 16, 13 * 16));
		add(new BarTable(15 * 16, 16 * 16));
		add(new BarTable(10 * 16, 16 * 16 + 5));
		
		add(new BarStool(23 * 16 + 4, 7 * 16 + 2));
		add(new BarStool(25 * 16 + 1, 7 * 16 + 4));
		add(new BarStool(25 * 16 - 6, 9 * 16 + 7));
		add(new BarStool(20 * 16 - 1, 7 * 16));
		add(new BarStool(22 * 16 + 8, 5 * 16 + 8));
		
		add(new Decoration(6 * 16, 12 * 16, Sprite.cable4, true));
//		add(new Decoration(12 * 16, 10 * 16, Sprite.barman));
//		add(new InteriorGuardRail(10 * 16, 17 * 16 + 1, Orientation.RIGHT));
//		add(new InteriorGuardRail(10 * 16, 18 * 16, Orientation.RIGHT));
	}
}
