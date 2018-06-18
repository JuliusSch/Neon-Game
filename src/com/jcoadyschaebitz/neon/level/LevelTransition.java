package com.jcoadyschaebitz.neon.level;

public class LevelTransition {

	private int x1, y1, x2, y2;
	private Level levelFrom, levelTo;
	
	public LevelTransition(int x1, int y1, int x2, int y2, Level levelFrom, Level levelTo) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.levelFrom = levelFrom;
		this.levelTo = levelTo;
	}
	
	public void checkForLevelChange(int x, int y) {
		int xp = x >> 4;
		int yp = y >> 4;
		if (x1 <= xp && x2 >= xp) {
			if (y1 <= yp && y2 >= yp) {
				levelFrom.getPlayer().game.switchToLevel(levelTo);
				levelFrom.getPlayer().goTo(levelTo.playerSpawn.getX(), levelTo.getPlayerSpawn().getY());
			}
		}
	}
	
}
