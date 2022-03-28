package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jcoadyschaebitz.neon.cutscene.CutScene;
import com.jcoadyschaebitz.neon.entity.CollisionBox;
import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity;
import com.jcoadyschaebitz.neon.entity.decorationEntities.BackgroundDecoration;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.particle.DebugParticle;
import com.jcoadyschaebitz.neon.entity.particle.LineParticle;
import com.jcoadyschaebitz.neon.entity.particle.Particle;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.BorderRenderer;
import com.jcoadyschaebitz.neon.level.tile.StairTile;
import com.jcoadyschaebitz.neon.level.tile.Tile;
import com.jcoadyschaebitz.neon.util.Util;
import com.jcoadyschaebitz.neon.util.Vec2d;
import com.jcoadyschaebitz.neon.util.Vec2i;

public abstract class Level {

	private final int transitionLength = 30;
	private int transitionDelay = 0;
	protected long seed;
	protected int width, height;
	public int[] ZMap;
	protected int[] tileCols;
	protected int[] shadowMap;
	protected int[] borderMap;
	protected int[] overlaysMap;
	protected boolean[] collisionMap, aiCollisionMap;
	protected final Sprite[] shadowSprites = { Sprite.tileBottomShadow1, Sprite.tileBottomShadow2, Sprite.tileRightShadowTop, Sprite.tileRightShadowBottom, Sprite.tileRightShadowMiddle, Sprite.tileLeftShadowTop, Sprite.tileLeftShadowBottom,
			Sprite.tileLeftShadowMiddle, Sprite.stairLeftShadowTop, Sprite.stairLeftShadowMiddle, Sprite.stairLeftShadowBottom, Sprite.stairRightShadowTop, Sprite.stairRightShadowMiddle, Sprite.stairRightShadowBottom,
			Sprite.tileLeftShadow5, Sprite.tileRightShadow5, Sprite.doubleSideShadowTop, Sprite.doubleSideShadowMiddle, Sprite.doubleSideShadowBottom };
	private final int MAX_SHAKE_OFFSET, MAX_RECOIL_OFFSET;
	private int shakeOffsetX, shakeOffsetY;
	private int recoilOffsetX, recoilOffsetY, xScroll, yScroll;
	private double screenTrauma, recoilStrength, recoilDir;
	protected Random random = new Random();
	protected TileCoordinate playerSpawn;
	protected Player player;
	public String levelName;

	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> tempAdd = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Decoration> decorations = new ArrayList<Decoration>();
	private List<LevelTransition> transitions = new ArrayList<LevelTransition>();
	private List<LevelSubArea> subAreas = new ArrayList<LevelSubArea>();
	private List<CutScene> cutScenes = new ArrayList<CutScene>();
	protected CutScene currentScene;

	public static Level testLevel = new SpawnLevel("/levels/testLevel.png", "testLevel", 80387673L);
	public static Level level_0_menu = new Level_0_Menu("/levels/level_0.png", "level_0_menu", 99010224L);
	public static Level level_1 = new Level_1("/levels/level1.png", "level_1", 448822856L);
	public static Level level_1_bar = new Level_1_Bar("/levels/level_1_bar.png", "level_1_bar", 498619487L);
	public static Level level_2 = new Level_2("/levels/level2.png", "level_2", 593057015L);
	public static Level level_3_chinatown = new Level_3_Chinatown("/levels/level_3_chinatown.png", "level_3_chinatown", 599271332L);
	public static Level level_4_pool = new Level_4_Pool("/levels/level_pool.png", "level_4_pool", 387341236L);

