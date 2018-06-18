package com.jcoadyschaebitz.neon.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.jcoadyschaebitz.neon.entity.Entity;
import com.jcoadyschaebitz.neon.entity.collisionEntities.CollisionEntity;
import com.jcoadyschaebitz.neon.entity.decorationEntities.Decoration;
import com.jcoadyschaebitz.neon.entity.mob.Mob;
import com.jcoadyschaebitz.neon.entity.mob.Player;
import com.jcoadyschaebitz.neon.entity.particle.Particle;
import com.jcoadyschaebitz.neon.entity.projectile.Projectile;
import com.jcoadyschaebitz.neon.graphics.Screen;
import com.jcoadyschaebitz.neon.graphics.Sprite;
import com.jcoadyschaebitz.neon.level.tile.Tile;
import com.jcoadyschaebitz.neon.level.tile.WallTile;
import com.jcoadyschaebitz.neon.util.Vector2i;

public abstract class Level implements Serializable {

	private static final long serialVersionUID = 8517428031736948566L;
	protected long seed;
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tileCols;
	private final int MAX_SHAKE_OFFSET, MAX_RECOIL_OFFSET;
	private int shakeOffsetX, shakeOffsetY;
	private int recoilOffsetX, recoilOffsetY;
	private double screenTrauma, recoilStrength, recoilDir;
	protected Random random = new Random();
	protected TileCoordinate playerSpawn;
	protected Player player;

	protected List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Decoration> decorations = new ArrayList<Decoration>();
	private List<LevelTransition> transitions = new ArrayList<LevelTransition>();
	private List<LevelSubArea> subAreas = new ArrayList<LevelSubArea>();

	public static Level testLevel = new SpawnLevel("/levels/testLevel.png", 80387673L);
	public static Level level1 = new Level1("/levels/level1.png", 448822856L);
	public static Level level2 = new Level2("/levels/level2.png", 593057015L);

	// private Comparator<Node> nodeSorter = new Comparator<Node>() {
	// public int compare(Node n0, Node n1) {
	// if (n1.fCost < n0.fCost) return 1;
	// if (n0.fCost < n1.fCost) return -1;
	// return 0;
	// }
	// };
	
	protected abstract void initTransition();
	
	protected abstract void addMobs();
	
	protected abstract void addItems();
	
	public static void initiateLevelTransitions() {
		level1.initTransition();
		level2.initTransition();
		testLevel.initTransition();
	}

