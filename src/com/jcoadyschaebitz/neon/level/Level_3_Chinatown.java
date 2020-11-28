package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.mob.GangMember;
import com.jcoadyschaebitz.neon.entity.weapon.AssaultRifle;
import com.jcoadyschaebitz.neon.entity.weapon.Crossbow;
import com.jcoadyschaebitz.neon.entity.weapon.Pistol;
import com.jcoadyschaebitz.neon.entity.weapon.Shotgun;
import com.jcoadyschaebitz.neon.graphics.Sprite;

@SuppressWarnings("serial")
public class Level_3_Chinatown extends Level {

	public Level_3_Chinatown(String path, long seed) {
		super(path, "/levels/overlays/lvl_4.png", seed);
		playerSpawn = new TileCoordinate(107, 250);
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

	@Override
	protected void initTransition() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void addMobs() {
		add(new GangMember(94, 212));
		add(new GangMember(91, 212));
	}

	@Override
	protected void addItems() {
		add(new Pistol(107, 245, 180));
		add(new Shotgun(107, 245, 48));
		add(new AssaultRifle(107, 245, 240));
		add(new Crossbow(107, 245, 48));
		
		add(new Decoration(105 * 16, 232 * 16, Sprite.largeHangingSign));
		add(new Decoration(101 * 16, 225 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(101 * 16, 226 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(101 * 16, 227 * 16, Sprite.corrIronHorizEndRight));
		
		add(new Decoration(115 * 16, 224 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(115 * 16, 225 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(115 * 16, 226 * 16, Sprite.corrIronHorizEndRight));
		
		add(new Decoration(119 * 16, 219 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(119 * 16, 220 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(119 * 16, 221 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(119 * 16, 222 * 16, Sprite.corrIronHorizEndRight));
		add(new Decoration(119 * 16, 223 * 16, Sprite.corrIronHorizEndRight));
	}

}