	public Level(String path, String overlaysPath, String levelName, long seed) {
		this.seed = seed;
		this.levelName = levelName;
		MAX_SHAKE_OFFSET = 16;
		MAX_RECOIL_OFFSET = 12;
		loadLevel(path);
		loadShadows();
		loadTileZ();
		loadOverlaysMap(overlaysPath);
		loadBorderMap();
		loadCollisionMap();
	}

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		MAX_SHAKE_OFFSET = 16;
		MAX_RECOIL_OFFSET = 12;
		generateLevel();
		playerSpawn = new TileCoordinate(1, 1);
	}

	protected abstract void initTransition();

	protected abstract void addMobs();

	protected abstract void addItems();

	public void setInSceneStatus(boolean status) {
		for (Entity e : entities) {
			try {
				((Mob) e).setInSceneStatus(status);
				((Mob) e).stopMoving();
			} catch (ClassCastException arg0) {
			}
		}
	}

	public static Level getLevelFromName(String name) {
		switch (name) {
		case "testLevel":
			return testLevel;
		case "level_0_menu":
			return level_0_menu;
		case "level_1":
			return level_1;
		case "level_1_bar":
			return level_1_bar;
		case "level_2":
			return level_2;
		case "level_3_chinatown":
			return level_3_chinatown;
		case "level_4_pool":
			return level_4_pool;
		default:
			return null;
		}
	}

	public String getLevelName() {
		return levelName;
	}

	public static void addPlayersToLevels(Player player) {
		testLevel.add(player);
		level_0_menu.add(player);
		level_1_bar.add(player);
		level_1.add(player);
		level_2.add(player);
		level_3_chinatown.add(player);
		level_4_pool.add(player);
	}

	public static void initiateLevelTransitions() {
		testLevel.initTransition();
		level_0_menu.initTransition();
		level_1_bar.initTransition();
		level_1.initTransition();
		level_2.initTransition();
		level_3_chinatown.initTransition();
		level_4_pool.initTransition();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] generateBorderMap(int[] adjTileCols) {
		int[] out = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (Util.contains(adjTileCols, tileCols[x + y * width])) out[x + y * width] = 1;
				else out[x + y * width] = 0;
			}
		}
		return out;
	}

	protected void generateLevel() {
	}

	protected abstract void loadLevel(String path);

	public Long getSeed() {
		return seed;
	}

	public static Long getUniversalSeed() {
		return 49275600L;
	}

	private void loadShadows() {
		shadowMap = new int[width * height];
		boolean[][] map = new boolean[5][5];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				shadowMap[y * width + x] = -1;
				if (getTile(x, y).castsShadow) continue; // add level based determination for being inside or out.
				for (int i = -2; i < 3; i++) {
					for (int j = -2; j < 3; j++) {
						map[i + 2][j + 2] = (getTile(x + i, y + j).castsShadow) ? true : false;
					}
				}
				if (map[1][0]) shadowMap[y * width + x] = 3;
				if (map[1][0] && map[1][1]) shadowMap[y * width + x] = 4;
				if (map[3][0]) shadowMap[y * width + x] = 6;
				if (map[3][0] && map[3][1]) shadowMap[y * width + x] = 7;
				if (map[3][0] && map[1][0]) shadowMap[y * width + x] = 16;
				if (map[2][0]) shadowMap[y * width + x] = 1;
				if (map[2][0] && map[1][1]) shadowMap[y * width + x] = 15;
				if (map[2][1]) shadowMap[y * width + x] = 0;
				if (map[1][1] && map[3][1]) shadowMap[y * width + x] = 0;
				if (map[1][2] && map[3][2]) shadowMap[y * width + x] = 17;

				if (getTile(x, y).isStair() != StairTile.NOT_A_STAIR) {
					int dir = getTile(x, y).isStair();
					switch (dir) {
					case StairTile.LEFT:
						if (getTile(x, y - 3).castsShadow) shadowMap[y * width + x] = 10;
						if (map[2][0]) shadowMap[y * width + x] = 9;
						if (map[2][0] && map[2][3]) shadowMap[y * width + x] = 10;
						if (map[2][1]) shadowMap[y * width + x] = 8;
						break;
					case StairTile.RIGHT:
						if (map[2][1]) shadowMap[y * width + x] = 13;
						if (map[2][0]) shadowMap[y * width + x] = 12;
						if (map[2][0] && map[2][3]) shadowMap[y * width + x] = 13;
						if (map[2][1]) shadowMap[y * width + x] = 11;
						break;
					default:
						break;
					}

				}
			}
		}
	}

	private void loadBorderMap() {
		borderMap = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				borderMap[y * width + x] = 1;
				if (getTile(x, y).border() || overlaysMap[x + y * width] == Tile.wall.getColour()) borderMap[y * width + x] = 0;
			}
		}
		borderMap = BorderRenderer.updateSpriteMap(borderMap, width, height);
	}

	protected void loadCollisionMap() {
		collisionMap = new boolean[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (getTile(x, y).getZ() == 0) collisionMap[x + y * width] = true;
				else collisionMap[x + y * width] = false;
			}
		}
		aiCollisionMap = collisionMap.clone();
	}

	public void loadCollisionEntitiesToMap() {
		int x, y;
		for (Entity e : entities) {
			if (e instanceof CollisionEntity) {
				CollisionBox box = e.entityBounds;
				for (int i = 0; i < box.getXValues().length; i++) {
					x = (box.getXValues()[i] + e.getIntX()) >> 4;
					y = (box.getYValues()[i] + e.getIntY()) >> 4;
					if (x < 0 || x >= width || y < 0 || y >= height) continue;
					aiCollisionMap[x + y * width] = false;
				}
			}
		}
	}

	public boolean[] getSubAICollisionMap(int x, int y, int width, int height) {
		boolean[] subMap = new boolean[width * height];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (x + i < 0 || x + i >= this.width || y + j < 0 || y + j >= this.height) continue;
				subMap[i + j * width] = aiCollisionMap[(x + i) + (y + j) * this.width];
			}
		}
		return subMap;
	}

	protected void loadTileZ() {
		ZMap = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ZMap[y * width + x] = 2;
				if (!getTile(x, y).blocksProjectiles) ZMap[y * width + x] = 0;
				if (getTile(x, y).blocksProjectiles && !getTile(x, y + 1).blocksProjectiles) ZMap[y * width + x] = 1;
			}
		}
	}

	protected void loadOverlaysMap(String path) {
		try {
			BufferedImage image = ImageIO.read(Level.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			overlaysMap = new int[w * h];
			image.getRGB(0, 0, w, h, overlaysMap, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: Could not load level overlays file.");
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public class sortByY implements Comparator<Entity> {
		public int compare(Entity e1, Entity e2) {
			int y1 = e1.getYAnchor();
			int y2 = e2.getYAnchor();
			return (y1 < y2) ? -1 : (y1 == y2) ? 0 : 1;
		}
	}

	public void update() {
		addWaiting();
		if (screenTrauma > 0) screenTrauma -= 0.015;
		else screenTrauma = 0;
		if (recoilStrength > 0) recoilStrength -= 0.05 * recoilStrength + 0.05;
		else recoilStrength = 0;
		shakeOffsetX = (int) (MAX_SHAKE_OFFSET * screenTrauma * screenTrauma * ((random.nextDouble() * 2) - 1));
		shakeOffsetY = (int) (MAX_SHAKE_OFFSET * screenTrauma * screenTrauma * ((random.nextDouble() * 2) - 1));

		Collections.sort(entities, new sortByY());
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < decorations.size(); i++) {
			decorations.get(i).update();
		}
		for (int i = 0; i < transitions.size(); i++) {
			transitions.get(i).update(player.getIntX(), player.getIntY());
		}
		for (int i = 0; i < subAreas.size(); i++) {
			subAreas.get(i).update(player.getIntX(), player.getIntY());
		}
		for (int i = 0; i < cutScenes.size(); i++) {
			cutScenes.get(i).update(player.getIntX(), player.getIntY());
		}
		remove();
	}

	private void addWaiting() {
		for (Entity e : tempAdd) {
			if (e == null) continue;
			e.init(this);
			if (e instanceof Particle) particles.add((Particle) e);
			else if (e instanceof Projectile) projectiles.add((Projectile) e);
			else if (e instanceof BackgroundDecoration) entities.add(e);
			else if (e instanceof Decoration) decorations.add((Decoration) e);
			else entities.add(e);
			if (e instanceof CollisionEntity) loadCollisionEntitiesToMap(); // may cause lag if many entities being added;
		}
		tempAdd.clear();
	}

	public void add(LevelTransition l) {
		transitions.add(l);
	}

	public void add(LevelSubArea ls) {
		subAreas.add(ls);
	}

	public void add(CutScene scene) {
		cutScenes.add(scene);
		currentScene = scene;
	}

	public void addTrauma(double amount) {
		screenTrauma += amount - screenTrauma / 2;
	}

	public void addScreenRecoil(double strength, double direction) {
		recoilStrength = strength;
		recoilDir = direction;
	}

	public Vec2i castRay(Vec2i start, Vec2i target, boolean ignoreLowWalls) {
		Vec2d directionVec = new Vec2d(target.x - start.x, target.y - start.y);
		double distance = Math.sqrt((target.x - start.x) * (target.x - start.x) + (target.y - start.y) * (target.y - start.y));
		return castRay(start, directionVec, distance, true, target, ignoreLowWalls);
	}

	public Vec2i castRay(Vec2i start, Vec2d directionVector, double maxDistance, boolean hasTarget, Vec2i target, boolean ignoreLowWalls) {
		Vec2d intraTileStartPos = new Vec2d((start.x % 16) / 16.0, (start.y % 16) / 16.0);
		Vec2i rayPos_Tile = new Vec2i((int) ((double) start.x / 16), (int) ((double) start.y / 16));
		Vec2d unitVec = directionVector.normalise();

		Vec2d rayStepSize = new Vec2d(Math.sqrt(1 + (unitVec.y * unitVec.y) / (unitVec.x * unitVec.x)), Math.sqrt(1 + (unitVec.x * unitVec.x) / (unitVec.y * unitVec.y)));
		Vec2d rayLength1D = new Vec2d(0, 0);
		Vec2i stepPolarity = new Vec2i(0, 0);

		double lastMove;
		boolean lastMoveX;
		double distanceCovered = 0;
		boolean isSightline = true;
		boolean lowWallHit = false;

		if (unitVec.x < 0) {
			stepPolarity.x = -1;
			rayLength1D.x = intraTileStartPos.x * rayStepSize.x;
		} else {
			stepPolarity.x = 1;
			rayLength1D.x = (1 - intraTileStartPos.x) * rayStepSize.x;
		}

		if (unitVec.y < 0) {
			stepPolarity.y = -1;
			rayLength1D.y = intraTileStartPos.y * rayStepSize.y;
		} else {
			stepPolarity.y = 1;
			rayLength1D.y = (1 - intraTileStartPos.y) * rayStepSize.y;
		}
		
		if (getTileZ((int) (start.x + maxDistance * unitVec.x) >> 4, (int) (start.y + maxDistance * unitVec.y) >> 4) != 0 && hasTarget) return start;

		while (isSightline && distanceCovered * 16 < maxDistance) {
			if (rayLength1D.x < rayLength1D.y) {
				rayPos_Tile.x = rayPos_Tile.x + stepPolarity.x;
//				lastMoveX = true;
//				lastMove = rayLength1D.x - distanceCovered;
				distanceCovered = rayLength1D.x;
				rayLength1D.x += rayStepSize.x;
				if (rayLength1D.x > maxDistance / 16) rayLength1D.x = maxDistance / 16;
				if (distanceCovered > maxDistance / 16) distanceCovered = maxDistance / 16;

				// DEBUG:
//				add(new DebugParticle(rayPos_Tile.x * 16, rayPos_Tile.y * 16, 10, 1, 1, Sprite.item_slot));
//				add(new DebugParticle(start.x + distanceCovered * unitVec.x * 16, start.y + distanceCovered * unitVec.y * 16, 30, 1, 1, Sprite.smallParticleCrimson));
			} else {
				rayPos_Tile.y = rayPos_Tile.y + stepPolarity.y;
//				lastMoveX = false;
//				lastMove = rayLength1D.y - distanceCovered;
				distanceCovered = rayLength1D.y;
				rayLength1D.y += rayStepSize.y;
				if (rayLength1D.y > maxDistance / 16) rayLength1D.y = maxDistance / 16;
				if (distanceCovered > maxDistance / 16) distanceCovered = maxDistance / 16;

//				if (lowWallHit) isSightline = false;

				// DEBUG:
//				add(new DebugParticle(rayPos_Tile.x * 16, rayPos_Tile.y * 16, 10, 1, 1, Sprite.item_slot_outline));
//				add(new DebugParticle(start.x + distanceCovered * unitVec.x * 16, start.y + distanceCovered * unitVec.y * 16, 30, 1, 1, Sprite.smallParticleYellow));
			}
			int tileZ = getTileZ(rayPos_Tile.x, rayPos_Tile.y);

//			if (ignoreLowWalls) {
//				if (tileZ == 1) {
//					if ((start.y + distanceCovered * unitVec.y * 16) % 16 == 0) {
//						if (stepPolarity.y == 1) {
//							int rx = (int) (start.x + distanceCovered * unitVec.x * 16);
//							int ry = (int) (start.y + distanceCovered * unitVec.y * 16 - 1);
//						add(new DebugParticle(rx, ry, 30, 1, 1, Sprite.smallParticleOrange));
//							return new Vec2i(rx, ry);
//						} else {
//							int rx = (int) (start.x + distanceCovered * unitVec.x * 16);
//							int ry = (int) (start.y + distanceCovered * unitVec.y * 16 - 16);
//						add(new DebugParticle(rx, ry, 30, 1, 1, Sprite.smallParticleBlue));
//							return new Vec2i(rx, ry);
//						}
//					} else isSightline = true;
//				} else isSightline = tileZ == 0;
//			}
//			if (ignoreLowWalls) {
//				if (tileZ == 1) {
//					if (!lastMoveX && stepPolarity.y == 1 && !lowWallHit) {
//						int rx = (int) (start.x + distanceCovered * unitVec.x * 16);
//						int ry = (int) (start.y + distanceCovered * unitVec.y - 1);
////						add(new DebugParticle(rx, ry, 30, 1, 1, Sprite.smallParticleBlue));
//						return new Vec2i(rx, ry);
//					} else {
//						isSightline = true;
//						lowWallHit = true;
//					}
//				} else isSightline = tileZ == 0;
//				if (tileZ == 0 && lowWallHit && !lastMoveX && stepPolarity.y == -1) {
//					int rx = (int) (start.x + (distanceCovered - lastMove / 16) * unitVec.x * 16);
//					int ry = (int) (start.y + (distanceCovered - lastMove / 16) * unitVec.y * 16 - 15);
//					return new Vec2i(rx, ry);
//				}
//				if (tileZ != 1) lowWallHit = false;
			/* } else */ isSightline = tileZ == 0;

//			if (ignoreLowWalls && tileZ == 1 && (lastMoveX || stepPolarity.y == -1)) lowWallHit = true;
//			if (lowWallHit) {
//				if (tileZ == 0) {
//					if (!lastMoveX && stepPolarity.y == -1) isSightline = false;
//					else lowWallHit = false;
//				} else if (tileZ == 2) isSightline = false;
//			} else isSightline = tileZ == 0;
//			if (tileZ == 1 && !lastMoveX && stepPolarity.y == 1) {
//				isSightline = false;
//				int rx = (int) (start.x + (distanceCovered - 1/16.0) * unitVec.x * 16);
//				int ry = (int) (start.y + (distanceCovered - 1/16.0) * unitVec.y * 16);
//				return new Vec2i(rx, ry);
//			}

//			if (!isSightline) {
//				if (lastMoveX) rayPos_Tile.x = rayPos_Tile.x - stepPolarity.x;
//				else rayPos_Tile.y = rayPos_Tile.y - stepPolarity.y;
//			}
//			if (drawLines && tileZ != 2) isSightline = true;
		}

//		if (distanceCovered * 16 >= maxDistance && hasTarget) return target;

		int rx = (int) (start.x + distanceCovered * unitVec.x * 16);
		int ry = (int) (start.y + distanceCovered * unitVec.y * 16);
		if (distanceCovered == maxDistance / 16) add(new DebugParticle(rx, ry, 30, 1, 1, Sprite.glassParticle));
		return new Vec2i(rx, ry);
	}

	public boolean isSightline(int x, int y, double direction, Entity target) {
		double x2 = x;
		double y2 = y;
		double nx = Math.cos(direction);
		double ny = Math.sin(direction);
		int ex1, ex2, ey1, ey2;
		ex1 = target.entityBounds.getXValues()[0] + target.getIntX();
		ex2 = target.entityBounds.getXValues()[1] + target.getIntX();
		ey1 = target.entityBounds.getYValues()[0] + target.getIntY();
		ey2 = target.entityBounds.getYValues()[2] + target.getIntY();
		while (aiCollisionMap[((int) x2 >> 4) + ((int) y2 >> 4) * width]) {
			x2 += nx;
			y2 += ny;
			if (x2 > ex1 && x2 < ex2) {
				if (y2 > ey1 && y2 < ey2) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSightline(Vec2i start, Vec2i target, boolean ignoreLowWalls) {
		Vec2i reached = castRay(start, target, ignoreLowWalls);
		return new Vec2i(reached.x >> 4, reached.y >> 4).equals(new Vec2i(target.x >> 4, target.y >> 4));
	}

	protected int getTileZ(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 2;
		else return ZMap[y * width + x];
	}

	public boolean collides(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return false;
		else return collisionMap[y * width + x];
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < decorations.size(); i++) {
			if (decorations.get(i).isRemoved()) decorations.remove(i);
		}
	}

	public CollisionPoint tileCollision(int x, int y, int[] xs, int[] ys) {
		for (int i = 0; i < xs.length; i++) {
			double xt = (x + xs[i]) >> 4;
			double yt = (y + ys[i]) >> 4;
			if (getTile((int) xt, (int) yt).blocksProjectiles) return new CollisionPoint(i, true);
		}
		return new CollisionPoint(0, false);
	}

	public class CollisionPoint {

		public int c = 0;
		public boolean isHit = false;

		public CollisionPoint(int c, boolean hit) {
			this.c = c;
			this.isHit = hit;
		}

	}

	public TileCoordinate getPlayerSpawn() {
		return playerSpawn;
	}

	public Tile getTile(double x, double y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		int x2 = (int) x;
		int y2 = (int) y;
		for (int i = 0; i < Tile.tiles.size(); i++) {
			if (tileCols[x2 + y2 * width] == Tile.tiles.get(i).getColour()) return Tile.tiles.get(i);
		}
		return Tile.voidTile;
	}

	public static Tile getTile(int colour) {
		for (int i = 0; i < Tile.tiles.size(); i++) {
			if (colour == Tile.tiles.get(i).getColour()) return Tile.tiles.get(i);
		}
		return Tile.voidTile;
	}

	public void render(int xS, int yS, Screen screen, Level level) {
		xScroll = xS;
		yScroll = yS;
		recoilOffsetX = (int) (recoilStrength * MAX_RECOIL_OFFSET * Math.cos(recoilDir));
		recoilOffsetY = (int) (recoilStrength * MAX_RECOIL_OFFSET * Math.sin(recoilDir));
		screen.setOffset(xScroll + shakeOffsetX + recoilOffsetX, yScroll + shakeOffsetY + recoilOffsetY);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTileZ(x, y) == 0) getTile(x, y).render(x, y, screen, level, seed);
			}
		}

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height) continue;
				if (shadowMap[y * width + x] == -1) continue;
				else screen.renderTranslucentSprite(x << 4, y << 4, shadowSprites[shadowMap[y * width + x]], true, 0.5);
			}
		}

		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).getZ() == 1) particles.get(i).render(screen);
		}
		for (int y = y0 - 4; y < y1 + 4; y++) {
			for (int i = 0; i < entities.size(); i++) {
				int eBottomY = entities.get(i).getYAnchor();
				if (eBottomY > y << 4 && eBottomY <= (y + 1) << 4) entities.get(i).render(screen);
			}
			for (int x = x0; x < x1; x++) {
				if (getTileZ(x, y) == 1) getTile(x, y).render(x, y, screen, level, seed);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		double arcLength = Math.PI / 3;
		double arc1 = player.getDirection() + (arcLength / 2);
		double arc2 = player.getDirection() - (arcLength / 2);
		Vec2d lVec = new Vec2d(Math.cos(arc1), Math.sin(arc1));
		Vec2d rVec = new Vec2d(Math.cos(arc2), Math.sin(arc2));
		Vec2i playerMid = new Vec2i(player.getMidX(), player.getMidY() + 4);

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height) continue;
				if (getTileZ(x, y) == 2) getTile(x, y).render(x, y, screen, level, seed);
				Sprite borderSpr = Sprite.nullSprite;
				int s = borderMap[y * width + x];
				if (s != -1) borderSpr = Tile.wallEdges.getSprites().get(s);
				if (overlaysMap[x + y * width] != Screen.ALPHA_COLOUR) {

					boolean[] corners = new boolean[4];
					corners[0] = isVecInArc(new Vec2d((x << 4) - player.getMidX(), (y << 4) - player.getMidY()), lVec, rVec) || isPlayerInRad((x << 4), (y << 4), 80);
					corners[1] = isVecInArc(new Vec2d((x << 4) + 15 - player.getMidX(), (y << 4) - player.getMidY()), lVec, rVec) || isPlayerInRad((x << 4) + 15, (y << 4), 80);
					corners[2] = isVecInArc(new Vec2d((x << 4) - player.getMidX(), (y << 4) + 15 - player.getMidY()), lVec, rVec) || isPlayerInRad((x << 4), (y << 4) + 15, 80);
					corners[3] = isVecInArc(new Vec2d((x << 4) + 15- player.getMidX(), (y << 4) + 15 - player.getMidY()), lVec, rVec) || isPlayerInRad((x << 4) + 15, (y << 4) + 15, 80);
					String cornersChar = String.valueOf(corners[0]).substring(0, 1) + String.valueOf(corners[1]).substring(0, 1) + String.valueOf(corners[2]).substring(0, 1) + String.valueOf(corners[3]).substring(0, 1);
					if (cornersChar.equals("ffff")) {
						getTile(x, y).renderOverlay(x, y, screen, level, getTile(overlaysMap[x + y * width]).getSprite(), borderSpr);
					} else {
						boolean[] corners2 = new boolean[4];
						corners2[0] = isSightline(playerMid, new Vec2i((x << 4) + 0, (y << 4) + 0), true) && isVecInArc(new Vec2d((x << 4) + 0 - player.getMidX(), (y << 4) + 0 - player.getMidY()), lVec, rVec);
						corners2[1] = isSightline(playerMid, new Vec2i((x << 4) + 15, (y << 4) + 0), true) && isVecInArc(new Vec2d((x << 4) + 15 - player.getMidX(), (y << 4) + 0 - player.getMidY()), lVec, rVec);
						corners2[2] = isSightline(playerMid, new Vec2i((x << 4) + 0, (y << 4) + 15), true) && isVecInArc(new Vec2d((x << 4) + 0 - player.getMidX(), (y << 4) + 15 - player.getMidY()), lVec, rVec);
						corners2[3] = isSightline(playerMid, new Vec2i((x << 4) + 15, (y << 4) + 15), true) && isVecInArc(new Vec2d((x << 4) + 15 - player.getMidX(), (y << 4) + 15 - player.getMidY()), lVec, rVec);

						Sprite spr = getSightlineSprite(x, y, corners2, getTile(overlaysMap[x + y * width]).getSprite(), lVec, rVec);
						borderSpr = getSightlineSprite(x, y, corners2, borderSpr, lVec, rVec);
						getTile(x, y).renderOverlay(x, y, screen, level, spr, (spr == Sprite.nullSprite ? spr : borderSpr));
					}
				} else screen.renderSprite(x << 4, y << 4, borderSpr, true);
			}
		}

		for (int i = 0; i < decorations.size(); i++) {
			decorations.get(i).render(screen);
		}
		for (int i = 0; i < subAreas.size(); i++) {
			subAreas.get(i).render(screen); // deprecated
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).getZ() == 2) particles.get(i).render(screen);
		}
		for (int i = 0; i < transitions.size(); i++) {
			transitions.get(i).render(screen);
		}
		for (int i = 0; i < cutScenes.size(); i++) {
			cutScenes.get(i).render(screen);
		}
		if (transitionDelay > 0) {
			screen.renderTranslucentSprite(0, 0, new Sprite(400, 250, 0xff000000), false, (double) transitionDelay / (double) transitionLength);
			transitionDelay--;
		}
	}

	private boolean isVecInArc(Vec2d vec, Vec2d leftVec, Vec2d rightVec) {
		return vec.invert().dot(rightVec.normal()) > 0 && vec.dot(leftVec.normal()) > 0;
	}

	private Sprite getSightlineSprite(int x, int y, boolean[] corners, Sprite sprite, Vec2d leftBorder, Vec2d rightBorder) {
		String cornersChar = String.valueOf(corners[0]).substring(0, 1) + String.valueOf(corners[1]).substring(0, 1) + String.valueOf(corners[2]).substring(0, 1) + String.valueOf(corners[3]).substring(0, 1);
		Sprite result = sprite/* Sprite.alterTranslucency(sprite, 0.7) */;
//		result = Sprite.nullSprite;
		if (cornersChar.equals("ffff")) return sprite;
		if (cornersChar.equals("tttt")) return Sprite.alterTranslucency(result, 0.3);
			
		if (cornersChar.equals("tftf")) {
			int point = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(point, 0), new Vec2i(point2, 15), new Vec2i(0, 8), result, 0xffff00ff);
		}
		if (cornersChar.equals("ftft")) {
			int point = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(15 + point, 0), new Vec2i(15 + point2, 15), new Vec2i(15, 8), result, 0xffff00ff);
		}
		if (cornersChar.equals("ttff")) {
			int point = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(0, point), new Vec2i(15, point2), new Vec2i(8, 0), result, 0xffff00ff);
		}
		if (cornersChar.equals("fftt")) {
			int point = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(0, 15 + point), new Vec2i(15, 15 + point2), new Vec2i(8, 15), result, 0xffff00ff);
		}

		if (cornersChar.equals("tfff")) {
			int point = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(point, 0), new Vec2i(0, point2), -1, result, 0xffff00ff);
		}
		if (cornersChar.equals("ftff")) {
			int point = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(15 + point, 0), new Vec2i(15, point2), 1, result, 0xffff00ff);
		}
		if (cornersChar.equals("fftf")) {
			int point = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(point, 15), new Vec2i(0, 15 + point2), 1, result, 0xffff00ff);
		}
		if (cornersChar.equals("ffft")) {
			int point = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(15 + point, 15), new Vec2i(15, 15 + point2), -1, result, 0xffff00ff);
		}

		if (cornersChar.equals("tttf")) {
			int point = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(point, 15), new Vec2i(15, point2), new Vec2i(0, 0), result, 0xffff00ff);
		}
		if (cornersChar.equals("ttft")) {
			int point = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, 1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(15 + point, 15), new Vec2i(0, point2), new Vec2i(15, 0), result, 0xffff00ff);
		}
		if (cornersChar.equals("tftt")) {
			int point = calculateIntersection(new Vec2i(1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(point, 0), new Vec2i(15, 15 + point2), new Vec2i(0, 15), result, 0xffff00ff);
		}
		if (cornersChar.equals("fttt")) {
			int point = calculateIntersection(new Vec2i(-1, 0), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(15, 0), leftBorder, rightBorder);
			int point2 = calculateIntersection(new Vec2i(0, -1), new Vec2i(player.getMidX(), player.getMidY() + 4), new Vec2i(x, y), new Vec2i(0, 15), leftBorder, rightBorder);
			result = calculateLineSprite(new Vec2i(15 + point, 0), new Vec2i(0, 15 + point2), new Vec2i(15, 15), result, 0xffff00ff);
		}
		return result;
	}

	private int calculateIntersection(Vec2i direction, Vec2i origin, Vec2i tile, Vec2i intraTile, Vec2d leftBorder, Vec2d rightBorder) {
		Vec2i oneVec = new Vec2i(1, 1);
		Vec2i check = new Vec2i(0, 0);
		int polarity = direction.dot(oneVec);
		int point = 0;
		int lastSight = point;
		check.set((tile.x << 4) + intraTile.x + direction.x * direction.x * point, (tile.y << 4) + intraTile.y + direction.y * direction.y * point);
		boolean pointFound = false;
		while (!pointFound) {
			point += polarity;
			check.set((tile.x << 4) + intraTile.x + direction.x * direction.x * point, (tile.y << 4) + intraTile.y + direction.y * direction.y * point);
			pointFound = !isSightline(origin, check, true) || !isVecInArc(new Vec2d(check.subtract(new Vec2i(player.getMidX(), player.getMidY()))), leftBorder, rightBorder);
			if (point == 0 || point == 15) pointFound = true;
			if (!pointFound) lastSight = point;
		}
		return lastSight;
	}
	
	private Sprite calculateLineSprite(Vec2i pointA, Vec2i pointB, int polarity, Sprite sprite, int colour) {
		int[] newPixels = new int[sprite.getWidth() * sprite.getHeight()];
		Sprite altSprite = Sprite.alterTranslucency(sprite, 0.3);
		int check;
		for (int y = 0; y < sprite.getHeight(); y++) {
			for (int x = 0; x < sprite.getWidth(); x++) {
				check = (int) Math.signum((x - pointA.x) * (pointB.y - pointA.y) - (y - pointA.y) * (pointB.x - pointA.x));
				if (check == polarity) newPixels[y * sprite.getWidth() + x] = altSprite.pixels[y * sprite.getWidth() + x];
				else newPixels[y * sprite.getWidth() + x] = sprite.pixels[y * sprite.getWidth() + x];
			}
		}
		return new Sprite(newPixels, sprite.getWidth(), sprite.getHeight());
	}
	
	private Sprite calculateLineSprite(Vec2i pointA, Vec2i pointB, Vec2i polarityCheck, Sprite sprite, int colour) {
		int polarity = (int) Math.signum((polarityCheck.x - pointA.x) * (pointB.y - pointA.y) - (polarityCheck.y - pointA.y) * (pointB.x - pointA.x));
		return calculateLineSprite(pointA, pointB, polarity, sprite, colour);
	}

	public void add(Entity e) {
		tempAdd.add(e);
	}

	public void initPlayer(Player player) {
		this.player = player;
		transitionDelay = 30;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Entity> getValidTargetsInRad(int x, int y, List<Entity> ignore, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		Entity e;
		for (int i = 0; i < entities.size(); i++) {
			e = entities.get(i);
			int ex = e.getIntX();
			int ey = e.getIntY();
			int dx = Math.abs(ex - x);
			int dy = Math.abs(ey - y);
			double d = Math.sqrt((dx * dx) + (dy * dy));
			if (d <= radius) {
				if (e instanceof Mob && ((Mob) e).getHealth() > 0 || e instanceof CollisionEntity) result.add(e);
			}
		}
		if (ignore == null) return result;
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < ignore.size(); j++) {
				if (result.get(i).equals(ignore.get(j))) result.remove(i);
			}
		}
		return result;
	}

	public List<Entity> getCollisionEntitiesInRange(Mob mob, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		for (Entity e : entities) {
			int ex = e.getIntX() + (e.getSpriteW() / 2);
			int ey = e.getIntY() + (e.getSpriteH() / 2);
			int dx = Math.abs(ex - mob.getIntX());
			int dy = Math.abs(ey - mob.getIntY());
			double d = Math.sqrt((dx * dx) + (dy * dy));
			if (d <= radius) {
				if (e instanceof CollisionEntity) result.add(e);
			}
		}
		return result;
	}

	public List<Projectile> getProjectilesInRad(int x, int y, int radius) {
		List<Projectile> result = new ArrayList<Projectile>();
		Projectile p;
		for (int i = 0; i < projectiles.size(); i++) {
			p = projectiles.get(i);
			int px = p.getIntX();
			int py = p.getIntY();
			int dx = Math.abs(px - x);
			int dy = Math.abs(py - y);
			double d = Math.sqrt((dx * dx) + (dy * dy));
			if (d <= radius) {
				result.add(p);
			}
		}
		return result;
	}

	public boolean isPlayerInRad(Entity e, int radius) {
		return isPlayerInRad(e.getMidX(), e.getMidY(), radius);
	}

	public boolean isPlayerInRad(double x, double y, int radius) {
		double px = getPlayer().getMidX();
		double py = getPlayer().getMidY();
		double dx = Math.abs(px - x);
		double dy = Math.abs(py - y);
		double d = Math.sqrt((dx * dx) + (dy * dy));
		return (d <= radius);
	}

	public List<Entity> getEntitiesInRad(double x, double y, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int xx, yy;
		double dx, dy;
		for (Entity e : entities) {
			xx = e.getIntX();
			yy = e.getIntY();
			dx = Math.abs(x - xx);
			dy = Math.abs(y - yy);
			double d = Math.sqrt((dx * dx) + (dy * dy));
			if (d <= radius) result.add(e);
		}
		return result;
	}

}