	public class sortByY implements Comparator<Entity> {
		public int compare(Entity e1, Entity e2) {
			int y1, y2;
			y1 = e1.getIntY() + e1.getSpriteZHeight();
			y2 = e2.getIntY() + e2.getSpriteZHeight();
			return (y1 < y2) ? -1 : (y1 == y2) ? 0 : 1;
		}

	}

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		MAX_SHAKE_OFFSET = 16;
		MAX_RECOIL_OFFSET = 12;
		generateLevel();
		playerSpawn = new TileCoordinate(1, 1);
	}

	public Level(String path, long seed) {
		this.seed = seed;
		MAX_SHAKE_OFFSET = 16;
		MAX_RECOIL_OFFSET = 12;
		loadLevel(path);
		generateLevel();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	protected void generateLevel() {
	}

	protected abstract void loadLevel(String path);

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void update() {
		if (screenTrauma > 0) screenTrauma -= 0.01;
		else screenTrauma = 0;
		if (recoilStrength > 0) recoilStrength -= 0.05 * recoilStrength + 0.05;
		else recoilStrength = 0;

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
			transitions.get(i).checkForLevelChange(player.getIntX(), player.getIntY());
		}
		for (int i = 0; i < subAreas.size(); i++) {
			subAreas.get(i).update(player.getIntX(), player.getIntY());
		}
		remove();
	}

	public void addTransition(LevelTransition l) {
		transitions.add(l);
	}

	public void addSubArea(LevelSubArea ls) {
		subAreas.add(ls);
	}

	public void addTrauma(double amount) {
		screenTrauma += amount - screenTrauma / 2;
	}

	public void addScreenRecoil(double strength, double direction) {
		recoilStrength = strength;
		recoilDir = direction;
	}

	public Vector2i castRay(int x, int y, double direction) {
		double x2 = x;
		double y2 = y;
		double nx = Math.cos(direction);
		double ny = Math.sin(direction);
		while (!getTile(x2 / 16, y2 / 16).isSolid()) {
			x2 += nx;
			y2 += ny;
		}
		return new Vector2i((int) x2, (int) y2);
	}

	public boolean isSightLine(int x, int y, double direction, Player target) {
		double x2 = x;
		double y2 = y;
		double nx = Math.cos(direction);
		double ny = Math.sin(direction);
		int ex1, ex2, ey1, ey2;
		ex1 = target.entityBounds.getXValues()[0] + target.getIntX();
		ex2 = target.entityBounds.getXValues()[1] + target.getIntX();
		ey1 = target.entityBounds.getYValues()[0] + target.getIntY();
		ey2 = target.entityBounds.getYValues()[2] + target.getIntY();
		while (!getTile(x2 / 16, y2 / 16).isSolid()) {
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

	public CollisionP tileCollision(int x, int y, int[] xs, int[] ys) {
		for (int i = 0; i < xs.length; i++) {
			double xt = (x + xs[i]) >> 4;
			double yt = (y + ys[i]) >> 4;
			if (getTile((int) xt, (int) yt).isSolid()) return new CollisionP(i, true);
		}
		return new CollisionP(0, false);
	}

	public class CollisionP {

		public int c = 0;
		public boolean isHit = false;

		public CollisionP(int c, boolean hit) {
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

	public void render(int xScroll, int yScroll, Screen screen, Level level) {
		shakeOffsetX = (int) (MAX_SHAKE_OFFSET * screenTrauma * screenTrauma * ((random.nextDouble() * 2) - 1));
		shakeOffsetY = (int) (MAX_SHAKE_OFFSET * screenTrauma * screenTrauma * ((random.nextDouble() * 2) - 1));
		recoilOffsetX = (int) (recoilStrength * MAX_RECOIL_OFFSET * Math.cos(recoilDir));
		recoilOffsetY = (int) (recoilStrength * MAX_RECOIL_OFFSET * Math.sin(recoilDir));
		screen.setOffset(xScroll + shakeOffsetX + recoilOffsetX, yScroll + shakeOffsetY + recoilOffsetY);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y).zIndex == 0) getTile(x, y).render(x, y, screen, level, seed);
			}
		}
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y - 1).canHaveShadow && !(getTile(x, y) instanceof WallTile) && getTile(x, y).isOutdoors()) {
					screen.renderTranslucentSprite(x << 4, y << 4, Sprite.tileBottomShadow, true, 0.5);
				}
				// if (getTile(x + 1, y).canHaveShadow && !(getTile(x, y) instanceof WallTile) && getTile(x, y).isOutdoors()) {
				// screen.renderTranslucentSprite(x << 4, y << 4, Sprite.tileLeftShadowTop, true, 0.5);
				// }
			}
		}
		for (int y = y0; y < y1 + 4; y++) {
			for (int i = 0; i < entities.size(); i++) {
				int eBottomY = entities.get(i).getIntY() + entities.get(i).getSpriteH();
				if (eBottomY > y * 16 && eBottomY <= (y + 1) * 16) entities.get(i).render(screen);
			}
			if (y > y1) continue;
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y).zIndex == 1) getTile(x, y).render(x, y, screen, level, seed);
			}
		}
		for (int i = 0; i < decorations.size(); i++) {
			decorations.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}

		for (int i = 0; i < subAreas.size(); i++) {
			subAreas.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) particles.add((Particle) e);
		else if (e instanceof Projectile) projectiles.add((Projectile) e);
		else if (e instanceof Decoration) decorations.add((Decoration) e);
		else entities.add(e);
	}

	public void initPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	// public List<Node> findPath(Vector2i start, Vector2i goal) {
	//
	// List<Node> openList = new ArrayList<Node>();
	// List<Node> closedList = new ArrayList<Node>();
	// Node current = new Node(start, null, 0, getDistance(start, goal));
	// openList.add(current);
	//
	// while (openList.size() > 0) {
	// Collections.sort(openList, nodeSorter);
	// current = openList.get(0);
	// if (current.tile.equals(goal)) {
	// List<Node> path = new ArrayList<Node>();
	// while (current.parent != null) {
	// path.add(current);
	// current = current.parent;
	// }
	// openList.clear();
	// closedList.clear();
	// return path;
	// }
	// openList.remove(current);
	// closedList.add(current);
	// for (int i = 0; i < 9; i++) {
	// if (i == 4) continue;
	// int x = current.tile.getX();
	// int y = current.tile.getY();
	// int ix = (i % 3) - 1;
	// int iy = (i / 3) - 1;
	// Tile at = getTile(x + ix, y + iy);
	// if (at == null) continue;
	// if (at.isSolid()) continue;
	// Vector2i a = new Vector2i(x + ix, y + iy);
	// double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 1 : 0.99);
	// double hCost = getDistance(a, goal);
	// Node node = new Node(a, current, gCost, hCost);
	// if (vectorInList(closedList, a) && gCost >= current.gCost) continue;
	// if (!vectorInList(closedList, a) || gCost < current.gCost) openList.add(node);
	// }
	// }
	// closedList.clear();
	// return null;
	// }
	//
	// private boolean vectorInList(List<Node> list, Vector2i v) {
	// for (Node n : list) {
	// if (n.tile.equals(v)) return true;
	// }
	// return false;
	// }

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
				if (e instanceof Mob || e instanceof CollisionEntity) result.add(e);
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

	public List<Entity> getCollisionEntitiesInRad(Mob mob, int radius) {
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
		double ex = e.getX();
		double ey = e.getY();
		double px = getPlayer().getX();
		double py = getPlayer().getY();
		double dx = Math.abs(px - ex);
		double dy = Math.abs(py - ey);
		double d = Math.sqrt((dx * dx) + (dy * dy));
		if (d <= radius) return true;
		else return false;
	}

}
