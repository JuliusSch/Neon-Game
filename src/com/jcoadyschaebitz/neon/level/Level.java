package com.jcoadyschaebitz.neon.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
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
import com.jcoadyschaebitz.neon.entity.particle.Particle;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.BorderRenderer;
import com.jcoadyschaebitz.neon.level.tile.StairTile;
import com.jcoadyschaebitz.neon.level.tile.Tile;
import com.jcoadyschaebitz.neon.util.Util;
import com.jcoadyschaebitz.neon.util.Vec2i;

public abstract class Level implements Serializable {

	private static final long serialVersionUID = 8517428031736948566L;
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

	protected List<Entity> entities = new ArrayList<Entity>();
	protected List<Entity> tempAdd = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Decoration> decorations = new ArrayList<Decoration>();
	private List<LevelTransition> transitions = new ArrayList<LevelTransition>();
	private List<LevelSubArea> subAreas = new ArrayList<LevelSubArea>();
	private List<CutScene> cutScenes = new ArrayList<CutScene>();
	protected CutScene currentScene;

	public static Level testLevel = new SpawnLevel("/levels/testLevel.png", 80387673L);
	public static Level level_1 = new Level_1("/levels/level1.png", 448822856L);
	public static Level level_2 = new Level_2("/levels/level2.png", 593057015L);
	public static Level level_1_bar = new Level_1_Bar("/levels/level_1_bar.png", 498619487L);
	public static Level level_3_chinatown = new Level_3_Chinatown("/levels/level_3_chinatown.png", 599271332L);
	public static Level level_4_pool = new Level_4_Pool("/levels/level_pool.png", 387341236L);

	public Level(String path, String path2, long seed) {
		this.seed = seed;
		MAX_SHAKE_OFFSET = 16;
		MAX_RECOIL_OFFSET = 12;
		loadLevel(path);
		loadShadows();
		loadTileZ();
		loadOverlaysMap(path2);
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

	public static void addPlayersToLevels(Player player) {
		testLevel.add(player);
		level_1_bar.add(player);
		level_1.add(player);
		level_2.add(player);
		level_3_chinatown.add(player);
		level_4_pool.add(player);
	}

	public static void initiateLevelTransitions() {
		testLevel.initTransition();
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
			if (e instanceof CollisionEntity) loadCollisionEntitiesToMap();					// may cause lag if many entities being added;
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

	public Vec2i castRay(int x, int y, double direction) {
		double x2 = x;
		double y2 = y;
		double nx = Math.cos(direction);
		double ny = Math.sin(direction);
		while (getTile((int) x2 >> 4, (int) y2 >> 4).getZ() != 2) {
			x2 += nx;
			y2 += ny;
		}
		return new Vec2i((int) x2, (int) y2);
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
	
	public boolean isSightline(int xStart, int yStart, int xGoal, int yGoal) {
		double direction = Math.atan2(yGoal - yStart, xGoal - xStart);
		double dx = Math.cos(direction) * 5;
		double dy = Math.sin(direction) * 5;
		while (aiCollisionMap[((int) xStart >> 4) + ((int) yStart >> 4) * width]) {
			xStart += dx;
			yStart += dy;
			if (Math.abs(xGoal - xStart) <= 4 && Math.abs(yGoal - yStart) <= 4) return true;
		}
		return false;
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
				if (eBottomY > y * 16 && eBottomY <= (y + 1) * 16) entities.get(i).render(screen);
			}
			for (int x = x0; x < x1; x++) {
				if (getTileZ(x, y) == 1) getTile(x, y).render(x, y, screen, level, seed);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height) continue;
				if (getTileZ(x, y) == 2) getTile(x, y).render(x, y, screen, level, seed);
				int s = borderMap[y * width + x];
				Sprite borderSpr = Sprite.nullSprite;
				if (s != -1) borderSpr = Tile.wallEdges.getSprites().get(s);
				if (overlaysMap[x + y * width] != Screen.ALPHA_COLOUR) {
					getTile(x, y).renderOverlay(x, y, screen, level, overlaysMap[x + y * width], borderSpr);
//					System.out.print(Integer.toHexString(overlaysMap[x + y * width]) + ", ");
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
		return isPlayerInRad(e.getIntX(), e.getIntY(), radius);
	}

	public boolean isPlayerInRad(double x, double y, int radius) {
		double ex = x;
		double ey = y;
		double px = getPlayer().getMidX();
		double py = getPlayer().getMidY();
		double dx = Math.abs(px - ex);
		double dy = Math.abs(py - ey);
		double d = Math.sqrt((dx * dx) + (dy * dy));
		if (d <= radius) return true;
		else return false;
	}

	public List<Entity> getEntitiesInRad(double x, double y, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		double ex = x;
		double ey = y;
		int xx, yy;
		double dx, dy;
		for (Entity e : entities) {
			xx = e.getIntX();
			yy = e.getIntY();
			dx = Math.abs(ex - xx);
			dy = Math.abs(ey - yy);
			double d = Math.sqrt((dx * dx) + (dy * dy));
			if (d <= radius) result.add(e);
		}
		return result;
	}

}
